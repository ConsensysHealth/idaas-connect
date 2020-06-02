package com.redhat.idaas.connect.route;

import com.redhat.idaas.connect.configuration.CamelRoute;
import org.apache.camel.builder.RouteBuilder;

/**
 * Creates a route used to parse HL7-MLLP Messages.
 */
public class Hl7BluePrint implements RouteBluePrint {
    @Override
    public RouteBuilder buildRoute(CamelRoute camelRoute) {
        return null;
    }
}
