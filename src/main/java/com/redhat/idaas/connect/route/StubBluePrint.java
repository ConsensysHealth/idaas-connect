package com.redhat.idaas.connect.route;

import com.redhat.idaas.connect.configuration.CamelRoute;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * Creates a route with a single Stub producer.
 */
public class StubBluePrint implements RouteBluePrint {

    @Override
    public RouteBuilder buildRoute(CamelRoute camelRoute) {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(camelRoute.getConsumer().toString())
                    .log(LoggingLevel.DEBUG, "received message")
                    .log(LoggingLevel.DEBUG, "${body}")
                        .to("stub://hl7-mllp");

            }
        };
    }
}
