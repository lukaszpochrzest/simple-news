package org.simple.news.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.AsyncRestTemplate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@EnableAsync
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Value("${newsapi.url}")
    private String url;

    private MockRestServiceServer mockServer;

    @Before
    public void setup() {
        this.mockServer = MockRestServiceServer.createServer(asyncRestTemplate);
    }

    @Test
    public void given_ValidLangAndCategory_When_GetNews_Then_ReturnOK() throws Exception {
        // given
        this.mockServer
                .expect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockResponseBody(), MediaType.APPLICATION_JSON));

        String lang = "pl";
        String category = "technology";

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(String.format("/news/%s/%s", lang, category)))
                .andReturn();

        // then
        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.country", is("Poland")))
                .andExpect(jsonPath("$.category", is("technology")))
                .andExpect(jsonPath("$.articles", hasSize(2)))
                .andExpect(jsonPath("$.articles[0].author", notNullValue()))
                .andExpect(jsonPath("$.articles[0].title", notNullValue()))
                .andExpect(jsonPath("$.articles[0].description", notNullValue()))
                .andExpect(jsonPath("$.articles[0].date", notNullValue()))
                .andExpect(jsonPath("$.articles[0].sourceName", notNullValue()))
                .andExpect(jsonPath("$.articles[0].articleUrl", notNullValue()))
                .andExpect(jsonPath("$.articles[0].imageUrl", notNullValue()))
                .andExpect(jsonPath("$.articles[1].author", notNullValue()))
                .andExpect(jsonPath("$.articles[1].title", notNullValue()))
                .andExpect(jsonPath("$.articles[1].description", notNullValue()))
                .andExpect(jsonPath("$.articles[1].date", notNullValue()))
                .andExpect(jsonPath("$.articles[1].sourceName", notNullValue()))
                .andExpect(jsonPath("$.articles[1].articleUrl", notNullValue()))
                .andExpect(jsonPath("$.articles[1].imageUrl", notNullValue()));
    }

    @Test
    public void given_InvalidLangOrCategory_When_GetNews_Then_ReturnOKWithNoArticles() throws Exception {
        // given
        this.mockServer
                .expect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockResponseBodyWithoutArticles(), MediaType.APPLICATION_JSON));

        String lang = "invalidLang";
        String category = "invalidCategory";

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(String.format("/news/%s/%s", lang, category)))
                .andReturn();

        // then
        mockMvc.perform(asyncDispatch(result))
                .andDo(x -> System.out.println((x.getResponse().getContentAsString())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.country", nullValue()))
                .andExpect(jsonPath("$.category", nullValue()))
                .andExpect(jsonPath("$.articles", hasSize(0)));
    }

    @Test
    public void given_ApiDoesNotRespondOk_When_GetNews_Then_ReturnServiceUnavailable() throws Exception {
        // given
        this.mockServer
                .expect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        String lang = "pl";
        String category = "technology";

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(String.format("/news/%s/%s", lang, category)))
                .andReturn();

        // then
        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isServiceUnavailable());
    }

    private String mockResponseBody() {
        return "{" +
                    "\"status\":\"ok\"," +
                    "\"totalResults\":2," +
                    "\"articles\":[" +
                        "{\"source\":{\"id\":\"srcId1\",\"name\":\"srcName1\"},\"author\":\"author1\",\"title\":\"title1\",\"description\":\"desc1\",\"url\":\"https://url1\",\"urlToImage\":\"https://urlToImg1\",\"publishedAt\":\"2018-05-16T20:01:00Z\"}," +
                        "{\"source\":{\"id\":\"srcId2\",\"name\":\"srcName2\"},\"author\":\"author2\",\"title\":\"title2\",\"description\":\"desc2\",\"url\":\"https://url2\",\"urlToImage\":\"https://urlToImg2\",\"publishedAt\":\"2018-05-16T18:00:00Z\"}" +
                    "]" +
                "}";
    }

    private String mockResponseBodyWithoutArticles() {
        return "{" +
                    "\"status\":\"ok\"," +
                    "\"totalResults\":2," +
                    "\"articles\":[]" +
                "}";
    }

}