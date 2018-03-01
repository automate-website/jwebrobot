package website.automate.jwebrobot.loader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.waml.io.model.Scenario;
import website.automate.waml.io.reader.WamlReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PatternScenarioLoader implements ScenarioLoader {

    private static final IOFileFilter SCENARIO_FORMAT_FILTER = new WildcardFileFilter(new String[] {"**.yaml", "**.json"});

    private static final Logger LOG = LoggerFactory.getLogger(PatternScenarioLoader.class);

    private final WamlReader wamlReader;

    @Autowired
    public PatternScenarioLoader(WamlReader wamlReader){
        this.wamlReader = wamlReader;
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
                    Collection<File> scenarioFiles = FileUtils.listFiles(baseScenarioFile, SCENARIO_FORMAT_FILTER, TrueFileFilter.INSTANCE);
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
    
    private List<Scenario> load(File scenarioFile) throws FileNotFoundException{
        LOG.debug(MessageFormat.format("Reading scenario file {0} ...", scenarioFile.getAbsolutePath()));
        return createFromInputStream(new FileInputStream(scenarioFile));
    }

    public List<Scenario> createFromInputStream(InputStream inputStream) {
        List<Scenario> scenarios = wamlReader.read(inputStream);

        LOG.info("Loaded {} scenarios.", scenarios.size());

        return scenarios;
    }
}
