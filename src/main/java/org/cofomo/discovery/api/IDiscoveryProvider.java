package org.cofomo.discovery.api;

import java.util.List;

import org.cofomo.commons.domain.exploration.MobilityProviderEntity;


public interface IDiscoveryProvider {
	
	public List<MobilityProviderEntity> getAll();
	
	public List<MobilityProviderEntity> getActive();

	public MobilityProviderEntity getById(String providerId);

	public MobilityProviderEntity register(MobilityProviderEntity provider);

	public void update(MobilityProviderEntity provider, String providerId);

	public void remove(String providerId);
	
	public void heartbeat(String providerId);
}
