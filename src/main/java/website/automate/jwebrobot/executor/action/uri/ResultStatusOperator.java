package website.automate.jwebrobot.executor.action.uri;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import website.automate.jwebrobot.executor.ActionResult;

import java.util.Collection;

import static java.text.MessageFormat.format;

public class ResultStatusOperator {

    public void apply(RestClientException e, ActionResult result){
        result.setFailed(true);
        result.setMessage(e.getMessage());
        result.setError(e);
    }

    public void apply(RestClientResponseException e, ActionResult result){
        result.setFailed(true);
        result.setMessage(e.getStatusText());
        result.setError(e);
    }

    public void apply(Collection<HttpStatus> expected, HttpStatus actual, ActionResult result){
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

}
