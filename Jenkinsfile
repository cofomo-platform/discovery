pipeline {
    agent any
    
    tools {
        maven 'M3'
        jdk 'openjdk-13'
    }

    environment {
        // Extract information from pom.xml
        IMAGE = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
        DOCUMENTATIONURL = "https://discovery.cofomo.io"
    }

    stages {
        stage('Build Maven') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/cofomo-platform/discovery'

                // Run Maven
                sh "mvn -Dspring.profiles.active=prod clean package"
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
        stage('Copy Documentation') {
            steps {
                // Copy all documentation snippets to parent folder
                sh "cp -r ./target/generated-snippets/. ../DOCS/generated-snippets"
                sh "cp -r ./src/main/asciidoc/index.adoc ../DOCS/sourcefiles/exploration.adoc"
            }
        }
        stage('Build Docker Image') {
            steps {
                // This builds the container
                sh "docker build . --build-arg version=${VERSION} -t discovery:${VERSION}"
            }
        }
        stage('Build Docker Container') {
            steps {
                // This runs the container
                sh "docker container stop discovery"
                sh "docker container rm discovery"
                sh "docker run --name discovery -p 127.0.0.1:8888:8080 --network cofomo --restart always -d discovery:${VERSION}"
            }
            post {
                 // Cleanup
                success {
                    sh "docker system prune"
                }
            }
        }
    }
    post { 
    	// Send success message to Slack
        success { 
            slackSend color: "good", message: "discovery:${VERSION} successfully built and deployed in ${currentBuild.durationString}.\nGo to ${DOCUMENTATIONURL} for more information"
        }
        // Send failure message to Slack
        failure {
            slackSend color: "bad", message: "Failure in build and deployment of discovery:${VERSION}"
        }
        
    }
}