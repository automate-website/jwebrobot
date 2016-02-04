package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.listener.ExecutionEventListener;
import website.automate.jwebrobot.report.ReportWriter;
import website.automate.jwebrobot.report.Reporter;
import website.automate.jwebrobot.report.YamlReportWriter;
import website.automate.jwebrobot.screenshot.ScreenshotEventListener;

public class ExecutionEventListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ExecutionEventListener> listenerBinder = Multibinder.newSetBinder(binder(), ExecutionEventListener.class);
        
        listenerBinder.addBinding().to(Reporter.class).in(Singleton.class);
        listenerBinder.addBinding().to(ScreenshotEventListener.class).in(Singleton.class);
        
        bind(ReportWriter.class).to(YamlReportWriter.class);
    }
}
