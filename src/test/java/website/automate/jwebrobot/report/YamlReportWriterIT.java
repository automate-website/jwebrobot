package website.automate.jwebrobot.report;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import website.automate.jwebrobot.report.model.ActionReport;
import website.automate.jwebrobot.report.model.ExecutionReport;
import website.automate.jwebrobot.report.model.ExecutionStatus;
import website.automate.jwebrobot.report.model.ScenarioReport;

public class YamlReportWriterIT {

    private static final String REPORT_PATH = "report";
    
    private YamlReportWriter writer = new YamlReportWriter();
    
    @Rule
    public TemporaryFolder directory = new TemporaryFolder();
    
    @Test
    public void reportIsWrittenProperly() throws IOException{
        ExecutionReport report = createExecutionReport(
                asList(createScenarioReport("scenario1", "/scenario.yaml", asList(
                        createActionReport("open", "scenario1", ExecutionStatus.SUCESS, 1.0),
                        createActionReport("click", "scenario1", ExecutionStatus.FAILURE, 2.0)))));
        
        writer.writeReport(getReportPath(), report);
        
        assertEquals(readFileFromClasspath("./website/automate/jwebrobot/report/report.yaml"), readFile(getReportPath() + ".yaml"));
    }
    
    private String getReportPath(){
        return directory.getRoot().getAbsolutePath() + "/" + REPORT_PATH;
    }
    
    private ExecutionReport createExecutionReport(List<ScenarioReport> scenarios){
        ExecutionReport report = new ExecutionReport();
        report.setScenarios(scenarios);
        return report;
    }
    
    private ScenarioReport createScenarioReport(String name, String path, List<ActionReport> actions){
        ScenarioReport report = new ScenarioReport();
        report.setName(name);
        report.setPath(path);
        report.setActions(actions);
        return report;
    }
    
    private ActionReport createActionReport(String name, String path, ExecutionStatus status, Double time){
        ActionReport report = new ActionReport();
        report.setStatus(status);
        report.setTime(time);
        report.setName(name);
        report.setPath(path);
        return report;
    }
    
    private static String readFileFromClasspath(String path) throws IOException{
        InputStream fileStream = ClassLoader.getSystemResourceAsStream(path);
        return IOUtils.toString(fileStream);
    }
    
    private static String readFile(String path) 
            throws IOException 
          {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, "UTF-8");
          }
}
