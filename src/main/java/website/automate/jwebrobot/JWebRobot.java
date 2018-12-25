package website.automate.jwebrobot;

import com.beust.jcommander.JCommander;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.loader.ScenarioLoader;

import java.util.List;

@SpringBootApplication
public class JWebRobot implements CommandLineRunner {

  private ScenarioLoader scenarioLoader;

  private ScenarioExecutor scenarioExecutor;

  @Autowired
  public JWebRobot(ScenarioLoader scenarioLoader,
      ScenarioExecutor scenarioExecutor) {
    this.scenarioLoader = scenarioLoader;
    this.scenarioExecutor = scenarioExecutor;
  }

  public static void main(String[] args) {
    SpringApplication.run(JWebRobot.class, args);
  }

  private static void bridgeJULToSLF4() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }

  public void run(ConfigurationProperties configurationProperties) {
    List<ScenarioFile> scenarioFiles = scenarioLoader.load(
        configurationProperties.getAllScenarioPaths(), configurationProperties.getReportPath());
    ExecutorOptions executorOptions = ExecutorOptions.of(configurationProperties);
    GlobalExecutionContext globalContext = new GlobalExecutionContext(scenarioFiles,
        executorOptions, configurationProperties.getContext());

    scenarioExecutor.execute(globalContext);
  }

  @Override
  public void run(String... args) throws Exception {
    bridgeJULToSLF4();

    ConfigurationProperties configurationProperties = new ConfigurationProperties();
    new JCommander(configurationProperties, args);

    run(configurationProperties);
  }
}
