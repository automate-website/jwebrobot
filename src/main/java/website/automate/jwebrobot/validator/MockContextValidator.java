package website.automate.jwebrobot.validator;

import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.GlobalExecutionContext;

@Service
public class MockContextValidator implements ContextValidator {

    @Override
    public void validate(GlobalExecutionContext context) {
    }

}
