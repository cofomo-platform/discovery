package org.cofomo.discovery.controller;

import java.util.List;

import org.cofomo.discovery.api.IDiscoveryConsumer;
import org.cofomo.discovery.domain.MobilityProvider;
import org.cofomo.discovery.domain.MobilitySearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Discovery Consumer API", description = "Implements IDiscoveryConsumer")
@RequestMapping(path = "/v1")
public class DiscoveryConsumerController implements IDiscoveryConsumer {

	@Autowired
	private DiscoveryFacade mpFacade;

	@Override
	@PostMapping(path = "/lookup/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Search by search params")
	public List<MobilityProvider> getBySearchCriteria(@RequestBody MobilitySearchParam searchParam) {
		return mpFacade.getBySearchCriteria(searchParam);
	}

	@Override
	@GetMapping(path = "/lookup/operationarea/{postcode}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Search by operation area")
	public List<MobilityProvider> getByOperationArea(@PathVariable int postcode) {
		return mpFacade.getByOperationArea(postcode);
	}

}
