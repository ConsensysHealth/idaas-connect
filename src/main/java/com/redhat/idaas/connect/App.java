package com.redhat.idaas.connect;

import com.redhat.idaas.connect.config.PropertyParser;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Properties;

/**
 * The iDAAS Connect application.
 * 
 * iDAAS Connect provides data integration and processing routes for application integration.
 * Apache Camel is use to provide routing, mediation, and processing services.
 */
public final class App {

    private static final String PROPERTIES_FILE = "application.properties";

    private Logger logger = LoggerFactory.getLogger(App.class);

    private Main camelMain = new Main();

    private Properties properties = new Properties();

    /**
     * Parses a "component map" to add components to the Camel lookup registry.
     * Component map is structured as:
     * - Key - the component name
     * - Value - component class name
     * @param componentMap {@link Map<String, String>}
     * @throws ReflectiveOperationException if an error occurs creating a component instance
     */
    private void addCamelComponents(Map<String, String> componentMap) throws ReflectiveOperationException {
        for (Map.Entry<String, String> mapEntry: componentMap.entrySet()) {
            String className = mapEntry.getValue();
            Constructor<?> componentConstructor = Class.forName(className).getConstructor();
            camelMain.bind(mapEntry.getKey(), componentConstructor.newInstance());
        }
    }

    /**
     * Configures camel settings, components, and routes.
     * @throws ReflectiveOperationException
     */
    private void configureCamel() throws ReflectiveOperationException {
        camelMain.enableHangupSupport();

        PropertyParser propertyParser = new PropertyParser(properties);
        Map<String, String> componentMap = propertyParser.parseComponents();
        addCamelComponents(componentMap);
    }

    /**
     * Loads application properties from the classpath
     * @param propertyFile The properties file name
     * @throws IOException
     */
    private void loadProperties(String propertyFile) throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(propertyFile)) {
            properties.load(inputStream);
        }
    }

    /**
     * Starts the iDAAS Connect application
     * @throws Exception
     */
    private void start() throws Exception {
        loadProperties(PROPERTIES_FILE);
        configureCamel();
        camelMain.start();
    }

    /**
     * Entrypoint for iDAAS Connection Application.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }
}
