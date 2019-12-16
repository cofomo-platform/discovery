package org.cofomo.discovery.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.cofomo.commons.domain.exploration.MobilityProvider;
import org.cofomo.commons.domain.exploration.MobilitySearchParam;
import org.cofomo.discovery.error.ProviderNotFoundException;
import org.cofomo.discovery.repository.DiscoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryFacade {

	@Autowired
	DiscoveryRepository mpRepository;

	public List<MobilityProvider> getAll() {
		return (List<MobilityProvider>) mpRepository.findAll();
	}

	public List<MobilityProvider> getAllActive() {
		return (List<MobilityProvider>) mpRepository.findAllByLastHeartBeatBetween(LocalDateTime.now().minusMinutes(1),
				LocalDateTime.now());
	}

	public MobilityProvider get(String providerId) {
		return mpRepository
				.findAllByLastHeartBeatBetweenAndId(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), providerId)
				.orElseThrow(() -> new ProviderNotFoundException(providerId));
	}

	public MobilityProvider create(MobilityProvider provider) {
		provider.setAlive(true);
		provider.setLastHeartBeat(LocalDateTime.now());
		return mpRepository.save(provider);
	}

	public void update(MobilityProvider provider, String providerId) {
		provider.setAlive(true);
		provider.setLastHeartBeat(LocalDateTime.now());
		mpRepository.save(provider);
	}

	public void delete(String providerId) {
		mpRepository.deleteById(providerId);
	}

	public void heartbeat(String providerId) {
		MobilityProvider mp = mpRepository.findById(providerId)
				.orElseThrow(() -> new ProviderNotFoundException(providerId));
//		mp.setLastHeartBeat(LocalDateTime.now());
		mpRepository.save(mp);
	}

	// IDiscoveryConsumer

	public List<MobilityProvider> getByOperationArea(int postcode) {
		return mpRepository.findByOperationAreas(postcode);
	}

	public List<MobilityProvider> getBySearchCriteria(MobilitySearchParam searchParam) {
		List<String> ms = searchParam.mobilityServices;
		List<MobilityProvider> mp;
		if (ms != null) {
			mp = mpRepository.findByServiceOffersIn(ms);
		} else {
			mp = (List<MobilityProvider>) mpRepository.findAll();
		}
		return mp;
	}

}
