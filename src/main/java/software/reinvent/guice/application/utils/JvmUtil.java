package software.reinvent.guice.application.utils;

import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;

import humanize.Humanize;


public class JvmUtil {

    public static String getPid() {
        RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
        return rmxb.getName().split("@")[0];
    }


    public static String getMachineName() {
        RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
        return rmxb.getName().split("@")[1];
    }


    public static long getUptime() {
        RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
        return rmxb.getUptime();
    }


    public static TerminateBuilder terminate() {
        return new TerminateBuilder();
    }

    public static void sleep(Duration time) {
        try {
            Thread.sleep(time.toMillis());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Builder to terminate the JVM
     */
    public static class TerminateBuilder {

        private int builderReturnCode;
        private boolean builderThreaded = false;
        private String builderMessage;


        /**
         * Sets the exit value, default is 0 (zero).
         */
        public TerminateBuilder returnCode(int returnCode) {
            builderReturnCode = returnCode;
            return this;
        }


        /**
         * Starts the termination in a separate thread or blocking, default is true.
         */
        public TerminateBuilder threaded(boolean threaded) {
            builderThreaded = threaded;
            return this;
        }


        /**
         * Adds an optional user-defined message to the termination log.
         */
        public TerminateBuilder message(String message) {
            builderMessage = message;
            return this;
        }


        /**
         * Stops the JVM in the specified time
         */
        public void in(Duration time) {
            shutdown(time);
        }


        /**
         * Stops the JVM immediately
         */
        public void now() {
            shutdown(null);
        }


        protected void shutdown(Duration time) {
            if (builderThreaded) {
                Thread thread = new Thread(() -> shutdownFinal(time), "JvmTermination-thread");
                thread.setDaemon(false);
                thread.start();
            }
            else {
                shutdownFinal(time);
            }
        }


        protected void shutdownFinal(Duration time) {
            if (time != null) {
                LoggerFactory.getLogger(JvmUtil.class)
                             .info("The JavaVM will exit in {"
                                   + Humanize.nanoTime(time.toNanos())
                                   + "}, return code will be {"
                                   + builderReturnCode
                                   + "}");
                sleep(time);
            }
            System.exit(builderReturnCode);
        }
    }


}
