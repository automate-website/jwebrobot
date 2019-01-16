package website.automate.jwebrobot.executor.action.uri;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;

import java.util.Map;

public class HeadersConverter implements Converter<Map<String, String>, HttpHeaders> {

    @Override
    public HttpHeaders convert(Map<String, String> source) {
        HttpHeaders target = new HttpHeaders();

        if(source == null){
            return target;
        }

        for(Map.Entry<String, String> entry : source.entrySet()){
            target.add(entry.getKey(), entry.getValue());
        }

        return target;
    }
}
