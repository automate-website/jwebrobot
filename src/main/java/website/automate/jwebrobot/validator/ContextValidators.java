package website.automate.jwebrobot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.GlobalExecutionContext;

import java.util.HashSet;
import java.util.Set;

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
