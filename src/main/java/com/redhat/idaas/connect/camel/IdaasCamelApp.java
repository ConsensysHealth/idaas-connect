
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

import java.util.Arrays;
import java.util.Properties;

import org.apache.camel.Component;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7MLLPNettyDecoderFactory;
import org.apache.camel.component.hl7.HL7MLLPNettyEncoderFactory;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A standalone iDAAS Connect Camel Application
 */
public class IdaasCamelApp {

    private Logger logger = LoggerFactory.getLogger(IdaasCamelApp.class);

    private Main camelMain = new Main();

    /**
     * Configures the iDAAS Connect Application
     */
    private void configureApplication() {
        camelMain.enableHangupSupport();
    }

    /**
     * Adds components to the camel context's registry lookup.
     */
    private void addComponents() {
        camelMain.bind("hl7encoder", new HL7MLLPNettyEncoderFactory());
        camelMain.bind("hl7decoder", new HL7MLLPNettyDecoderFactory());
    }

    /**
     * Adds routes to the camel context
     * 
     * @param routes one or more {@link RouteBuilder} instances
     */
    private void addRoutes(RouteBuilder... routes) {
        Arrays.asList(routes).stream().forEach(camelMain.configure()::addRoutesBuilder);
    }

    /**
     * Creates an instance of the iDAAS Camel App, configuring it for use.
     */
    public IdaasCamelApp() {
        logger.info("configuring iDAAS Connect Camel Application");
        configureApplication();
        addComponents();
        addRoutes(new IdaasRouteBuilder());
    }

    /**
     * Starts the iDAAS camel application
     */
    public void start() throws Exception {
        logger.info("starting iDAAS Connect Camel Application");
        camelMain.run();
    }
}