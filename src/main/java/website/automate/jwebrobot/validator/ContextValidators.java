package website.automate.jwebrobot.validator;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.GlobalExecutionContext;

@Service
public class ContextValidators {

    private Set<ContextValidator> validators = new HashSet<>();
    
    @Autowired
    public ContextValidators(Set<ContextValidator> validators){
        this.validators = validators;
    }
    
    public void validate(GlobalExecutionContext context){
        for(ContextValidator validator : validators){
            validator.validate(context);
        }
    }
}
