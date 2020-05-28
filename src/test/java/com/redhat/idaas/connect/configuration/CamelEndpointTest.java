package com.redhat.idaas.connect.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Tests {@link CamelEndpoint}
 */
public class CamelEndpointTest {

    private CamelEndpoint camelEndpoint;

    /**
     * Constructs a {@link CamelEndpoint} fixture prior to each test
     */
    @BeforeEach
    public void beforeEach() {
        camelEndpoint = new CamelEndpoint();
        camelEndpoint.setScheme("ftp");
        camelEndpoint.setUri("myftp.com:21/dropbox");
    }

    /**
     * Tests {@link CamelEndpoint#equals} where an instance is compared to itself
     */
    @Test
    public void testEqualsSameInstance() {
        CamelEndpoint otherEndpoint = camelEndpoint;
        Assertions.assertEquals(camelEndpoint, otherEndpoint);
    }

    /**
     * Tests {@link CamelEndpoint#equals} where an instance is compared to null
     */
    @Test
    public void testEqualsNull() {
        Assertions.assertNotEquals(camelEndpoint, null);
    }

    /**
     * Tests {@link CamelEndpoint#equals} where an instance is compared to a non-compatible type
     */
    @Test
    public void testEqualsDifferentClass() {
        Assertions.assertNotEquals(camelEndpoint, "a string");
    }

    /**
     * Tests {@link CamelEndpoint#equals} where an instance is compared to an equivalent object
     */
    @Test
    public void testEqualsEquivalent() {
        CamelEndpoint otherEndpoint = new CamelEndpoint();
        otherEndpoint.setScheme(camelEndpoint.getScheme());
        otherEndpoint.setUri(camelEndpoint.getUri());
        Assertions.assertEquals(camelEndpoint, otherEndpoint);
    }

    /**
     * Tests {@link CamelEndpoint#equals} where an instance is compared to a non-equivalent object
     */
    @Test
    public void testEqualsNotEquivalent() {
        CamelEndpoint otherEndpoint = new CamelEndpoint();
        otherEndpoint.setScheme(camelEndpoint.getScheme());
        otherEndpoint.setUri("myftp.com:21/dropoff");
        Assertions.assertNotEquals(camelEndpoint, otherEndpoint);
    }

    /**
     * Validates that {@link CamelEndpoint#hashCode()} generates the same hash code for equivalent objects
     */
    @Test
    public void testHashCodeForEqualObjects() {
        CamelEndpoint otherEndpoint = new CamelEndpoint();
        otherEndpoint.setScheme(camelEndpoint.getScheme());
        otherEndpoint.setUri(camelEndpoint.getUri());

        Assertions.assertEquals(camelEndpoint.hashCode(), otherEndpoint.hashCode());
    }

    /**
     * Validates that {@link CamelEndpoint#hashCode()} generates the same hash code for non-equivalent objects
     */
    @Test
    public void testHashCodeForNonEqualObjects() {
        CamelEndpoint otherEndpoint = new CamelEndpoint();
        otherEndpoint.setScheme(camelEndpoint.getScheme());
        otherEndpoint.setUri("myftp.com:21/dropoff");

        Assertions.assertNotEquals(camelEndpoint.hashCode(), otherEndpoint.hashCode());
    }

    /**
     * Tests {@link CamelEndpoint#toString()}
     */
    @Test
    public void testToString() {
        String expectedString = "ftp://myftp.com:21/dropbox";
        Assertions.assertEquals(expectedString, camelEndpoint.toString());
    }

    /**
     * Tests {@link CamelEndpoint#toString()} when endpoint options are present
     */
    @Test
    public void testToStringWithOptions() {
        camelEndpoint.addOption("binary", "true");
        camelEndpoint.addOption("disconnect", "true");
        camelEndpoint.addOption("transferLoggingLevel", "ERROR");

        // order of parameters is not deterministic
        String actual = camelEndpoint.toString();
        String[] options = actual.split("\\?");
        Assertions.assertEquals(2, options.length);

        options = actual.split("\\?")[1].split("&");
        Assertions.assertEquals(3, options.length);

        Arrays.sort(options);
        String[] expectedOptions = {"binary=true", "disconnect=true", "transferLoggingLevel=ERROR"};
        Assertions.assertArrayEquals(expectedOptions, options);
    }
}
