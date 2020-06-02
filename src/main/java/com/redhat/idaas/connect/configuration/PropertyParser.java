package com.redhat.idaas.connect.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;


/**
 * Parses iDAAS Connect Application Properties used to generate Camel components, routes, and processors.
 */
public final class PropertyParser {

    // constants used to parse property keys
    private static final String IDAAS_PROPERTY_NAMESPACE = "idaas.connect";
    private static final String IDAAS_ROUTE_NAMESPACE = "route";
    private static final String IDAAS_COMPONENT_NAMESPACE = "component";
    private static final String IDAAS_CONSUMER_NAMESPACE = "consumer";

    /**
     * properties object containing idaas connect properties
     */
    private final Properties idaasProperties;

    /**
     * Maps a component name to it's fully qualified class name
     */
    private final Map<String, String> idaasComponents = new HashMap<>();

    /**
     * Maps a route id to a camel route
     */
    private final Map<String, CamelRoute> idaasRoutes = new HashMap<>();

    /**
     * Returns a map of iDAAS Components
     * Key - component name
     * Value - component class name
     *
     * @return iDAAS Components as {@link Map}
     */
    public Map<String, String> getIdaasComponents() {
        return idaasComponents;
    }

    /**
     * Returns a map of iDAAS Routes
     * Key - route id
     * Value - {@link CamelRoute}
     *
     * @return iDAAS Route as {@link Map}
     */
    public Map<String, CamelRoute> getIdaasRoutes() {
        return idaasRoutes;
    }

    /**
     * Parses the component identifier and value (component class name) from a property name and value.
     *
     * @param propertyKey   The property key
     * @param propertyValue The property value (component class name)
     */
    private void parseComponent(String propertyKey, String propertyValue) {
        int componentNameIndex = propertyKey.lastIndexOf('.') + 1;
        String componentKey = propertyKey.substring(componentNameIndex);
        idaasComponents.put(componentKey, propertyValue);
    }

    /**
     * Sets a {@link CamelEndpoint} field
     *
     * @param camelEndpoint The target CamelEndpoint
     * @param endpointField The field to set
     * @param fieldValue    The field value
     */
    private void setEndpointField(CamelEndpoint camelEndpoint, String endpointField, String fieldValue) {

        if (endpointField.equalsIgnoreCase("scheme")) {
            camelEndpoint.setScheme(fieldValue);
        } else if (endpointField.equalsIgnoreCase("context")) {
            camelEndpoint.setContextPath(fieldValue);
        } else if (endpointField.equalsIgnoreCase("options")) {
            camelEndpoint.setOptions(fieldValue);
        }
    }

    /**
     * Parses route configuration fields
     *
     * @param routeId       The route id
     * @param propertyField The route configuration property field
     * @param propertyValue The route configuration property value
     */
    private void parseRoute(String routeId, String propertyField, String propertyValue) {
        if (propertyField.endsWith("blueprint")) {
            idaasRoutes.get(routeId).setRouteBluePrint(propertyValue);
        }
    }

    /**
     * Parses a consumer endpoint property
     *
     * @param routeId       The route id
     * @param propertyField The property name/key
     * @param propertyValue The property value
     */
    private void parseConsumer(String routeId, String propertyField, String propertyValue) {
        CamelEndpoint consumer = idaasRoutes.get(routeId).getConsumer();
        setEndpointField(consumer, propertyField, propertyValue);
    }

    /**
     * Parses application properties into domain models, generating application metadata for Camel components.
     */
    private void parseProperties() {
        for (String propertyKey : idaasProperties.stringPropertyNames()) {
            String propertyValue = idaasProperties.getProperty(propertyKey);

            int namespaceStartIndex = IDAAS_PROPERTY_NAMESPACE.length() + 1;
            int namespaceEndIndex = namespaceStartIndex + propertyKey.substring(namespaceStartIndex).indexOf('.');
            String propertyNamespace = propertyKey.substring(namespaceStartIndex, namespaceEndIndex);

            if (propertyNamespace.equalsIgnoreCase(IDAAS_COMPONENT_NAMESPACE)) {
                parseComponent(propertyKey, propertyValue);
                continue;
            }

            String[] tokens = propertyKey.substring(namespaceEndIndex + 1).split("\\.");
            String routeId = tokens[0];
            String propertyField = tokens[1];

            if (!idaasRoutes.containsKey(routeId)) {
                CamelRoute camelRoute = new CamelRoute();
                camelRoute.setRouteId(routeId);
                idaasRoutes.put(routeId, camelRoute);
            }

            switch (propertyNamespace) {
                case IDAAS_ROUTE_NAMESPACE:
                    parseRoute(routeId, propertyField, propertyValue);
                case IDAAS_CONSUMER_NAMESPACE:
                    parseConsumer(routeId, propertyField, propertyValue);
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
     * @param properties The source Properties
     * @return a Properties instance containing properties which match the string prefix
     */
    private Properties filterProperties(Properties properties) {
        Map<Object, Object> filteredProperties = properties
                .entrySet()
                .stream()
                .filter(p -> p.getKey().toString().startsWith(IDAAS_PROPERTY_NAMESPACE))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Properties idaasProperties = new Properties();
        idaasProperties.putAll(filteredProperties);
        return idaasProperties;
    }

    /**
     * Creates a new PropertyParser instance.
     * Incoming properties are filtered to include properties which are used to generate Camel
     * components, routes, and processors.
     *
     * @param properties The source application properties
     */
    public PropertyParser(Properties properties) {
        idaasProperties = filterProperties(properties);
        parseProperties();
    }
}