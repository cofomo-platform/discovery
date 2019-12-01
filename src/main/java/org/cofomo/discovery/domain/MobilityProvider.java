package org.cofomo.discovery.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobilityProvider {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	String id;
	
	@NotBlank
	String name;
	
	@NotBlank
	String url;
	
	@NotNull
	@ElementCollection
	List<Integer> operationAreas;
	
	@NotNull
	@ElementCollection
	List<String> serviceOffers;
	
	@NotNull
	boolean alive;
	
	@NotNull
	LocalDateTime lastHeartBeat;
}
