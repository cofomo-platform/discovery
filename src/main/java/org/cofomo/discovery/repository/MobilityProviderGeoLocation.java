package org.cofomo.discovery.repository;

import java.util.List;

import org.cofomo.discovery.domain.Location;
import org.cofomo.discovery.domain.MobilityProvider;

public interface MobilityProviderGeoLocation {
	public List<MobilityProvider> getByLocation(Location location); 
}
