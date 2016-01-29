package website.automate.jwebrobot.validator;

import java.util.HashSet;
import java.util.Set;

import website.automate.jwebrobot.context.GlobalExecutionContext;

import com.google.inject.Inject;

public class ContextValidators {

    private Set<ContextValidator> validators = new HashSet<>();
    
    @Inject
    public ContextValidators(Set<ContextValidator> validators){
        this.validators = validators;
    }
    
    public void validate(GlobalExecutionContext context){
        for(ContextValidator validator : validators){
            validator.validate(context);
        }
    }
}
