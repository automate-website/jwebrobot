package website.automate.jwebrobot;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static java.text.MessageFormat.format;

class TestUtils {

    static String getWebDriverUrl(){
        return "http://localhost:44441/wd/hub";
    }

    static String readFile(File file) throws Exception {
        return IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
    }

    static Collection<File> getSamples(String baseClassPath) {
        return FileUtils.listFiles(getScenariosDir(baseClassPath), new String[] {"yaml", "yml"},
                false);
    }

    private static File getScenariosDir(String baseClassPath) {
        return new File(getAbsoluteScenariosPath(baseClassPath));
    }

    private static String getAbsoluteScenariosPath(String baseClassPath) {
        URL resource = TestUtils.class.getClassLoader().getResource(baseClassPath);
        if(resource == null){
            throw new IllegalArgumentException(format("Resource not found at path {0}!", baseClassPath));
        }
        return resource.getPath();
    }
}
