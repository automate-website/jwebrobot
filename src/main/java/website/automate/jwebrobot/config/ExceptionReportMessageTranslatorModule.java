package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.report.DefaultExceptionTranslator;
import website.automate.jwebrobot.report.ExceptionReportMessageTranslator;


public class ExceptionReportMessageTranslatorModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ExceptionReportMessageTranslator<? extends Throwable>> actionExecutorBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ExceptionReportMessageTranslator<? extends Throwable>>() {});

        actionExecutorBinder.addBinding().to(DefaultExceptionTranslator.class);
    }
}
