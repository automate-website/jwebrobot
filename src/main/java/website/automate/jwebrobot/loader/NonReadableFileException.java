package website.automate.jwebrobot.loader;

import java.text.MessageFormat;

public class NonReadableFileException extends RuntimeException {

    private static final long serialVersionUID = -75907572155982505L;

    public NonReadableFileException(String filename){
        super(toMessage(filename));
    }
    
    public NonReadableFileException(String filename, Throwable e){
        super(toMessage(filename), e);
    }
    
    private static String toMessage(String filename){
        return MessageFormat.format("Can not read file {0}.", filename);
    }
}
