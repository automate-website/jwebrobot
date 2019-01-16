package website.automate.jwebrobot.executor.action.uri;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import website.automate.waml.io.model.main.criteria.UriCriteria;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class UriRestTemplateProvider {

    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    public RestTemplate get(UriCriteria criteria){
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
}
