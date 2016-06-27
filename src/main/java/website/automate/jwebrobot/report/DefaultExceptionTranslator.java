package website.automate.jwebrobot.report;

import website.automate.waml.report.io.model.ExecutionStatus;

public class DefaultExceptionTranslator implements ExceptionReportMessageTranslator<Exception>{

	@Override
	public Class<Exception> getSupportedType() {
		return Exception.class;
	}

	@Override
	public String translateToMessage(Throwable e) {
		return e.getMessage();
	}

	@Override
	public ExecutionStatus translateToStatus(Throwable e) {
		return ExecutionStatus.ERROR;
	}

}
