package website.automate.jwebrobot.report;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

public class ExceptionReportMessageTranslatorFactory {
	
	private final Map<Class<? extends Throwable>, ExceptionReportMessageTranslator<? extends Throwable>> translatorRegistry = new HashMap<>();

    private final Set<ExceptionReportMessageTranslator<? extends Throwable>> translators;

    @Inject
    public ExceptionReportMessageTranslatorFactory(Set<ExceptionReportMessageTranslator<? extends Throwable>> translators) {
        this.translators = translators;

        init();
    }

    private void init() {
        for (ExceptionReportMessageTranslator<? extends Throwable> translator : translators) {
        	translatorRegistry.put(translator.getSupportedType(), translator);
        }
    }
    
    public ExceptionReportMessageTranslator<? extends Throwable> getInstance(Class<? extends Throwable> actionClazz) {
    	ExceptionReportMessageTranslator<? extends Throwable> translator = translatorRegistry.get(actionClazz);
    	if(translator == null){
    		translator = translatorRegistry.get(Exception.class);
    	}
        return translator;
    }
}
