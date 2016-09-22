package website.automate.jwebrobot;

import com.beust.jcommander.JCommander;
import com.google.inject.Injector;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.loader.ScenarioLoader;

import javax.inject.Inject;

import java.util.List;

public class JWebRobot {

    @Inject
    private ScenarioLoader scenarioLoader;

    @Inject
    private ScenarioExecutor scenarioExecutor;

    public JWebRobot() {
    }

    public void run(ConfigurationProperties configurationProperties) {
        List<ScenarioFile> scenarioFiles = scenarioLoader.load(configurationProperties.getAllScenarioPaths(),
                configurationProperties.getReportPath());
        ExecutorOptions executorOptions = ExecutorOptions.of(configurationProperties);
        GlobalExecutionContext globalContext = new GlobalExecutionContext(scenarioFiles, executorOptions,
                configurationProperties.getContext());

        scenarioExecutor.execute(globalContext);
    }

    public static void main(String[] args) {
        ConfigurationProperties configurationProperties = new ConfigurationProperties();
        new JCommander(configurationProperties, args);

        Injector injector = GuiceInjector.getInstance();

        JWebRobot jWebRobot = injector.getInstance(JWebRobot.class);
        jWebRobot.run(configurationProperties);
    }

}
