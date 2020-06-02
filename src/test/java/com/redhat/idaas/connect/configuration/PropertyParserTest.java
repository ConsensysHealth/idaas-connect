package com.redhat.idaas.connect.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Tests {@link PropertyParser}
 */
class PropertyParserTest {

    private static Properties sourceIdaasProperties;

    private PropertyParser propertyParser;

    /**
     * Loads test properties from file
     *
     * @throws IOException for errors reading the property file
     */
    @BeforeAll
    static void beforeAll() throws IOException {
        sourceIdaasProperties = new Properties();

        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("application.properties")) {
            sourceIdaasProperties.load(inputStream);
        }
    }

    /**
     * Sets up the {@link PropertyParser} prior to each test
     */
    @BeforeEach
    void beforeEach() {
        propertyParser = new PropertyParser(sourceIdaasProperties);
    }

    /**
     * Tests {@link PropertyParser#PropertyParser(Properties)} to ensure properties are filtered correctly
     */
    @Test
    void testPropertyFiltering() {
        Assertions.assertNotNull(sourceIdaasProperties.getProperty("some.other.property"));
        Assertions.assertNull(propertyParser.getPropertyValue("some.other.property"));
    }

    /**
     * Tests {@link PropertyParser#PropertyParser(Properties)} and validates component parsing
     */
    @Test
    void testPropertyParserComponents() {
        Map<String, String> actualComponents = propertyParser.getIdaasComponents();
        Assertions.assertTrue(actualComponents.containsKey("hl7decoder"));
        Assertions.assertTrue(actualComponents.containsKey("hl7encoder"));

        String expectedValue = "org.apache.camel.component.hl7.HL7MLLPNettyDecoderFactory";
        Assertions.assertEquals(expectedValue, actualComponents.get("hl7decoder"));

        expectedValue = "org.apache.camel.component.hl7.HL7MLLPNettyEncoderFactory";
        Assertions.assertEquals(expectedValue, actualComponents.get("hl7encoder"));
    }

    /**
     * Tests {@link PropertyParser#PropertyParser(Properties)} and validates idaas route domain models
     */
    @Test
    void testPropertyParserIdaasRoutes() {
        CamelRoute expectedCamelRoute = new CamelRoute();
        expectedCamelRoute.setRouteBluePrint("stub");
        expectedCamelRoute.setRouteId("hl7-mllp");

        CamelEndpoint expectedConsumer = new CamelEndpoint();
        expectedConsumer.setScheme("netty:tcp://");
        expectedConsumer.setContextPath("localhost:2575");
        expectedConsumer.setOptions("sync=true&encoders=#hl7encoder&decoders=#hl7decoder");
        expectedCamelRoute.setConsumer(expectedConsumer);

        Map<String, CamelRoute> actualIdaasRoutes =  propertyParser.getIdaasRoutes();
        Assertions.assertTrue(actualIdaasRoutes.containsKey("hl7-mllp"));

        CamelRoute actualRoute = actualIdaasRoutes.get("hl7-mllp");
        Assertions.assertEquals("stub", actualRoute.getRouteBluePrint());

        String expectedUri = "netty:tcp://localhost:2575?sync=true&encoders=#hl7encoder&decoders=#hl7decoder";
        CamelEndpoint actualConsumer = actualRoute.getConsumer();
        Assertions.assertEquals(expectedUri, actualConsumer.toString());
    }
}