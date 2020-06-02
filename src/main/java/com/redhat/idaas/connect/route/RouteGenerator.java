package com.redhat.idaas.connect.route;

import com.redhat.idaas.connect.configuration.CamelRoute;
import org.apache.camel.builder.RouteBuilder;

/**
 * Generates {@link RouteBuilder} instances from a {@link CamelRoute} model
 */
public class RouteGenerator {

    private final CamelRoute camelRoute;

    /**
     * Generates the {@link RouteBuilder} instance
     * @return {@link RouteBuilder} instance
     */
    public RouteBuilder generate() {
        String routeBluePrint = camelRoute.getRouteBluePrint();
        RouteBluePrint bluePrint = RouteBluePrintFactory.getInstance(routeBluePrint);
        return bluePrint.buildRoute(camelRoute);
    }

    /**
     * Associates the RouteGenerator with a {@link CamelRoute} instance
     * @param camelRoute The {@link CamelRoute} instance
     */
    public RouteGenerator(CamelRoute camelRoute) {
        this.camelRoute = camelRoute;
    }
}
