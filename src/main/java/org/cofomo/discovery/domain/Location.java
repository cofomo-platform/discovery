package org.cofomo.discovery.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
	
	@NotNull
	public double latitude;
	
	@NotNull
	public double longitude;
}
