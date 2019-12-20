package org.cofomo.discovery.controller;

import java.util.List;

import org.cofomo.commons.domain.exploration.MobilityProvider;
import org.cofomo.discovery.api.IDiscoveryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Discovery Provider API", description = "Implements IDiscoveryProvider")
@RequestMapping(path = "/v1")
public class DiscoveryProviderController implements IDiscoveryProvider {

	@Autowired
	private DiscoveryFacade mpFacade;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get all mobility provider")
	public List<MobilityProvider> getAll() {
		return mpFacade.getAll();
	}
	
	@Override
	@GetMapping("/active")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get all active mobility provider")
	public List<MobilityProvider> getActive() {
		return mpFacade.getAllActive();
	}
	
	@Override
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create mobility provider")
	public MobilityProvider register(@RequestBody MobilityProvider provider) {
		return mpFacade.create(provider);
	}

	@Override
	@GetMapping(path = "/{providerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get mobility provider by id")
	public MobilityProvider getById(@PathVariable String providerId) {
		return mpFacade.get(providerId);
	}


	@Override
	@PutMapping(path = "/{providerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(summary = "Update mobility provider")
	public void update(@RequestBody MobilityProvider provider, @PathVariable String providerId) {
		mpFacade.update(provider, providerId);
	}

	@Override
	@DeleteMapping("/{providerId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Delete mobility provider")
	public void remove(@PathVariable String providerId) {
		mpFacade.delete(providerId);
	}

	@Override
	@GetMapping("/{providerId}/heartbeat")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Send heartbeat")
	public void heartbeat(@PathVariable String providerId) {
		mpFacade.heartbeat(providerId);
	}
}
