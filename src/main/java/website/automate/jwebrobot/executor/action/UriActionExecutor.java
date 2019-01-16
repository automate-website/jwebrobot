package website.automate.jwebrobot.executor.action;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.jwebrobot.executor.ActionExecutorUtils;
import website.automate.jwebrobot.executor.ActionResult;
import website.automate.jwebrobot.executor.action.uri.*;
import website.automate.waml.io.model.main.action.UriAction;
import website.automate.waml.io.model.main.criteria.UriCriteria;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class UriActionExecutor extends BaseActionExecutor<UriAction> {

    private FileResourceManager fileResourceManager = new FileResourceManager();

    private BodyFormatMediaTypeConverter bodyFormatMediaTypeConverter = new BodyFormatMediaTypeConverter();

    private UriRestTemplateProvider restTemplateProvider = new UriRestTemplateProvider();

    private ResultStatusOperator resultStatusOperator = new ResultStatusOperator();

    private HeadersConverter headersConverter = new HeadersConverter();

    @Override
    public void execute(UriAction action,
                        ScenarioExecutionContext context,
                        ActionResult result,
                        ActionExecutorUtils utils) {

        UriCriteria criteria = action.getUri();
        HttpHeaders headers = headersConverter.convert((Map<String, String>)criteria.getHeaders());
        RestTemplate restTemplate = restTemplateProvider.get(criteria);
        String url = criteria.getUrl();
        Object body = getBody(criteria);
        HttpMethod method = getMethod(criteria);
        MediaType mediaType = bodyFormatMediaTypeConverter.convert(criteria.getBodyFormat());
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
            resultStatusOperator.apply(e, result);
            return;
        } catch (RestClientException e){
            resultStatusOperator.apply(e, result);
            return;
        }

        resultStatusOperator.apply(expectedStatus, status, result);

        String filePath = fileResourceManager.save(criteria, url, responseEntity, context.getGlobalContext());

        result.setValue(createResultValue(responseEntity, filePath));
    }

    private UriResultValue createResultValue(ResponseEntity<Object> responseEntity, String filePath){
        return new UriResultValue(responseEntity.getHeaders(),
            responseEntity.getStatusCode(),
            responseEntity.getBody(),
            filePath);
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

    private Object getBody(UriCriteria criteria){
        if(isFileUpload(criteria)){
            return getSrcAsBody(criteria.getSrc());
        }
        return criteria.getBody();
    }

    @Override
    public Class<UriAction> getSupportedType() {
        return UriAction.class;
    }

}
