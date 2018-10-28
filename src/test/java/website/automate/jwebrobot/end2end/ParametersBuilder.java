package website.automate.jwebrobot.end2end;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.net.URL;
import java.util.*;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;

public class ParametersBuilder {

    private boolean scanBaseDirForDependencies = false;

    private String basePath;

    private List<String> arguments = Collections.emptyList();

    private Class<? extends Throwable> error;

    private Map<String, String> context = new HashMap<>();

    public ParametersBuilder(String basePath){
        super();
        this.basePath = basePath;
    }

    public ParametersBuilder withArguments(List<String> arguments){
        validateArguments(arguments);
        this.arguments = arguments;
        return this;
    }

    public ParametersBuilder withError(Class<? extends Throwable> error){
        this.error = error;
        return this;
    }

    public ParametersBuilder withScanBaseDirForDependencies(boolean scanBaseDirForDependencies){
        this.scanBaseDirForDependencies = scanBaseDirForDependencies;
        return this;
    }

    public ParametersBuilder withContextEntry(String key, String value){
        context.put(key, value);
        return this;
    }

    private void validateArguments(List<String> arguments){
        if(arguments.contains("-context")){
            throw new IllegalArgumentException("Argument -context can not be specified here! Use withContextEntry instead.");
        }
    }

    public ParametersBuilder withContextBaseUrl(String baseUrl){
        return withContextEntry("baseUrl", baseUrl);
    }

    public Collection<Object[]> build() {
        Collection<File> scenarioFiles = getSamples(basePath);
        Collection<Object[]> parameters = new ArrayList<>();

        for(File scenarioFile : scenarioFiles){
            String testName = getTestName(scenarioFile);
            List<String> arguments;

            if(scanBaseDirForDependencies){
                File scenarioBaseDir = scenarioFile.getParentFile();
                arguments = generateArguments(scenarioBaseDir);
            } else {
                arguments = generateArguments(scenarioFile);
            }

            parameters.add(asObjects(testName,
                arguments,
                error));
        }

        return parameters;
    }

    private String getTestName(File scenarioFile){
        return scenarioFile.getName();
    }

    private Object[] asObjects(Object ... objects){
        return objects;
    }

    private List<String> generateArguments(File scenarioFile){
        String webDriverUrl = "http://localhost:44441/wd/hub";

        List<String> allArguments = new ArrayList<>(asList("-scenarioPath",
            scenarioFile.getAbsolutePath(),
            "-browserDriverUrl",
            webDriverUrl));

        allArguments.addAll(arguments);
        allArguments.addAll(getContextArguments(context));

        return allArguments;
    }

    private List<String> getContextArguments(Map<String, String> context){
        if(context.isEmpty()){
            return Collections.emptyList();
        }

        List<String> contextArguments = new ArrayList<>();

        for(Map.Entry<String, String> contextEntry : context.entrySet()){
            contextArguments.add("-context");
            contextArguments.add(contextEntry.getKey() + "=" + contextEntry.getValue());
        }

        return contextArguments;
    }

    private Collection<File> getSamples(String baseClassPath) {
        File baseFile = getBaseFile(baseClassPath);

        if(baseFile.isDirectory()){
            return FileUtils.listFiles(
                getBaseFile(
                    baseClassPath),
                    new String[] {"yaml", "yml"},
                false);
        }

        return asList(baseFile);
    }

    private File getBaseFile(String baseClassPath) {
        return new File(getAbsoluteScenariosPath(baseClassPath));
    }

    private String getAbsoluteScenariosPath(String baseClassPath) {
        URL resource = ParametersBuilder.class.getClassLoader().getResource(baseClassPath);
        if(resource == null){
            throw new IllegalArgumentException(format("Resource not found at path {0}!", baseClassPath));
        }
        return resource.getPath();
    }
}
