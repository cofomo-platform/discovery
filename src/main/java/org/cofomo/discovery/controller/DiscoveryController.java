package org.cofomo.discovery.controller;

import java.util.List;

import org.cofomo.discovery.api.IDiscoveryConsumer;
import org.cofomo.discovery.api.IDiscoveryProvider;
import org.cofomo.discovery.domain.MobilityProvider;
import org.cofomo.discovery.domain.MobilitySearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/discovery", produces="application/json")
public class DiscoveryController implements IDiscoveryConsumer, IDiscoveryProvider{
	
	@Autowired
	private DiscoveryFacade mpFacade;
	
	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<MobilityProvider> get() {
		return mpFacade.get();
	}

	@Override
	@GetMapping("/{providerId}")
	@ResponseStatus(HttpStatus.OK)
	public MobilityProvider get(@PathVariable String providerId) {
		return mpFacade.get(providerId);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MobilityProvider add(@RequestBody MobilityProvider provider) {
		return mpFacade.add(provider);
	}
	
	@Override
	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void update(@RequestBody MobilityProvider provider) {
		mpFacade.update(provider);
	}
	
	@Override
	@DeleteMapping("/{providerId}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String providerId) {
		mpFacade.delete(providerId);
	}
	
	@Override
	@GetMapping("/{providerId}/heartbeat")
	@ResponseStatus(HttpStatus.OK)
	public void heartbeat(@PathVariable String providerId) {
		mpFacade.heartbeat(providerId);
	}
	
	// IDiscoveryConsunemr
	
	@Override
	@PostMapping("/lookup/search")
	@ResponseStatus(HttpStatus.OK)
	public List<MobilityProvider> getBySearchCriteria(@RequestBody MobilitySearchParam searchParam) {
		return mpFacade.getBySearchCriteria(searchParam);
	}
	
	@Override
	@PostMapping("/lookup/area")
	@ResponseStatus(HttpStatus.OK)
	public List<MobilityProvider> getByOperationArea(@RequestBody int postcode) {
		return mpFacade.getByOperationArea(postcode);
	}
	
}
