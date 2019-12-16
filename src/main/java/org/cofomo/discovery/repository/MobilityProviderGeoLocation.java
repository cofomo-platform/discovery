package org.cofomo.discovery.repository;

import java.util.List;

import org.cofomo.commons.domain.exploration.Location;
import org.cofomo.commons.domain.exploration.MobilityProvider;

public interface MobilityProviderGeoLocation {
	public List<MobilityProvider> getByLocation(Location location); 
}
