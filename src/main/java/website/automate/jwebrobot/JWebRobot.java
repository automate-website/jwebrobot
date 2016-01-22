package website.automate.jwebrobot;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;
import website.automate.jwebrobot.config.ActionExecutorModule;
import website.automate.jwebrobot.config.ActionMapperModule;
import website.automate.jwebrobot.config.CriterionMapperModule;
import website.automate.jwebrobot.config.logger.LoggerModule;
import website.automate.jwebrobot.executor.ContextHolder;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.models.factories.ScenarioFactory;
import website.automate.jwebrobot.models.scenario.Scenario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Hello world!
 *
 */
public class JWebRobot {

    public static void main(String[] args) throws FileNotFoundException {
        ConfigurationProperties configurationProperties = new ConfigurationProperties();
        new JCommander(configurationProperties, args);

        System.out.println("Executing: " + configurationProperties.getScenarioFilename());

        Injector injector = configureModules();
        ScenarioFactory scenarioFactory = injector.getInstance(ScenarioFactory.class);
        ScenarioExecutor scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        InputStream inputStream = new FileInputStream(configurationProperties.getScenarioFilename());
        List<Scenario> scenarioList = scenarioFactory.createFromInputStream(inputStream);

        scenarioExecutor.execute(scenarioList, new ContextHolder(), new ExecutorOptions());

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
