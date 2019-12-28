package org.cofomo.discovery.api;

import java.util.List;

import org.cofomo.commons.domain.exploration.MobilityProviderEntity;
import org.cofomo.commons.domain.exploration.MobilitySearchParam;

public interface IDiscoveryConsumer {
	public List<MobilityProviderEntity> getBySearchCriteria(MobilitySearchParam searchParam);
	public List<MobilityProviderEntity> getByOperationArea(int postcode);
}
