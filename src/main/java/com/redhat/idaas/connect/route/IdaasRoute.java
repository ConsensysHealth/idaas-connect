package com.redhat.idaas.connect.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7MLLPNettyDecoderFactory;

// netty:tcp://{{idaas.connect.mllp.host}}:{{idaas.connect.mllp.port}}?sync=true&encoders=#hl7encoder&decoders=#hl7decoder

public class IdaasRoute {

    public void x() {
        HL7MLLPNettyDecoderFactory f = new HL7MLLPNettyDecoderFactory();
    }

    /**
     * Builds the route consumer URI, including the schema, context, and additional parameters.
     * @return the consumer URI string
     */
    // protected String buildRouteConsumerUri() {
    //     StringBuilder routeConsumer = new StringBuilder();
    //     routeConsumer.append(getRouteScheme());
    //     routeConsumer.append("://");
    //     routeConsumer.append(getRouteContextPath());

    //     if (getRouteOptions() != null && getRouteOptions().size() > 0) {
    //         routeConsumer.append("?");
    //         for (Entry<String, String> mapEntry: getRouteOptions().entrySet()) {
    //             routeConsumer.append(mapEntry.getKey());
    //             routeConsumer.append("=");
    //             routeConsumer.append(mapEntry.getValue());
    //             routeConsumer.append("&");
    //         }
    //         routeConsumer.delete(routeConsumer.length() - 1, routeConsumer.length());
    //     }
    //     return routeConsumer.toString();
    // }

    /**
     * Returns the processing route's definition.
     * Route definitions are defined using a {@link RouteBuilder} instance.
     * @return {@link RouteBuilder} instance
     */
    // public RouteBuilder getRouteDefinition() {
    //     return new RouteBuilder() {
    //         public void configure() {
    //             from(buildRouteConsumerUri())
    //             .routeId(getRouteId())
    //             .log("${body}")
    //             .to("stub:hl7-stub");
    //         }
    //     };
    // }

    // public IdaasRoute() {

    // }
}