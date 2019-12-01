package org.cofomo.discovery.api;

import java.util.List;

import org.cofomo.discovery.domain.MobilityProvider;
import org.cofomo.discovery.domain.MobilitySearchParam;

public interface IDiscoveryConsumer {
	public List<MobilityProvider> getBySearchCriteria(MobilitySearchParam searchParam);
	public List<MobilityProvider> getByOperationArea(int postcode);
}
