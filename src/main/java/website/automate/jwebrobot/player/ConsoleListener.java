package website.automate.jwebrobot.player;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import website.automate.jwebrobot.GuiceInjector;

public class ConsoleListener implements Runnable {

    private volatile boolean shutdown = false;
    
    private static ConsoleListener INSTANCE;
    
    private static ExecutorService EXECUTOR_SERVICE;
    
    private static final String STANDARD_CONSOLE_APPENDER = "STDOUT";
    private static final char NOOP  = '0';
    private static final char QUIT  = 'q';
  
    private static final String AVAILABLE_COMMANDS = "Next(N|n), Continue(C|c), Stop(S|s):";
    
    private ExecutionStagnator player;
    
    @Inject
    public ConsoleListener(ExecutionStagnator player) {
        super();
        this.player = player;
    }
    
    @Override
    public void run() {
        char command = NOOP;
        Scanner scanner = new Scanner(System.in);
        
        printAvailableCommands();
        
        while( command != QUIT && !shutdown){
            if(scanner.hasNextLine()){
                command = scanner.nextLine().charAt(0);
                if(player.isValid(command)){
                    player.executeCommand(command);
                    printAvailableCommands();
                }
            }
        }

        scanner.close();
        EXECUTOR_SERVICE.shutdown();
    }
    
    private static void disableConsoleAppender(){
        Logger.getRootLogger().removeAppender(STANDARD_CONSOLE_APPENDER);
    }
    
    private void printAvailableCommands(){
        System.out.println(AVAILABLE_COMMANDS);
    }
    
    private void printSuccessMessage(){
        System.out.println("Execution completed. Enter any key to exit...");
    }
    
    public static ConsoleListener getInstance(){
        if(INSTANCE == null){
            ConsoleListener consoleListener = GuiceInjector.getInstance().getInstance(ConsoleListener.class);
            EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);
            EXECUTOR_SERVICE.execute(consoleListener);
            
            disableConsoleAppender();
            
            INSTANCE = consoleListener;
        }
        return INSTANCE;
    }
    
    public ExecutionStagnator getPlayer() {
        return player;
    }

    public void setPlayer(ExecutionStagnator player) {
        this.player = player;
    }
    
    public void shutdown() {
        this.shutdown = true;
        printSuccessMessage();
    }
}
