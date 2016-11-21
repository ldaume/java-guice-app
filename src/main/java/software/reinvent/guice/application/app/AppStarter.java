package software.reinvent.guice.application.app;

import com.google.inject.Guice;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.reinvent.guice.application.module.AppModule;
import software.reinvent.guice.application.utils.JvmUtil;


/**
 * The main class which passes some app arguments to the config and starts the {@link Guice} application.
 *
 * @author leonard Daume
 */
class AppStarter {
    private static final Logger LOG = LoggerFactory.getLogger(AppStarter.class);

    public static void main(String[] args) {
        final AppStarter app = new AppStarter();
        try {
            JCommander jCommander = new JCommander(app);
            jCommander.setAcceptUnknownOptions(true);
            jCommander.parseWithoutValidation(args);
            jCommander.setProgramName("guice-application");
            app.run(jCommander);
        } catch (ParameterException e) {
            LOG.error("{}", e.getMessage());
            JvmUtil.terminate().returnCode(64).now();
        }
    }

    private void run(JCommander jCommander) {
        /*if (help) {
            jCommander.usage();
            JvmUtil.terminate().returnCode(help ? 0 : 64).now();
        }*/

        LOG.info("Will start the application with {}.", this);
        Guice.createInjector(new AppModule())
             .getInstance(Application.class)
             .start();
    }
}
