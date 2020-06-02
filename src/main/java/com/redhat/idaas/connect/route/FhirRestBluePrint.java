package com.redhat.idaas.connect.route;

import com.redhat.idaas.connect.configuration.CamelRoute;
import org.apache.camel.builder.RouteBuilder;

/**
 * Creates a route used to parse FHIR Rest Messages.
 */
public class FhirRestBluePrint implements RouteBluePrint {
    @Override
    public RouteBuilder buildRoute(CamelRoute camelRoute) {
        return null;
    }
}
