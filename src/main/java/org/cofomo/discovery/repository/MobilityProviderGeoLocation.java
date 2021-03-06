package org.cofomo.discovery.repository;

import java.util.List;

import org.cofomo.commons.domain.exploration.Location;
import org.cofomo.commons.domain.exploration.MobilityProviderEntity;

public interface MobilityProviderGeoLocation {
	public List<MobilityProviderEntity> getByLocation(Location location); 
}
