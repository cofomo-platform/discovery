package org.cofomo.discovery.api;

import java.util.List;

import org.cofomo.commons.domain.exploration.MobilityProvider;


public interface IDiscoveryProvider {
	
	public List<MobilityProvider> getAll();
	
	public List<MobilityProvider> getActive();

	public MobilityProvider get(String providerId);

	public MobilityProvider create(MobilityProvider provider);

	public void update(MobilityProvider provider, String providerId);

	public void delete(String providerId);
	
	public void heartbeat(String providerId);
}
