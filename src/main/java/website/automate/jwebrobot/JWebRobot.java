package website.automate.jwebrobot;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;

import website.automate.jwebrobot.config.ActionExecutorModule;
import website.automate.jwebrobot.config.ActionMapperModule;
import website.automate.jwebrobot.config.CriterionMapperModule;
import website.automate.jwebrobot.config.logger.LoggerModule;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.loader.ScenarioLoader;

import java.util.List;

/**
 * Hello world!
 *
 */
public class JWebRobot {

    public static void main(String[] args) {
        ConfigurationProperties configurationProperties = new ConfigurationProperties();
        new JCommander(configurationProperties, args);

        System.out.println("Executing: " + configurationProperties.getScenarioFilename());

        Injector injector = configureModules();
        ScenarioLoader scenarioLoader = injector.getInstance(ScenarioLoader.class);
        ScenarioExecutor scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        List<ScenarioFile> scenarioFiles = scenarioLoader.load(configurationProperties.getScenarioFilename());
        GlobalExecutionContext globalContext = new GlobalExecutionContext(scenarioFiles, new ExecutorOptions());
        
        scenarioExecutor.execute(globalContext);

    }

    public static Injector configureModules() {
        Injector injector = Guice.createInjector(
            new LoggerModule(),
            new ActionMapperModule(),
            new CriterionMapperModule(),
            new ActionExecutorModule()
        );

        return injector;
    }
}
