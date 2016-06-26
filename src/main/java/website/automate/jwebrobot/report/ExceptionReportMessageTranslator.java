package website.automate.jwebrobot.report;

import website.automate.waml.report.io.model.ExecutionStatus;

public interface ExceptionReportMessageTranslator<T extends Throwable> {

	Class<T> getSupportedType();
	
	String translateToMessage(Throwable e);
	
	ExecutionStatus translateToStatus(Throwable e);
}
