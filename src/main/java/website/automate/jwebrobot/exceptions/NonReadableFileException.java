package website.automate.jwebrobot.exceptions;

import java.text.MessageFormat;

public class NonReadableFileException extends RuntimeException {

	private static final long serialVersionUID = -75907572155982505L;

    private String filename;
    
    public NonReadableFileException(String filename){
        super(toMessage(filename));
        this.filename = filename;
    }
    
    public NonReadableFileException(String filename, Throwable e){
        super(toMessage(filename), e);
        this.filename = filename;
    }
    
    private static String toMessage(String filename){
        return MessageFormat.format("Can not read file {0}.", filename);
    
    }

    public String getFilename() {
		return filename;
	}
}
