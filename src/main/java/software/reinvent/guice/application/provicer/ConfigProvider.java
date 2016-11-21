package software.reinvent.guice.application.provicer;

import com.google.inject.Provider;

import com.typesafe.config.Config;

import software.reinvent.guice.application.utils.ConfigLoader;

/**
 * @author leonard Daume
 */
public class ConfigProvider implements Provider<Config> {
    @Override
    public Config get() {
        return ConfigLoader.load();
    }
}
