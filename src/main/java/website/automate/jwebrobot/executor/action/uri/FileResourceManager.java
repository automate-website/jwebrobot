package website.automate.jwebrobot.executor.action.uri;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import website.automate.jwebrobot.context.GlobalExecutionContext;
import website.automate.waml.io.model.main.criteria.UriCriteria;

import java.io.File;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class FileResourceManager {

    public String save(UriCriteria criteria, String url, ResponseEntity<Object> responseEntity,
                       GlobalExecutionContext context){

        if(responseEntity != null){
            String dest = criteria.getDest();
            Object response = responseEntity.getBody();

            if(response instanceof byte []){
                File file;
                if(isNotBlank(dest)){
                    file = new File(dest);
                    writeByteArrayToFile(file, (byte[])response);
                    return file.getAbsolutePath();
                } else {
                    HttpHeaders httpHeaders = responseEntity.getHeaders();
                    File tempDir = context.getTempDir();
                    String fileName = getFileName(httpHeaders, url);
                    file = createTempFile(tempDir, fileName);
                    writeByteArrayToFile(file, (byte[])response);
                    return file.getAbsolutePath();
                }
            }
        }

        return null;
    }

    private void writeByteArrayToFile(File file, byte[] data){
        try {
            FileUtils.writeByteArrayToFile(file, (byte[])data);
        } catch (Exception e){
            throw new RuntimeException(format("Can not write to file {0}.", file), e);
        }
    }

    private String getFileName(HttpHeaders httpHeaders, String url){
        ContentDisposition contentDisposition = httpHeaders.getContentDisposition();
        String fileName = null;

        if(contentDisposition != null){
            fileName = contentDisposition.getFilename();
        }

        if(isBlank(fileName)){
            return FilenameUtils.getName(url);
        } else {
            return fileName;
        }
    }

    private File createTempFile(File tempDir, String fileName){
        if(isBlank(fileName)){
            try {
                return File.createTempFile("jwebrobot", null, tempDir);
            } catch (Exception e){
                throw new RuntimeException(format("Can not create temp file within {0}.", tempDir), e);
            }
        } else {
            return new File(tempDir, fileName);
        }
    }
}
