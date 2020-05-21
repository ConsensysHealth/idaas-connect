package com.redhat.idaas.connect.configuration;

import org.apache.camel.builder.RouteBuilder;

public class IdaasRouteBuilder extends RouteBuilder{

    @Override
    public void configure() {
        from("netty:tcp://localhost:8888?sync=true&encoders=#hl7encoder&decoders=#hl7decoder")
        .routeId("hl7")
        .log("${body}")
        .to("stub:hl7-stub");
    }
}
