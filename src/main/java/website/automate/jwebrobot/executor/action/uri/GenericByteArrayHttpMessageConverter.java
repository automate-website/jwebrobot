package website.automate.jwebrobot.executor.action.uri;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
