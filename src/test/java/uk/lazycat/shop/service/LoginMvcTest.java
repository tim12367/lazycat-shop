package uk.lazycat.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uk.lazycat.shop.Authentication.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 整合測試，透過mockMvc呼叫需要測試的API
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LoginMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * 註冊成功
	 */
	@Test
	void signUpSuccessShoudReturn200() throws Exception {
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

	/**
	 * 重複註冊
	 */
	@Test
	void duplicateSignUpShoudReturn400() throws Exception {
		// 1. 創建 requestBuilder 負責設定相關請求參數
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/singup")
				.param("username", "test123")
				.param("password", "dummy");
		// 2. mockMvc.perform發起請求
		MvcResult mvcResult = mockMvc.perform(requestBuilder)
				.andDo(System.out::println)
				.andExpect(status().is(400))
				.andReturn();

		String body = mvcResult.getResponse().getContentAsString();
		System.out.println("返回的body: " + body);

		assertEquals(body, "帳號重複註冊!");
	}

	/**
	 * 登入成功
	 */
	@Test
	void loginSuccessShoudReturn200() throws Exception {
		// 1. 創建 requestBuilder 負責設定相關請求參數
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/login")
				.param("username", "test123")
				.param("password", "dummy");
		// 2. mockMvc.perform發起請求
		MvcResult mvcResult = mockMvc.perform(requestBuilder)
				.andDo(System.out::println)
				.andExpect(status().is(200))
				.andReturn();

		String body = mvcResult.getResponse().getContentAsString();
		System.out.println("返回的body: " + body);

		assertTrue(StringUtils.isNotBlank(body), "未返回登入token!");
	}

	/**
	 * 獲得access token
	 */
	@Test
	void getAccessTokenSuccessShoudReturn200() throws Exception {
		// 登入取得refresh token
		String refreshToken = authenticationService.login("test123", "dummy");

		// 1. 創建 requestBuilder 負責設定相關請求參數
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/get-access-token")
				.header("Authorization", "Bearer " + refreshToken);
		// 2. mockMvc.perform發起請求
		MvcResult mvcResult = mockMvc.perform(requestBuilder)
				.andDo(System.out::println)
				.andExpect(status().is(200))
				.andReturn();

		String body = mvcResult.getResponse().getContentAsString();
		System.out.println("返回的body: " + body);

		assertTrue(StringUtils.isNotBlank(body), "未返回授權token!");
	}
}
