package com.redhat.idaas.connect.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CamelRoute}
 */
class CamelRouteTest {

    private CamelRoute camelRoute;

    /**
     * Configures the {@link CamelRoute} fixture
     */
    @BeforeEach
    void beforeEach() {
        camelRoute = new CamelRoute();
        camelRoute.setRouteId("myRoute");
        camelRoute.setRouteBluePrint("StubBluePrint");

        CamelEndpoint consumer = new CamelEndpoint();
        consumer.setScheme("ftp://");
        consumer.setContextPath("myftp.com:22/dropbox");
        camelRoute.setConsumer(consumer);
    }

    /**
     * Tests {@link CamelRoute#equals} where an instance is compared to null
     */
    @Test
    void testEqualsNull() {
        Assertions.assertNotEquals(camelRoute, null);
    }

    /**
     * Tests {@link CamelRoute#equals} where an instance is compared to a non-compatible type
     */
    @Test
    void testEqualsDifferentClass() {
        Assertions.assertNotEquals(camelRoute, "a string");
    }

    /**
     * Tests {@link CamelRoute#equals} where an instance is compared to an equivalent object
     */
    @Test
    void testEqualsEquivalent() {
        CamelRoute otherRoute = new CamelRoute();
        otherRoute.setRouteId(camelRoute.getRouteId());
        Assertions.assertEquals(camelRoute, otherRoute);
    }

    /**
     * Tests {@link CamelRoute#equals} where an instance is compared to a non-equivalent object
     */
    @Test
    void testEqualsNotEquivalent() {
        CamelRoute otherRoute = new CamelRoute();
        otherRoute.setRouteId("otherRoute");
        Assertions.assertNotEquals(camelRoute, otherRoute);
    }

    /**
     * Validates that {@link CamelRoute#hashCode()} generates the same hash code for equivalent objects
     */
    @Test
    void testHashCodeForEqualObjects() {
        CamelRoute otherRoute = new CamelRoute();
        otherRoute.setRouteId(camelRoute.getRouteId());
        Assertions.assertEquals(camelRoute.hashCode(), otherRoute.hashCode());
    }

    /**
     * Validates that {@link CamelRoute#hashCode()} generates the same hash code for non-equivalent objects
     */
    @Test
    void testHashCodeForNonEqualObjects() {
        CamelRoute otherRoute = new CamelRoute();
        otherRoute.setRouteId("otherRoute");
        Assertions.assertNotEquals(camelRoute.hashCode(), otherRoute.hashCode());
    }
}
