package org.cofomo.discovery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.cofomo.discovery.domain.MobilityProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:application-integration-test.yml")
public class ApiIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
//	@Test
//	public void shouldAddMobilityProvider() throws Exception {
//		MobilityProvider mp = createMobilityProvider();
//		MvcResult result = this.mockMvc.perform(post("/discovery").content(objectMapper.writeValueAsString(mp))
//		.contentType(MediaType.APPLICATION_JSON)
//		.accept(MediaType.APPLICATION_JSON))
//		.andDo(print())
//		.andExpect(status().isCreated())
//		.andReturn();
//		String mpResponseString = result.getResponse().getContentAsString();
//		MobilityProvider mpResponse = objectMapper.readValue(mpResponseString, MobilityProvider.class);
//		mpResponse.setId("1");
//		assertThat(mp).isEqualTo(mpResponse);
//	}
//	
//	public static MobilityProvider createMobilityProvider() {
//		List<String> serviceOffers = new ArrayList<String>();
//		serviceOffers.add("carsharing");
//		serviceOffers.add("bikesharing");
//		
//		return new MobilityProvider("1", "carsharing","https://test.carsharing.org", 48.521637, 9.057645, serviceOffers);
//	}
	
}
