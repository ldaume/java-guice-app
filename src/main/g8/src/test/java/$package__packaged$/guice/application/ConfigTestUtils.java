package $package$.guice.application;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by lenny on 02.01.17.
 */
public class ConfigTestUtils {
    private ConfigTestUtils() {
    }

    /**
     * Adds a new value to a specific config. If the value is null, the path will be removed.
     *
     * @param path               the path as key
     * @param value              the value to set or unset
     * @param configToManipulate the config to manipulate
     */
    public static void addConfigEntry(String path,
                                      Object value,
                                      Config configToManipulate) throws NoSuchFieldException, IllegalAccessException {

        Map<String, ConfigValue> refMap = getConfigValueMap(configToManipulate);


        ConfigValueFactory.fromAnyRef(value).atPath(path).root().entrySet().forEach(entry -> {
            if (value == null) {
                refMap.remove(entry.getKey());
            } else {
                refMap.put(entry.getKey(), entry.getValue());
            }
        });
    }

    private static Map<String, ConfigValue> getConfigValueMap(Config configToManipulate) throws NoSuchFieldException, IllegalAccessException {
        final ConfigObject root = configToManipulate.root();
        Field field = configToManipulate.root().getClass().getDeclaredField("value");
        field.setAccessible(true);
        return (Map<String, ConfigValue>) field.get(root);
    }
}
