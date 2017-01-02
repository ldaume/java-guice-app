package software.reinvent.guice.application.app;

import com.google.inject.Inject;

import com.typesafe.config.Config;

import org.slf4j.Logger;

import software.reinvent.guice.application.utils.InjectLogger;
import software.reinvent.guice.application.utils.JvmUtil;

/**
 * @author leonard Daume
 */
public class GuiceApplication implements Application {

    @InjectLogger
    private Logger log;

    @Inject
    private Config config;

    @Override
    public void start() {
        log.info("Hello {}.", config.getString(AppConfigConsts.HELLO_WORLD_NAME));
        if (config.hasPath(AppConfigConsts.TERMINATE_APP) && config.getBoolean(AppConfigConsts.TERMINATE_APP)) {
            JvmUtil.terminate().now();
        }
    }
}
