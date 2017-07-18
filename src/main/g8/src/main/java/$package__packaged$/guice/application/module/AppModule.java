package $package$.guice.application.module;

import com.google.inject.AbstractModule;

import com.typesafe.config.Config;

import $package$.guice.application.app.Application;
import $package$.guice.application.app.GuiceApplication;
import software.reinvent.commons.config.ConfigProvider;
import software.reinvent.log.Slf4jTypeListener;

import static com.google.inject.matcher.Matchers.any;

/**
 * The default application module.
 *
 * @author leonard Daume
 */
public class AppModule extends AbstractModule {


    @Override
    protected void configure() {
        bindListener(any(), new Slf4jTypeListener());
        bind(Config.class).toProvider(ConfigProvider.class).asEagerSingleton();

        bind(Application.class).to(GuiceApplication.class);
    }
}
