package org.cofomo.discovery.api;

import java.util.List;

import org.cofomo.discovery.domain.MobilityProvider;

public interface IDiscoveryProvider {
	public List<MobilityProvider> get();

	public MobilityProvider get(String providerId);

	public MobilityProvider add(MobilityProvider provider);

	public void update(MobilityProvider provider);

	public void delete(String providerId);
	
	public void heartbeat(String providerId);
}
