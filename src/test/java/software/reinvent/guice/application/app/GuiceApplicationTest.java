package software.reinvent.guice.application.app;

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
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import software.reinvent.guice.application.ConfigTestUtils;
import software.reinvent.guice.application.module.AppModule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by lenny on 02.01.17.
 */
@RunWith(JukitoRunner.class)
@UseModules(AppModule.class)
public class GuiceApplicationTest {

    @Inject
    Application application;

    @Inject
    private Config config;

    private Logger log = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private Appender<ILoggingEvent> mockAppender;
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Before
    public void setUp(Appender<ILoggingEvent> appender, ArgumentCaptor<LoggingEvent> captor) throws Exception {
        mockAppender = appender;
        this.captorLoggingEvent = captor;

        log.addAppender(mockAppender);
    }

    @After
    public void tearDown() throws Exception {
        log.detachAppender(mockAppender);
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

        verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();
        assertThat(loggingEvent.getFormattedMessage()).isEqualTo("Hello Test.");

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