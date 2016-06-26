package website.automate.jwebrobot.report;

import website.automate.jwebrobot.context.ExceptionContext;
import website.automate.waml.report.io.model.ExecutionStatus;

public interface ExceptionReportMessageTranslator<T extends Throwable> {

	Class<T> getSupportedType();
	
	String translateToMessage(Throwable e, ExceptionContext context);
	
	ExecutionStatus translateToStatus(Throwable e, ExceptionContext context);
}
