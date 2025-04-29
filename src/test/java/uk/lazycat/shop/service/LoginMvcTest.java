package uk.lazycat.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 整合測試，透過mockMvc呼叫需要測試的API
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LoginMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void signUpShoudReturn200() throws Exception {
		// 1. 創建 requestBuilder 負責設定相關請求參數
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/singup")
				.param("username", "user")
				.param("password", "dummy");
		// 2. mockMvc.perform發起請求
		MvcResult mvcResult = mockMvc.perform(requestBuilder)
				.andDo(System.out::println)
				.andExpect(status().is(200))
				.andReturn();

		String body = mvcResult.getResponse().getContentAsString();
		System.out.println("返回的body: " + body);
	}

	@Test
	void duplicateSignUpShoudReturn200() throws Exception {
		// 1. 創建 requestBuilder 負責設定相關請求參數
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/singup")
				.param("username", "test123")
				.param("password", "dummy");
		// 2. mockMvc.perform發起請求
		MvcResult mvcResult = mockMvc.perform(requestBuilder)
				.andDo(System.out::println)
				.andExpect(status().is(200))
				.andReturn();

		String body = mvcResult.getResponse().getContentAsString();
		System.out.println("返回的body: " + body);

		assertEquals(objectMapper.readTree(body), objectMapper.readTree("{\"code\":\"9996\",\"info\":\"帳號重複註冊!\"}"));
	}
}
