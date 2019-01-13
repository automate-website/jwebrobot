package website.automate.jwebrobot.executor.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.UriAction;
import website.automate.waml.io.model.main.criteria.UriCriteria;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.text.MessageFormat.format;
import static java.util.Collections.singletonMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UriActionExecutorIT {

    @ClassRule
    public static final WireMockRule SERVER = new WireMockRule(8888);

    @MockBean
    private ScenarioExecutionContext context;

    @Autowired
    private ActionExecutorUtils utils;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UriActionExecutor executor;

    @Before
    public void init(){
        when(context.getTotalMemory()).thenReturn(singletonMap("foo", "bar"));
    }

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

        ResponseEntity<?> object = ResponseEntity.class.cast(result.getValue());
        assertThat(object.getBody(), is(singletonMap("foo", "bar")));
    }

    @Test
    public void jsonPostOk() throws Exception {
        SERVER.stubFor(
            post(urlEqualTo("/json/post/ok"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(singletonMap("foo", "bar"))))
                .willReturn(aResponse()
                    .withBodyFile("action/uri/post.json")
                    .withHeader("Content-Type", "application/json")
                )
        );
        UriAction action = new UriActionBuilder()
            .withUrl("http://localhost:8888/json/post/ok")
            .withMethod(HttpMethod.POST.name())
            .withBody(singletonMap("foo", "bar"))
            .build();
        ActionResult result = new ActionResult();

        executor.execute(action, context, result, utils);

        ResponseEntity<?> object = ResponseEntity.class.cast(result.getValue());
        assertThat(object.getBody(), is(singletonMap("bar", "foo")));
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

        ResponseEntity<?> object = ResponseEntity.class.cast(result.getValue());
        assertNull(object.getBody());
        assertThat(result.getMessage(), is("Request has failed with status 500."));
    }

    @Test
    public void fileGetOk() throws Exception {
        SERVER.stubFor(
            get(urlEqualTo("/file/get/ok"))
                .willReturn(aResponse()
                    .withBodyFile("action/uri/get.pdf")
                    .withHeader("Content-Type", "application/octet-stream")
                )
        );
        UriAction action = new UriActionBuilder()
            .withUrl("http://localhost:8888/file/get/ok")
            .build();

        ActionResult result = new ActionResult();

        executor.execute(action, context, result, utils);

        ResponseEntity<?> object = ResponseEntity.class.cast(result.getValue());
        assertThat(object.getBody(), is(readFile("__files/action/uri/get.pdf")));

    }

    @Test
    public void filePostOk() throws Exception {
        byte [] fileContent = readFile("__files/action/uri/get.pdf");
        String filePath = getAbsoluteFilePath("__files/action/uri/get.pdf");

        SERVER.stubFor(
            post(urlEqualTo("/file/post/ok"))
                .withMultipartRequestBody(
                    aMultipart()
                        .withName("file")
                       // .withHeader("Content-Type", containing("application/octet-stream"))
                        .withBody(binaryEqualTo(fileContent))
                )
                .willReturn(aResponse()
                    .withStatus(200)
                )
        );
        UriAction action = new UriActionBuilder()
            .withUrl("http://localhost:8888/file/post/ok")
            .withSrc(filePath)
            .build();

        ActionResult result = new ActionResult();

        executor.execute(action, context, result, utils);

        ResponseEntity<?> object = ResponseEntity.class.cast(result.getValue());
        assertThat(object.getStatusCode(), is(HttpStatus.OK));

    }

    private String getAbsoluteFilePath(String path){
        return UriActionExecutorIT.class.getClassLoader().getResource(path).getPath();
    }

    private byte [] readFile(String path){
        try {
            return IOUtils.toByteArray(UriActionExecutorIT.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e){
            throw new RuntimeException(format("Can not read file {0} to byte array.", path), e);
        }
    }

    public class UriActionBuilder {

        private String url;

        private Object body;

        private String method;

        private String bodyFormat;

        private String src;

        private Map<String, String> headers = new HashMap<>();

        UriActionBuilder withUrl(String url){
            this.url = url;
            return this;
        }

        UriActionBuilder withHeader(String key, String value){
            this.headers.put(key, value);
            return this;
        }

        UriActionBuilder withBody(Object body){
            this.body = body;
            return this;
        }

        UriActionBuilder withMethod(String method){
            this.method = method;
            return this;
        }

        UriActionBuilder withBodyFormat(String bodyFormat){
            this.bodyFormat = bodyFormat;
            return this;
        }


        UriActionBuilder withSrc(String src){
            this.src = src;
            return this;
        }

        UriAction build(){
            UriAction action = new UriAction();
            UriCriteria criteria = new UriCriteria();
            action.setUri(criteria);

            criteria.setUrl(url);
            criteria.setHeaders(headers);
            criteria.setBody(body);
            criteria.setMethod(method);
            criteria.setBodyFormat(bodyFormat);
            criteria.setSrc(src);

            return action;
        }
    }
}
