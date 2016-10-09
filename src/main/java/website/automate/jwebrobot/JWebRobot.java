package website.automate.jwebrobot;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.beust.jcommander.JCommander;
import com.google.inject.Injector;

import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.jwebrobot.executor.ExecutorOptions;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.loader.ScenarioLoader;
import website.automate.jwebrobot.player.ConsoleListener;
import website.automate.jwebrobot.player.ExecutionStagnator;

public class JWebRobot {

    @Inject
    private ScenarioLoader scenarioLoader;

    @Inject
    private ScenarioExecutor scenarioExecutor;

    public void run(ConfigurationProperties configurationProperties) {
        List<ScenarioFile> scenarioFiles = scenarioLoader.load(configurationProperties.getAllScenarioPaths(),
                configurationProperties.getReportPath());
        ExecutorOptions executorOptions = ExecutorOptions.of(configurationProperties);
        GlobalExecutionContext globalContext = new GlobalExecutionContext(scenarioFiles, executorOptions,
                configurationProperties.getContext());

        scenarioExecutor.execute(globalContext);
    }
    
    public static void main(String[] args) {
        bridgeJULToSLF4();
        
        ConfigurationProperties configurationProperties = new ConfigurationProperties();
        new JCommander(configurationProperties, args);

        Injector injector = GuiceInjector.getInstance();

        if(configurationProperties.isInteractive()){
            ConsoleListener consoleListener = ConsoleListener.getInstance();
            consoleListener.getPlayer().executeCommand(ExecutionStagnator.STOP);
        }
        
        JWebRobot jWebRobot = injector.getInstance(JWebRobot.class);
        jWebRobot.run(configurationProperties);
        
        System.exit(0);
    }
    
    private static void bridgeJULToSLF4(){
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
