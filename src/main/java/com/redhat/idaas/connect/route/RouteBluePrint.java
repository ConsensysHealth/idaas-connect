package com.redhat.idaas.connect.route;

import com.redhat.idaas.connect.configuration.CamelRoute;
import org.apache.camel.builder.RouteBuilder;

/**
 * Builds a {@link RouteBuilder} Camel Java DSL route from a {@link CamelRoute} domain object
 */
public interface RouteBluePrint {

    /**
     * Constructs a {@link RouteBuilder} instance from a {@link CamelRoute}
     * @param camelRoute The camel route domain object
     * @return {@link RouteBuilder} instance
     */
    RouteBuilder buildRoute(CamelRoute camelRoute);
}
