package website.automate.jwebrobot.executor.action.uri;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class UriResultValue {

    private HttpHeaders headers;

    private HttpStatus status;

    private Object body;

    private String filePath;

    public UriResultValue(HttpHeaders headers,
                          HttpStatus status,
                          Object body,
                          String filePath) {
        this.headers = headers;
        this.status = status;
        this.body = body;
        this.filePath = filePath;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Object getBody() {
        return body;
    }

    public String getFilePath() {
        return filePath;
    }
}
