package website.automate.jwebrobot.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.models.factories.ScenarioFactory;

public class ScenarioLoader {

    private static final IOFileFilter SCENARIO_FORMAT_FILTER = new WildcardFileFilter(new String[] {"**.yaml", "**.json"}); 
    
    private static final Logger LOG = LoggerFactory.getLogger(ScenarioLoader.class);
    
    private ScenarioFactory scenarioFactory;

    @Inject
    public ScenarioLoader(ScenarioFactory scenarioFactory){
        this.scenarioFactory = scenarioFactory;
    }
    
    public List<ScenarioFile> load(String scenarioPath){
        String currentPath = scenarioPath;
        List<ScenarioFile> loadedScenarioFiles = new ArrayList<>();
        
        try {
            File baseScenarioFile = new File(scenarioPath);
            if(baseScenarioFile.canRead()){
                if(baseScenarioFile.isDirectory()){
                    Collection<File> scenarioFiles = FileUtils.listFiles(baseScenarioFile, SCENARIO_FORMAT_FILTER, TrueFileFilter.INSTANCE);
                    for(File scenarioFile : scenarioFiles){
                        currentPath = scenarioFile.getAbsolutePath();
                        loadedScenarioFiles.add(new ScenarioFile(load(scenarioFile), scenarioFile));
                    }
                } else {
                    loadedScenarioFiles.add(new ScenarioFile(load(baseScenarioFile), baseScenarioFile));
                }
            } else {
                throw new NonReadableFileException(currentPath);
            }
        } catch (FileNotFoundException e) {
            throw new NonReadableFileException(currentPath, e);
        }
        
        return loadedScenarioFiles;
    }
    
    private List<Scenario> load(File scenarioFile) throws FileNotFoundException{
        LOG.debug(MessageFormat.format("Reading scenario file {0} ...", scenarioFile.getAbsolutePath()));
        return scenarioFactory.createFromInputStream(new FileInputStream(scenarioFile));
    }
}
