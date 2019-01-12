package website.automate.jwebrobot.executor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.UriAction;
import website.automate.waml.io.model.main.criteria.UriCriteria;

import java.io.IOException;
import java.util.*;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class UriActionExecutor extends BaseActionExecutor<UriAction> {

    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public UriActionExecutor(){
        this.restTemplateBuilder = new RestTemplateBuilder();
    }

    @Override
    public void execute(UriAction action, ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {

        UriCriteria criteria = action.getUri();
        HttpHeaders headers = getHeaders(criteria);
        RestTemplate restTemplate = getRestTemplate(criteria);
        HttpMethod method = getMethod(criteria);
        String url = criteria.getUrl();
        Collection<HttpStatus> expectedStatus = getStatus(criteria);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Object> responseEntity;
        Object response;
        HttpStatus status;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, Object.class);
            response = responseEntity.getBody();
            status = responseEntity.getStatusCode();
        } catch (RestClientResponseException e){
            setResultStatus(e, result);
            return;
        } catch (RestClientException e){
            setResultStatus(e, result);
            return;
        }

        setResultStatus(expectedStatus, status, result);
        result.setValue(response);
    }

    private void setResultStatus(RestClientException e, ActionResult result){
        result.setFailed(true);
        result.setMessage(e.getMessage());
        result.setError(e);
    }

    private void setResultStatus(RestClientResponseException e, ActionResult result){
        result.setFailed(true);
        result.setMessage(e.getStatusText());
        result.setError(e);
    }

    private void setResultStatus(Collection<HttpStatus> expected, HttpStatus actual, ActionResult result){
        if(actual == null){
            result.setFailed(true);
        }
        if(expected == null){
            boolean failed = actual.isError();
            result.setFailed(failed);
            if(failed){
                result.setMessage(format("Request has failed with status {0}.", actual));
            }
        } else {
            if(!expected.contains(actual)){
                result.setFailed(true);
                result.setMessage(format("Expected request status {0} but got {1}.", expected, actual));
            }
        }
    }

    private Collection<HttpStatus> getStatus(UriCriteria criteria){
        String statusCode = criteria.getStatusCode();
        if(isNotBlank(statusCode)){
            return Collections.singleton(HttpStatus.valueOf(statusCode));
        }
        return null;
    }

    private HttpMethod getMethod(UriCriteria criteria){
        String httpMethodStr = criteria.getMethod();
        if(isNotBlank(httpMethodStr)){
            return HttpMethod.valueOf(httpMethodStr);
        }
        return HttpMethod.GET;
    }

    private RestTemplate getRestTemplate(UriCriteria criteria){
        String user = criteria.getUser();
        String password = criteria.getPassword();

        RestTemplateBuilder builder = restTemplateBuilder.errorHandler(new NoopErrorHandler());
        if(isNotBlank(user) || isNotBlank(password)){
            return builder.basicAuthorization(user, password).build();
        }

        return builder.build();
    }

    private HttpHeaders getHeaders(UriCriteria criteria){
        Map<String, String> headers = criteria.getHeaders();
        HttpHeaders httpHeaders = new HttpHeaders();

        if(headers == null){
            return httpHeaders;
        }

        for(Map.Entry<String, String> entry : headers.entrySet()){
            httpHeaders.add(entry.getKey(), entry.getValue());
        }

        return httpHeaders;
    }

    public static class NoopErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            // do nothing
        }
    }

    @Override
    public Class<UriAction> getSupportedType() {
        return UriAction.class;
    }
}
