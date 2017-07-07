package software.reinvent.guice.application.app;

import com.google.common.base.MoreObjects;
import com.google.inject.Guice;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.reinvent.guice.application.module.AppModule;
import software.reinvent.jvm.JvmUtil;


/**
 * The main class which passes some app arguments to the config and starts the {@link Guice} application.
 *
 * @author leonard Daume
 */
class AppStarter {
    private static final Logger LOG = LoggerFactory.getLogger(AppStarter.class);
    private static JCommander jCommander;

    @Parameter(names = {"--help", "-h"}, help = true, description = "Prints usage information.")
    private boolean help;


    @Parameter(names = {"--name", "-n"}, required = true, description = "Name for hello world log entry.")
    private String name;


    public static void main(String[] args) {
        final AppStarter app = new AppStarter();
        try {
            jCommander = new JCommander(app);
            jCommander.setAcceptUnknownOptions(true);
            jCommander.parseWithoutValidation(args);
            if (jCommander.getCommands().get("help") == null) {
                jCommander.setAllowParameterOverwriting(true);
                jCommander.parse(args);
            }
            jCommander.setProgramName("guice-application");
            app.run(jCommander, args.length);
        } catch (ParameterException e) {
            LOG.error("{}", e.getMessage());
            JvmUtil.terminate().returnCode(64).now();
        }
    }

    private void run(JCommander jCommander, Integer commands) {
        if (help || commands == 0) {
            jCommander.usage();
            JvmUtil.terminate().returnCode(help ? 0 : 64).now();
        }

        addConfigEntries();

        LOG.info("Will start the application with {}.", this);
        Guice.createInjector(new AppModule())
             .getInstance(Application.class)
             .start();
    }

    private void addConfigEntries() {
        System.setProperty(AppConfigConsts.HELLO_WORLD_NAME, name);
    }

    @Override
    public String toString() {
        final MoreObjects.ToStringHelper stringHelper = MoreObjects.toStringHelper(this)
                                                                   .add("commands", jCommander.getCommands())
                                                                   .add("help", help);
        return stringHelper.toString();
    }
}
