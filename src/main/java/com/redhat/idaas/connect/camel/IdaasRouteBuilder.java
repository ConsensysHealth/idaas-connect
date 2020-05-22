/*
 * Copyright 2020 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */

package com.redhat.idaas.connect.camel;

import org.apache.camel.builder.RouteBuilder;

/**
 * Defines iDAAS Connect's supported data routes.
 * Supported routes include:
 * <ul>
 *  <li>HL7-MLLP</li>
 *  <li>FHIR-REST</li>
 * </ul>
 */
class IdaasRouteBuilder extends RouteBuilder{

    /**
     * Builds iDAAS Connect's data routes.
     */
    @Override
    public void configure() {
        from("netty:tcp://{{idaas.connect.mllp.host}}:{{idaas.connect.mllp.port}}?sync=true&encoders=#hl7encoder&decoders=#hl7decoder")
        .routeId("hl7")
        .log("${body}")
        .to("stub:hl7-stub");
    }
}
