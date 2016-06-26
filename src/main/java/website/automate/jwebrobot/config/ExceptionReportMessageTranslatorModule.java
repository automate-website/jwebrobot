package website.automate.jwebrobot.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import website.automate.jwebrobot.report.DefaultExceptionTranslator;
import website.automate.jwebrobot.report.ExceptionReportMessageTranslator;
import website.automate.jwebrobot.report.TimeoutExceptionTranslator;


public class ExceptionReportMessageTranslatorModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ExceptionReportMessageTranslator<? extends Throwable>> exceptionReportMessageTranslatorBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ExceptionReportMessageTranslator<? extends Throwable>>() {});

        exceptionReportMessageTranslatorBinder.addBinding().to(DefaultExceptionTranslator.class);
        exceptionReportMessageTranslatorBinder.addBinding().to(TimeoutExceptionTranslator.class);
    }
}
