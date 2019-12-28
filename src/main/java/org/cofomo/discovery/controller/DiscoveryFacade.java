package org.cofomo.discovery.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.cofomo.commons.domain.exploration.MobilityProviderEntity;
import org.cofomo.commons.domain.exploration.MobilitySearchParam;
import org.cofomo.discovery.error.ProviderNotFoundException;
import org.cofomo.discovery.repository.DiscoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryFacade {

	@Autowired
	DiscoveryRepository mpRepository;

	public List<MobilityProviderEntity> getAll() {
		return (List<MobilityProviderEntity>) mpRepository.findAll();
	}

	public List<MobilityProviderEntity> getAllActive() {
		return (List<MobilityProviderEntity>) mpRepository.findAllByLastHeartBeatBetween(LocalDateTime.now().minusMinutes(1),
				LocalDateTime.now());
	}

	public MobilityProviderEntity get(String providerId) {
		return mpRepository
				.findAllByLastHeartBeatBetweenAndId(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), providerId)
				.orElseThrow(() -> new ProviderNotFoundException(providerId));
	}

	public MobilityProviderEntity create(MobilityProviderEntity provider) {
		provider.setAlive(true);
		provider.setLastHeartBeat(LocalDateTime.now());
		return mpRepository.save(provider);
	}

	public void update(MobilityProviderEntity provider, String providerId) {
		provider.setAlive(true);
		provider.setLastHeartBeat(LocalDateTime.now());
		mpRepository.save(provider);
	}

	public void delete(String providerId) {
		mpRepository.deleteById(providerId);
	}

	public void heartbeat(String providerId) {
		MobilityProviderEntity mp = mpRepository.findById(providerId)
				.orElseThrow(() -> new ProviderNotFoundException(providerId));
		mp.setLastHeartBeat(LocalDateTime.now());
		mpRepository.save(mp);
	}

	// IDiscoveryConsumer

	public List<MobilityProviderEntity> getByOperationArea(int postcode) {
		return mpRepository.findByOperationAreas(postcode);
	}

	public List<MobilityProviderEntity> getBySearchCriteria(MobilitySearchParam searchParam) {
		List<String> ms = searchParam.mobilityServices;
		List<MobilityProviderEntity> mp;
		if (ms != null) {
			mp = mpRepository.findByServiceOffersIn(ms);
		} else {
			mp = (List<MobilityProviderEntity>) mpRepository.findAll();
		}
		return mp;
	}

}
