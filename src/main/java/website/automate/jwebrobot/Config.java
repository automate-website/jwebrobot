package website.automate.jwebrobot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import website.automate.waml.io.WamlConfig;
import website.automate.waml.io.reader.WamlReader;
import website.automate.waml.io.writer.WamlWriter;

@Configuration
public class Config {

  @Bean
  public WamlReader wamlReader() {
    return WamlConfig.getInstance().getWamlReader();
  }
  
  @Bean
  public WamlWriter wamlReportWriter() {
    return WamlConfig.getInstance().getWamlWriter();
  }
}
