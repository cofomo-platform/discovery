package org.cofomo.discovery.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.cofomo.discovery.api.IDiscoveryConsumer;
import org.cofomo.discovery.api.IDiscoveryProvider;
import org.cofomo.discovery.domain.MobilityProvider;
import org.cofomo.discovery.domain.MobilitySearchParam;
import org.cofomo.discovery.error.ProviderNotFoundException;
import org.cofomo.discovery.repository.DiscoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryFacade implements IDiscoveryConsumer, IDiscoveryProvider {

	@Autowired
	DiscoveryRepository mpRepository;

	@Override
	public List<MobilityProvider> get() {
		return (List<MobilityProvider>) mpRepository.findAllByLastHeartBeatBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now()
				);
	}

	@Override
	public MobilityProvider get(String providerId) {
		return mpRepository
				.findAllByLastHeartBeatBetweenAndId(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), providerId)
				.orElseThrow(() -> new ProviderNotFoundException(providerId));
	}

	@Override
	public MobilityProvider create(MobilityProvider provider) {
		provider.setAlive(true);
		provider.setLastHeartBeat(LocalDateTime.now());
		return mpRepository.save(provider);
	}

	@Override
	public void update(MobilityProvider provider) {
		provider.setAlive(true);
		provider.setLastHeartBeat(LocalDateTime.now());
		mpRepository.save(provider);
	}

	@Override
	public void delete(String providerId) {
		mpRepository.deleteById(providerId);
	}

	@Override
	public void heartbeat(String providerId) {
		MobilityProvider mp = mpRepository.findById(providerId)
				.orElseThrow(() -> new ProviderNotFoundException(providerId));
//		mp.setLastHeartBeat(LocalDateTime.now());
		mpRepository.save(mp);
	}

	// IDiscoveryConsumer

	@Override
	public List<MobilityProvider> getByOperationArea(int postcode) {
		return mpRepository.findByOperationAreas(postcode);
	}

	@Override
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
