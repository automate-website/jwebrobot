package website.automate.jwebrobot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import website.automate.waml.io.WamlReader;
import website.automate.waml.report.io.WamlReportWriter;

@Configuration
public class Config {

  @Bean
  public WamlReader wamlReader() {
    return new WamlReader();
  }
  
  @Bean
  public WamlReportWriter wamlReportWriter() {
    return new WamlReportWriter();
  }
}
