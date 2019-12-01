package org.cofomo.discovery.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MobilitySearchParam {
	
	@NotNull
	public Location origin;
	
	public Location destination;
	
	public List<String> mobilityServices;
}
