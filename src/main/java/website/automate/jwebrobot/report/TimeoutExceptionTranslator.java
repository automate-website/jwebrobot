package website.automate.jwebrobot.report;

import static java.text.MessageFormat.format;

import org.openqa.selenium.TimeoutException;

import website.automate.jwebrobot.context.ExceptionContext;
import website.automate.waml.io.model.ActionType;
import website.automate.waml.io.model.action.Action;
import website.automate.waml.io.model.action.FilterAction;
import website.automate.waml.report.io.model.ExecutionStatus;

public class TimeoutExceptionTranslator implements ExceptionReportMessageTranslator<TimeoutException> {

	@Override
	public Class<TimeoutException> getSupportedType() {
		return TimeoutException.class;
	}

	@Override
	public String translateToMessage(Throwable e, ExceptionContext context) {
		Action action = context.getAction();
		if(action != null){
			ActionType actionType = ActionType.findByClazz(action.getClass());
			if(action instanceof FilterAction){
				FilterAction filterAction = (FilterAction)action;
				return format("Timeout exceeded while waiting for element using selector:{1}, text:{2} and value:{3} to perform {0} action.", 
						actionType.getName().toLowerCase(),
						filterAction.getSelector(),
						filterAction.getText(),
						filterAction.getValue());
			}
		}
		return e.getMessage();
	}

	@Override
	public ExecutionStatus translateToStatus(Throwable e, ExceptionContext context) {
		return ExecutionStatus.FAILURE;
	}

}
