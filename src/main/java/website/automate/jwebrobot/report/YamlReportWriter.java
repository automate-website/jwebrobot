package website.automate.jwebrobot.report;

import static java.util.Arrays.asList;

import java.beans.IntrospectionException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import website.automate.jwebrobot.exceptions.NonReadableFileException;
import website.automate.jwebrobot.report.model.ExecutionReport;

public class YamlReportWriter implements ReportWriter {

    private Yaml yaml;

    public YamlReportWriter() {
        init();
    }

    private void init(){
        Representer representer = new SkipNullRepresenter();
        DumperOptions options = new DumperOptions();
        
        representer.addClassTag(ExecutionReport.class, Tag.MAP);
        options.setDefaultFlowStyle(FlowStyle.BLOCK);
        
        yaml  = new Yaml(representer, options);
    }

    @Override
    public void writeReport(String reportPath, ExecutionReport report) {
        String reportFile = reportPath + ".yaml";
        try {
            yaml.dump(report, new FileWriter(reportFile));
        } catch (IOException e) {
            throw new NonReadableFileException(reportFile);
        }
    }

    private static class SkipNullRepresenter extends Representer {
        
        private static final List<String> PROPERTY_ORDER = asList("name", "status", "time", "path", "numScenarioTotal",
                "numScenarioPasses", "numActionPasses", "numScenarioFailures", "numActionFailures", "scenarios", "actions");
        
        @Override
        protected NodeTuple representJavaBeanProperty(Object javaBean, Property property,
                Object propertyValue, Tag customTag) {
            if (propertyValue == null) {
                return null;
            } else {
                return super
                        .representJavaBeanProperty(javaBean, property, propertyValue, customTag);
            }
        }

        @Override
        protected Set<Property> getProperties(Class<? extends Object> type) throws IntrospectionException{
            Set<Property> properties = super.getProperties(type);
            List<Property> sortedProperties = new ArrayList<>(properties);
            
            Collections.sort(sortedProperties, new Comparator<Property>() {

                @Override
                public int compare(Property left, Property right) {
                    int indexLeft = PROPERTY_ORDER.indexOf(left.getName());
                    int indexRight = PROPERTY_ORDER.indexOf(right.getName());
                    
                    return Integer.compare(indexLeft, indexRight);
                }
            });
            
            return new LinkedHashSet<Property>(sortedProperties);
        }
    }
}
