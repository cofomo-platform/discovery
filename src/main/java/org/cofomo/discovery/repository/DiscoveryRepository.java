package org.cofomo.discovery.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.cofomo.commons.domain.exploration.MobilityProviderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoveryRepository extends CrudRepository<MobilityProviderEntity, String>, MobilityProviderGeoLocation {

	// no heartbeat check
	public List<MobilityProviderEntity> findByServiceOffersIn(List<String> ms);

	// no heartbeat check
	public List<MobilityProviderEntity> findByOperationAreas(int postcode);

	public List<MobilityProviderEntity> findByLastHeartBeatBetweenAndServiceOffersIn(LocalDateTime timeStart,
			LocalDateTime timeEnd, List<String> ms);

	public List<MobilityProviderEntity> findByLastHeartBeatBetweenAndOperationAreas(LocalDateTime timeStart,
			LocalDateTime timeEnd, int postcode);

	public Optional<MobilityProviderEntity> findAllByLastHeartBeatBetweenAndId(LocalDateTime timeStart,
			LocalDateTime timeEnd, String id);

	public List<MobilityProviderEntity> findAllByLastHeartBeatBetween(LocalDateTime timeStart,
			LocalDateTime timeEnd);
}
