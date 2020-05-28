package com.redhat.idaas.connect.configuration;

import java.util.Properties;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Parses iDAAS Connect Application Properties used to generate Camel components, routes, and processors.
 */
public final class PropertyParser {

    private static final String IDAAS_NAMESPACE_PROPERTY = "idaas.connect.namespace";
    private static final String DEFAULT_IDAAS_PROPERTY_NAMESPACE = "idaas.connect";
    private static final String IDAAS_COMPONENT_NAMESPACE = "component";
    private static final String IDAAS_CONSUMER_NAMESPACE = "consumer";
    private static final String IDAAS_PRODUCER_NAMESPACE = "producer";

    /**
     * the namespace, or prefix, used to filter idaas connect properties
     */
    private final String idaasPropertyNamespace;

    /**
     * properties object containing idaas connect properties
     */
    private final Properties idaasProperties;

    /**
     * Maps a component name to it's fully qualified class name
     */
    private final Map<String, String> idaasComponents = new HashMap<>();


    public Map<String, String> getIdaasComponents() {
        return idaasComponents;
    }

    /**
     * Parses the component identifier and value (component class name) from a property name and value.
     * @param propertyName The property name
     * @param propertyValue The property value (component class name)
     */
    private void parseComponent(String propertyName, String propertyValue) {
        int componentNameIndex = propertyName.lastIndexOf('.') + 1;
        String componentKey = propertyName.substring(componentNameIndex);
        idaasComponents.put(componentKey, propertyValue);
    }

    private void parseConsumer(String propertyName, String propertyValue) {

    }

    private void parseProducer(String propertyName, String propertyValue) {

    }

    /**
     * Parses application properties, generating application metadata for Camel components.
     */
    public void parseProperties() {
        for (String propertyName : idaasProperties.stringPropertyNames()) {
            String propertyValue = idaasProperties.getProperty(propertyName);

            int categoryStartIndex = idaasPropertyNamespace.length() + 1;
            int categoryEndIndex = categoryStartIndex + propertyName.substring(categoryStartIndex).indexOf('.');
            String propertyCategory = propertyName.substring(categoryStartIndex, categoryEndIndex);

            switch (propertyCategory) {
                case IDAAS_COMPONENT_NAMESPACE:
                    parseComponent(propertyName, propertyValue);
                    break;
                case IDAAS_CONSUMER_NAMESPACE:
                    parseConsumer(propertyName, propertyValue);
                    break;
                case IDAAS_PRODUCER_NAMESPACE:
                    parseProducer(propertyName, propertyValue);
                    break;
            }
        }
    }


    /**
     * Returns a property value from the underlying properties object
     *
     * @param propertyKey The property key to lookup
     * @return The property value if found, otherwise returns null
     */
    public String getPropertyValue(String propertyKey) {
        return idaasProperties.getProperty(propertyKey);
    }

    /**
     * Filters properties by matching on a string prefix.
     *
     * @param properties   The source Properties
     * @param filterPrefix The prefix used to match the properties
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
     * Creates a new PropertyParser instance.
     * Incoming properties are filtered to include properties which are used to generate Camel
     * components, routes, and processors.
     * @param properties The source application properties
     */
    public PropertyParser(Properties properties) {
        idaasPropertyNamespace = properties.getProperty(IDAAS_NAMESPACE_PROPERTY, DEFAULT_IDAAS_PROPERTY_NAMESPACE);
        idaasProperties = filterProperties(properties, idaasPropertyNamespace);
        parseProperties();
    }
}