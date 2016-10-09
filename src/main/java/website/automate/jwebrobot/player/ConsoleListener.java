package website.automate.jwebrobot.player;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import website.automate.jwebrobot.GuiceInjector;

public class ConsoleListener implements Runnable {

    private static ConsoleListener INSTANCE;
    
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
        
        while( command != QUIT ){
            if(scanner.hasNextLine()){
                command = scanner.nextLine().charAt(0);
                player.executeCommand(command);
                printAvailableCommands();
            }
        }

        scanner.close();
    }
    
    private static void disableConsoleAppender(){
        Logger.getRootLogger().removeAppender(STANDARD_CONSOLE_APPENDER);
    }
    
    private void printAvailableCommands(){
        System.out.println(AVAILABLE_COMMANDS);
    }
    
    public static ConsoleListener getInstance(){
        if(INSTANCE == null){
            ConsoleListener consoleListener = GuiceInjector.getInstance().getInstance(ConsoleListener.class);
            ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.execute(consoleListener);
            
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
}
