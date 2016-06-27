package com.oriaxx77.play.coinminer.rest;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.oriaxx77.play.coinminer.CoinMinerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoinMinerApplication.class})
@WebAppConfiguration
public class CoinMinerControllerTest {
		
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	
	@Test
	public void testMine_ReturnEventsSuccessful() throws Exception {
		
		MvcResult result = mvc.perform( get("/coinminer/mine/11") )
							  .andExpect( status().isOk() )
							  .andExpect( request().asyncStarted())
						      .andExpect( request().asyncResult(nullValue()))
						      .andExpect( header().string("Content-Type", "text/event-stream"))
						      .andReturn();
		assertEquals( 11, getEventCountFromContent( result.getResponse().getContentAsString() ) );		
	}
	
	
	private int getEventCountFromContent( String responseContent )
	{
		Pattern pattern = Pattern.compile( "data\\:\\{\\\"value\\\":\\d+\\}" );
		Matcher matcher = pattern.matcher( responseContent );
		int eventCount = 0;
		while (matcher.find()){
			eventCount++;
		}
		return eventCount;
	}
		
}
