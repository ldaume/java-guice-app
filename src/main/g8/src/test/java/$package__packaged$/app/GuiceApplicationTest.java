package $package$.app;

import com.google.inject.Inject;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import $package$.guice.application.ConfigTestUtils;
import $package$.guice.application.module.AppModule;
import software.reinvent.log.InjectLogger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

/**
 * Created by lenny on 02.01.17.
 */
@RunWith(JukitoRunner.class)
@UseModules(AppModule.class)
public class GuiceApplicationTest {

    @Inject
    private Application application;

    @Inject
    private Config config;

    @InjectLogger
    private org.slf4j.Logger injectedLogger;

    private Logger rootLogger;

    @Inject
    private Appender mockedAppender;


    private ArgumentCaptor<Appender> argumentCaptor = ArgumentCaptor.forClass(Appender.class);

    @Before
    public void setUp() throws Exception {
        rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(mockedAppender);
    }

    @After
    public void tearDown() throws Exception {
        rootLogger.detachAppender(mockedAppender);
    }

    @Test
    public void failStart() throws Exception {
        assertThatThrownBy(() -> application.start()).isInstanceOf(ConfigException.class)
                                                     .hasMessage("No configuration setting found for key 'hello'");
    }

    @Test
    public void startWithHelloWorldLog() throws Exception {
        final String backedUppedConfigValue = getConfigValue(AppConfigConsts.HELLO_WORLD_NAME);
        addConfigEntry(AppConfigConsts.HELLO_WORLD_NAME, "Test");

        application.start();

        verify(mockedAppender).doAppend(argumentCaptor.capture());
        assertThat(((LoggingEvent) argumentCaptor.getAllValues().get(0)).getFormattedMessage()).isEqualTo("Hello Test.");

        addConfigEntry(AppConfigConsts.HELLO_WORLD_NAME, backedUppedConfigValue);
    }

    private void addConfigEntry(String path, String backedUppedConfigValue) throws NoSuchFieldException, IllegalAccessException {
        ConfigTestUtils.addConfigEntry(path, backedUppedConfigValue, config);
    }

    private String getConfigValue(String path) {
        String backedUppedConfigValue = null;
        if (config.hasPath(path)) {
            backedUppedConfigValue = config.getString(path);
        }
        return backedUppedConfigValue;
    }
}
