package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;

import website.automate.jwebrobot.loader.ScenarioLoader;
import website.automate.jwebrobot.loader.PatternScenarioLoader;


public class ScenarioLoaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ScenarioLoader.class).to(PatternScenarioLoader.class);
    }
}
