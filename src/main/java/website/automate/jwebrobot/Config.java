package website.automate.jwebrobot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import website.automate.waml.io.WamlConfig;
import website.automate.waml.io.mappers.ActionMapper;
import website.automate.waml.io.reader.WamlReader;
import website.automate.waml.io.writer.WamlWriter;

@Configuration
public class Config {

  @Bean
  public WamlWriter wamlWriter() {
      return WamlConfig.getInstance().getWamlWriter();
  }
    
  @Bean
  public WamlReader wamlReader() {
    return WamlConfig.getInstance().getWamlReader();
  }
  
  @Bean
  public ActionMapper actionMapper() {
      return WamlConfig.getInstance().getActionMapper();
  }
}
