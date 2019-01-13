package website.automate.jwebrobot.executor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.waml.io.model.main.action.UriAction;
import website.automate.waml.io.model.main.criteria.UriCriteria;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class UriActionExecutor extends BaseActionExecutor<UriAction> {

    private static final List<MediaType> ACCEPTED_MEDIA_TYPES = asList(
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_JSON_UTF8,
        MediaType.APPLICATION_OCTET_STREAM,
        MediaType.APPLICATION_FORM_URLENCODED,
        MediaType.MULTIPART_FORM_DATA);

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
        HttpHeaders headers = getHeaders(criteria, context, utils);
        RestTemplate restTemplate = getRestTemplate(criteria);
        String url = criteria.getUrl();
        Object body = getBody(criteria, context, utils, headers);
        HttpMethod method = getMethod(criteria);
        MediaType mediaType = bodyFormatToMediaType(criteria);
        Collection<HttpStatus> expectedStatus = getStatus(criteria);
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);

        if(mediaType != null){
            headers.setContentType(mediaType);
        }

        ResponseEntity<Object> responseEntity;
        HttpStatus status;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, Object.class);
            status = responseEntity.getStatusCode();
        } catch (RestClientResponseException e){
            setResultStatus(e, result);
            return;
        } catch (RestClientException e){
            setResultStatus(e, result);
            return;
        }

        setResultStatus(expectedStatus, status, result);
        result.setValue(responseEntity);
    }

    private boolean isFileUpload(UriCriteria criteria){
        return isNotBlank(criteria.getSrc());
    }

    private MultiValueMap<String, Object> getSrcAsBody(String src){
        MultiValueMap<String, Object> body
            = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(new File(src)));
        return body;
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

    private MediaType bodyFormatToMediaType(UriCriteria criteria){
        String bodyFormat = criteria.getBodyFormat();
        if(bodyFormat == null){
            return null;
        }

        switch (bodyFormat.toLowerCase()){
            case "multipart-form-data":
                return MediaType.MULTIPART_FORM_DATA;
            case "form-urlencoded":
                return MediaType.APPLICATION_FORM_URLENCODED;
            case "raw":
                return MediaType.APPLICATION_OCTET_STREAM;
            case "json":
                return MediaType.APPLICATION_JSON_UTF8;
            default:
                throw new RuntimeException(format("Unsupported body format {0}.", bodyFormat));

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
        String src = criteria.getSrc();

        if(isNotBlank(httpMethodStr)){
            return HttpMethod.valueOf(httpMethodStr.toUpperCase());
        } else if(isNotBlank(src)){
            return HttpMethod.POST;
        }
        return HttpMethod.GET;
    }

    private RestTemplate getRestTemplate(UriCriteria criteria){
        String user = criteria.getUser();
        String password = criteria.getPassword();

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(new GenericByteArrayHttpMessageConverter());

        RestTemplateBuilder builder = restTemplateBuilder.errorHandler(new NoopErrorHandler());
        if(isNotBlank(user) || isNotBlank(password)){
            return builder.basicAuthorization(user, password).build();
        }

        builder = builder.additionalMessageConverters(messageConverters);

        builder.configure(restTemplate);

        return restTemplate;
    }

    private Object getBody(UriCriteria criteria,
                           ScenarioExecutionContext context,
                           ActionExecutorUtils utils,
                           HttpHeaders headers){
        if(isFileUpload(criteria)){
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            return getSrcAsBody(criteria.getSrc());
        }
        return utils.getObjectEvaluator().evaluate(criteria.getBody(), context.getTotalMemory(), Object.class);
    }

    @SuppressWarnings("unchecked")
    private HttpHeaders getHeaders(UriCriteria criteria, ScenarioExecutionContext context, ActionExecutorUtils utils){
        Map<String, String> headers = utils
            .getObjectEvaluator()
            .evaluate(
                criteria.getHeaders(),
                context.getTotalMemory(),
                Map.class);

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

    public class GenericByteArrayHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

        public GenericByteArrayHttpMessageConverter() {
            super(new MediaType[]{new MediaType("application", "octet-stream"), MediaType.ALL});
        }

        public boolean supports(Class<?> clazz) {
            return Object.class == clazz;
        }

        @Override
        protected Object readInternal(Class<?> aClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            long contentLength = inputMessage.getHeaders().getContentLength();
            ByteArrayOutputStream bos = new ByteArrayOutputStream(contentLength >= 0L ? (int)contentLength : 4096);
            StreamUtils.copy(inputMessage.getBody(), bos);
            return bos.toByteArray();
        }

        protected Long getContentLength(byte[] bytes, @Nullable MediaType contentType) {
            return (long)bytes.length;
        }

        protected void writeInternal(Object bytes, HttpOutputMessage outputMessage) throws IOException {
            if(bytes instanceof byte[]){
                StreamUtils.copy((byte[])bytes, outputMessage.getBody());
            }
        }
    }
}
