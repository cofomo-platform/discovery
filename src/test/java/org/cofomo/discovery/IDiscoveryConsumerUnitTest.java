package org.cofomo.discovery;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.cofomo.discovery.controller.DiscoveryController;
import org.cofomo.discovery.controller.DiscoveryFacade;
import org.cofomo.discovery.domain.Location;
import org.cofomo.discovery.domain.MobilityProvider;
import org.cofomo.discovery.domain.MobilitySearchParam;
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
@WebMvcTest(DiscoveryController.class)
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
		List<MobilityProvider> mpList = new ArrayList<MobilityProvider>();
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
				.perform(post("/discovery/lookup/search").content(objectMapper.writeValueAsString(searchParam))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andDo(document("lookup-search", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						requestFields(fieldWithPath("origin").description("Origin of travel"),
								fieldWithPath("origin.latitude").description("Latitude"),
								fieldWithPath("origin.longitude").description("Longitude"),
								fieldWithPath("destination").description("Destination of travel"),
								fieldWithPath("destination.latitude").description("Latitude"),
								fieldWithPath("destination.longitude").description("Longitude"),
								fieldWithPath("mobilityServices").description("Requested mobility services"))));
	}

	@Test
	public void shouldReturnMobilityProviderByLocationRequest() throws Exception {

		// create list of mobility providers
		List<MobilityProvider> mpList = new ArrayList<MobilityProvider>();
		mpList.add(createMobilityProvider1());

		// define mock return value
		when(facade.getByOperationArea(72072)).thenReturn(mpList);

		// action
		this.mockMvc.perform(get("/discovery/lookup/area/72072").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andDo(document("lookup-area"));
	}

	// helper functions
	private static MobilityProvider createMobilityProvider1() {
		List<String> serviceOffers = new ArrayList<String>();
		List<Integer> areas = new ArrayList<Integer>();
		serviceOffers.add("carsharing");
		areas.add(72072);
		return new MobilityProvider("Carsharing Inc.", "https://test.carsharing.org", areas, serviceOffers);
	}
}
