package org.cofomo.discovery.api;

import java.util.List;

import org.cofomo.commons.domain.exploration.MobilityProvider;
import org.cofomo.commons.domain.exploration.MobilitySearchParam;

public interface IDiscoveryConsumer {
	public List<MobilityProvider> getBySearchCriteria(MobilitySearchParam searchParam);
	public List<MobilityProvider> getByOperationArea(int postcode);
}
