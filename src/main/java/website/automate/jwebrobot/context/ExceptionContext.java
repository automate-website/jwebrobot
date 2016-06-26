package website.automate.jwebrobot.context;

import website.automate.waml.io.model.action.Action;

public class ExceptionContext {

	private GlobalExecutionContext globalContext;
	
	private ScenarioExecutionContext scenarioContext;
	
	private Action action;

	public ExceptionContext(GlobalExecutionContext globalContext){
		this.globalContext = globalContext;
	}

	public ExceptionContext(ScenarioExecutionContext scenarioContext){
		this(scenarioContext.getGlobalContext());
		this.scenarioContext = scenarioContext;
	}
	
	public ExceptionContext(ScenarioExecutionContext scenarioContext, Action action){
		this(scenarioContext);
		this.action = action;
	}
	
	public GlobalExecutionContext getGlobalContext() {
		return globalContext;
	}

	public ScenarioExecutionContext getScenarioContext() {
		return scenarioContext;
	}

	public Action getAction() {
		return action;
	}
}
