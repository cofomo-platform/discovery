pipeline {
    agent any
    
    tools {
        maven 'M3'
        jdk 'openjdk-13'
    }

    environment {
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
        IMAGE = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
        DOCUMENTATIONURL = "https://cofomo.io/discovery/documentation"
    }

    stages {
        stage('Build Maven') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/ilkohoffmann/cofomo-discovery'

                // Run Maven on a Unix agent.
                sh "mvn -Dspring.profiles.active=prod clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                /* This builds the actual image; synonymous to
                 * docker build on the command line */
                sh "docker build . --build-arg version=${VERSION} -t discovery:${VERSION}"
            }
        }
        stage('Build Docker Container') {
            steps {
                /* This builds the actual image; synonymous to
                 * docker build on the command line */
                sh "docker container stop discovery"
                sh "docker container rm discovery"
                sh "docker run --name discovery -p 8888:8080 --network cofomo --restart always -d discovery:${VERSION}"
            }
            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    sh "docker system prune"
                }
            }
        }
    }
    post { 
        success { 
            slackSend color: "good", message: "discovery:${VERSION} successfully built and deployed in ${currentBuild.durationString}.\nGo to ${DOCUMENTATIONURL} for more information"
        }
        failure {
            slackSend color: "bad", message: "Failure in build and deployment of discovery:${VERSION}"
        }
        
    }
}