package com.redhat.idaas.connect.config;

import java.util.Properties;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.camel.builder.RouteBuilder;


/**
 * Parses iDAAS Connect Application Properties into configuration elements.
 */
public final class PropertyParser {

    private static final String IDAAS_NAMESPACE_PROPERTY = "idaas.connect.namespace";
    private static final String DEFAULT_IDAAS_PROPERTY_NAMESPACE = "idaas.connect";

    /**
     * the namespace, or prefix, used for idaas connect properties
     */
    private String idaasPropertyNamespace;

    private Properties idaasProperties;

    public List<RouteBuilder> parseRoutes() {
        List<RouteBuilder> routes = new ArrayList<>();
        return routes;
    }

    /**
     * Parses iDAAS components from property settings
     * Components are returned within a Map where:
     * Key - component/bean name registered with Camel
     * Value - the component's full class name
     * @return Map<String, String>
     */
    public Map<String, String> parseComponents() {
        Map<String, String> components = new HashMap<>();

        String componentPrefix = idaasPropertyNamespace.concat(".component.");
        Properties componentProperties = filterProperties(idaasProperties, componentPrefix);

        for (String propertyName : componentProperties.stringPropertyNames()) {
            String componentKey = propertyName.substring(componentPrefix.length());
            String componentPath = componentProperties.getProperty(propertyName);

            components.put(componentKey, componentPath);
        }
        return components;
    }

    /**
     * Returns a property value from the underlying properties object
     * @param propertyKey The property key to lookup
     * @return The property value if found, otherwise returns null
     */
    public String getPropertyValue(String propertyKey) {
        return idaasProperties.getProperty(propertyKey);
    }

    /**
     * Filters properties by matching on a string prefix.
     * @param properties The source Properties
     * @param filterPrefix The prefix used to match the properties
     *
     * @return a Properties instance containing properties which match the string prefix
     */
    private Properties filterProperties(Properties properties, String filterPrefix) {
        Map<Object, Object> filteredProperties = properties
        .entrySet()
        .stream()
        .filter(p -> p.getKey().toString().startsWith(filterPrefix))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Properties idaasProperties = new Properties();
        idaasProperties.putAll(filteredProperties);
        return idaasProperties;
    }

    /**
     * Creates a new PropertyParser instance
     * @param properties The source application properties
     */
    public PropertyParser(Properties properties) {
        idaasPropertyNamespace = properties.getProperty(IDAAS_NAMESPACE_PROPERTY, DEFAULT_IDAAS_PROPERTY_NAMESPACE);
        idaasProperties = filterProperties(properties, idaasPropertyNamespace);
    }
}