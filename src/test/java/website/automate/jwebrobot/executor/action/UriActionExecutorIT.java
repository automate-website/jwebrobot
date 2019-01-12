package website.automate.jwebrobot.executor.action;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.Collections.singletonMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.UriAction;
import website.automate.waml.io.model.main.criteria.UriCriteria;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UriActionExecutorIT {

    @ClassRule
    public static final WireMockRule SERVER = new WireMockRule(8888);

    @Configuration
    public static class UriActionExecutorITConfig {

        @Bean
        public UriActionExecutor uriActionExecutor(){
            return new UriActionExecutor();
        }

        @Bean
        public ObjectMapper objectMapper(){
            return new ObjectMapper();
        }
    }

    @MockBean
    private ScenarioExecutionContext context;

    @MockBean
    private ActionExecutorUtils utils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UriActionExecutor executor;

    @Test
    public void jsonGetOk(){
        SERVER.stubFor(
            get(urlEqualTo("/json/get"))
                .withHeader("X-Token", equalTo("foo"))
            .willReturn(aResponse()
                .withBodyFile("action/uri/get.json")
                .withHeader("Content-Type", "application/json")
            )
        );
        UriAction action = new UriActionBuilder()
            .withUrl("http://localhost:8888/json/get")
            .withHeader("X-Token", "foo")
            .build();
        ActionResult result = new ActionResult();

        executor.execute(action, context, result, utils);

        Object object = result.getValue();
        assertThat(object, is(singletonMap("foo", "bar")));
    }

    @Test
    @Ignore
    public void jsonPostOk() throws Exception {
        SERVER.stubFor(
            post(urlEqualTo("/json/post/ok"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(singletonMap("foo", "bar"))))
                .willReturn(aResponse()
                    .withBodyFile("action/uri/get.json")
                    .withHeader("Content-Type", "application/json")
                )
        );
        UriAction action = new UriActionBuilder()
            .withUrl("http://localhost:8888/json/post/ok")
            .build();
        ActionResult result = new ActionResult();

        executor.execute(action, context, result, utils);

        Object object = result.getValue();
        assertThat(object, is(singletonMap("foo", "bar")));
    }

    @Test
    public void jsonGetInternalServerError(){
        SERVER.stubFor(get(urlEqualTo("/json/get/error"))
            .willReturn(aResponse()
                .withStatus(500)
            )
        );
        UriAction action = new UriActionBuilder()
            .withUrl("http://localhost:8888/json/get/error")
            .build();
        ActionResult result = new ActionResult();

        executor.execute(action, context, result, utils);

        Object object = result.getValue();
        assertNull(object);
        assertThat(result.getMessage(), is("Request has failed with status 500."));
    }


    public class UriActionBuilder {

        private String url;

        private Map<String, String> headers = new HashMap<>();

        UriActionBuilder withUrl(String url){
            this.url = url;
            return this;
        }

        UriActionBuilder withHeader(String key, String value){
            this.headers.put(key, value);
            return this;
        }

        UriAction build(){
            UriAction action = new UriAction();
            UriCriteria criteria = new UriCriteria();
            action.setUri(criteria);

            criteria.setUrl(url);
            criteria.setHeaders(headers);

            return action;
        }
    }
}
