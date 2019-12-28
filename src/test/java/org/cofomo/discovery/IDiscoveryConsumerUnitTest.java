package org.cofomo.discovery;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.cofomo.commons.domain.exploration.Location;
import org.cofomo.commons.domain.exploration.MobilityProviderEntity;
import org.cofomo.commons.domain.exploration.MobilitySearchParam;
import org.cofomo.discovery.controller.DiscoveryConsumerController;
import org.cofomo.discovery.controller.DiscoveryFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@WebMvcTest(DiscoveryConsumerController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class IDiscoveryConsumerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DiscoveryFacade facade;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation).uris().withScheme("https")
						.withHost("api.cofomo.org").withPort(443))
				.alwaysDo(
						document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.build();
	}

	@Test
	public void shouldReturnMobilityProviderByTravelRequest() throws Exception {

		// create list of mobility providers
		List<MobilityProviderEntity> mpList = new ArrayList<MobilityProviderEntity>();
		mpList.add(createMobilityProvider1());

		// create search parameters
		List<String> serviceOffers = new ArrayList<String>();
		serviceOffers.add("carsharing");
		MobilitySearchParam searchParam = new MobilitySearchParam(new Location(48.521637, 9.057645),
				new Location(48.521637, 8.057645), serviceOffers);

		// define mock return value
		when(facade.getBySearchCriteria(searchParam)).thenReturn(mpList);

		// action
		this.mockMvc
				.perform(post("/v1/lookup/search").content(objectMapper.writeValueAsString(searchParam))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andDo(document("lookup-search", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						relaxedRequestFields(fieldWithPath("origin").description("Origin of travel").type("Location"),
								fieldWithPath("destination").description("Destination of travel").type("Location"),
								fieldWithPath("mobilityServices").description("Requested mobility services").type("MobilityType "))));
	}

	@Test
	public void shouldReturnMobilityProviderByLocationRequest() throws Exception {

		// create list of mobility providers
		List<MobilityProviderEntity> mpList = new ArrayList<MobilityProviderEntity>();
		mpList.add(createMobilityProvider1());

		// define mock return value
		when(facade.getByOperationArea(72072)).thenReturn(mpList);

		// action
		this.mockMvc.perform(get("/v1/lookup/operationarea/72072")).andDo(print())
				.andExpect(status().isOk()).andDo(document("lookup-area"));
	}

	// helper functions
	private static MobilityProviderEntity createMobilityProvider1() {
		List<String> serviceOffers = new ArrayList<String>();
		List<Integer> areas = new ArrayList<Integer>();
		serviceOffers.add("carsharing");
		areas.add(72072);
		return new MobilityProviderEntity("Carsharing Inc.", "https://test.carsharing.org", areas, serviceOffers);
	}
}
