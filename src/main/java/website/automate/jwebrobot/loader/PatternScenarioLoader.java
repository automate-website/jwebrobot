package website.automate.jwebrobot.loader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.waml.io.model.main.Scenario;
import website.automate.waml.io.reader.WamlReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.text.MessageFormat.format;

@Service
public class PatternScenarioLoader implements ScenarioLoader {

    private static final String TEMPLATE_SCENARIO_LOAD_FAIL_LOG_MESSAGE = "error: {0} > {1}";

    private static final Logger LOGGER = LoggerFactory.getLogger(PatternScenarioLoader.class);

    private final WamlReader wamlReader;

    private final FileFilterProvider fileFilterProvider;

    @Autowired
    public PatternScenarioLoader(WamlReader wamlReader,
                                 FileFilterProvider fileFilterProvider){
        this.wamlReader = wamlReader;
        this.fileFilterProvider = fileFilterProvider;
    }

    @Override
    public List<ScenarioFile> load(Collection<String> scenarioPaths, String reportPath){
        List<ScenarioFile> scenarioFiles = new ArrayList<>();
        
        for(String scenarioPath : scenarioPaths){
            scenarioFiles.addAll(load(scenarioPath, reportPath));
        }
        
        return scenarioFiles;
    }
    
    private List<ScenarioFile> load(String scenarioPath, String reportPath){
        String currentPath = scenarioPath;
        List<ScenarioFile> loadedScenarioFiles = new ArrayList<>();

        try {
            File baseScenarioFile = new File(scenarioPath);
            File reportFile = new File(reportPath);
            String reportCanonicalPath = reportFile.exists() ? reportFile.getCanonicalPath() : null;
            
            if(baseScenarioFile.canRead()){
                if(baseScenarioFile.isDirectory()){
                    Collection<File> scenarioFiles = FileUtils.listFiles(
                        baseScenarioFile,
                        fileFilterProvider.getFileFilter(baseScenarioFile),
                        fileFilterProvider.getDirFilter(baseScenarioFile)
                    );
                    for(File scenarioFile : scenarioFiles){
                        currentPath = scenarioFile.getAbsolutePath();
                        addScenarioFile(reportCanonicalPath, scenarioFile, loadedScenarioFiles);
                    }
                } else {
                        addScenarioFile(reportCanonicalPath, baseScenarioFile, loadedScenarioFiles);
                }
            } else {
                throw new NonReadableFileException(currentPath);
            }
        } catch (IOException e) {
            throw new NonReadableFileException(currentPath, e);
        }

        return loadedScenarioFiles;
    }

    private void addScenarioFile(String reportCanonicalPath, File scenarioFile, List<ScenarioFile> loadedScenarioFiles) throws IOException{
        if(!scenarioFile.getCanonicalPath().equals(reportCanonicalPath)){
           loadedScenarioFiles.add(new ScenarioFile(load(scenarioFile), scenarioFile));
        }
    }
    
    private Scenario load(File scenarioFile) {
        try {
            LOGGER.debug(format("Reading scenario file {0} ...", scenarioFile.getAbsolutePath()));
            return wamlReader.read(scenarioFile);
        } catch (Exception e){
            LOGGER.error(format(TEMPLATE_SCENARIO_LOAD_FAIL_LOG_MESSAGE,
                scenarioFile.getName(),
                "Failed to parse scenario."));
            throw e;
        }
    }
}
