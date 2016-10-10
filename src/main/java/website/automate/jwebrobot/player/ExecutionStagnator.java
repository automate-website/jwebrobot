package website.automate.jwebrobot.player;

import static java.util.Arrays.asList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.google.inject.Singleton;

@Singleton
public class ExecutionStagnator {

    public static final char NEXT = 'n';
    public static final char CONTINUE = 'c';
    public static final char STOP = 's';
    
    private static final List<Character> VALID_COMMANDS = asList(NEXT, CONTINUE, STOP);
    
    private char command = CONTINUE;
    
    private Semaphore lock = new Semaphore(0);
    
    public void executeCommand(char command){
        if(command == NEXT || command == CONTINUE){
            lock.release();
        }
        this.command = command;
    }
    
    public void pauseIfRequired(){
        if(command == CONTINUE){
            return;
        } else {
            try {
                lock.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to acquire the lock due to interruption", e);
            }
        }
    }

    public char getCommand() {
        return command;
    }
    
    public boolean isValid(char command){
        return VALID_COMMANDS.contains(command);
    }
}
