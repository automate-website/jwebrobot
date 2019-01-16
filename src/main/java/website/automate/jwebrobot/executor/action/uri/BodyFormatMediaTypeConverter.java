package website.automate.jwebrobot.executor.action.uri;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;

import static java.text.MessageFormat.format;

public class BodyFormatMediaTypeConverter implements Converter<String, MediaType> {

    @Override
    public MediaType convert(String bodyFormat) {
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
}
