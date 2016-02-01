package website.automate.jwebrobot.loader.impl;

import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.jwebrobot.loader.ScenarioFile;
import website.automate.jwebrobot.loader.ScenarioLoader;
import website.automate.jwebrobot.model.Scenario;
import website.automate.jwebrobot.model.mapper.ScenarioMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScenarioLoaderImpl implements ScenarioLoader {

    private static final IOFileFilter SCENARIO_FORMAT_FILTER = new WildcardFileFilter(new String[] {"**.yaml", "**.json"});

    private static final Logger LOG = LoggerFactory.getLogger(ScenarioLoaderImpl.class);

    private final ScenarioMapper scenarioMapper;

    @Inject
    public ScenarioLoaderImpl(ScenarioMapper scenarioMapper){
        this.scenarioMapper = scenarioMapper;
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
        return createFromInputStream(new FileInputStream(scenarioFile));
    }

    public List<Scenario> createFromInputStream(InputStream inputStream) {
        Yaml yaml = new Yaml();

        Iterable<Object> objects = yaml.loadAll(inputStream);

        List<Scenario> scenarios = scenarioMapper.map(objects);

        LOG.info("Loaded {} scenarios.", scenarios.size());

        return scenarios;
    }
}
