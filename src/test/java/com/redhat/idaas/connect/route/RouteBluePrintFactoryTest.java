package com.redhat.idaas.connect.route;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Tests {@link RouteBluePrintFactory}
 */
class RouteBluePrintFactoryTest {

    /**
     * Provides arguments to {@link RouteBluePrintFactory#getInstance(String)}
     *
     * @return {@link Stream} of arguments
     */
    static Stream<Arguments> getInstanceProvider() {
        return Stream.of(
                Arguments.arguments("stub", StubBluePrint.class)
        );
    }

    /**
     * Tests {@link RouteBluePrintFactory#getInstance(String)} with a blue print id
     * @param bluePrintId The blue print id for the lookup
     * @param expectedClass The expected {@link RouteBluePrint} implementation class
     */
    @ParameterizedTest
    @MethodSource("getInstanceProvider")
    void testGetInstance(String bluePrintId, Class<? extends RouteBluePrint> expectedClass) {
        RouteBluePrint actualClass = RouteBluePrintFactory.getInstance(bluePrintId);
        Assertions.assertNotNull(actualClass);
        Assertions.assertTrue(expectedClass.isInstance(actualClass));
    }

    /**
     * Tests {@link RouteBluePrintFactory#getInstance(String)} for expected null return values
     */
    @Test
    void testGetInstanceNullReturn() {
        Assertions.assertNull(RouteBluePrintFactory.getInstance(null));
        Assertions.assertNull(RouteBluePrintFactory.getInstance("foo"));
    }


}

