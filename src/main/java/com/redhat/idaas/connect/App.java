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
package com.redhat.idaas.connect;

import com.redhat.idaas.connect.configuration.IdaasRouteBuilder;

import org.apache.camel.component.hl7.HL7MLLPNettyDecoderFactory;
import org.apache.camel.component.hl7.HL7MLLPNettyEncoderFactory;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * iDAAS Connect Application
 */
public class App {

    private Logger logger = LoggerFactory.getLogger(App.class);


    private void start() throws Exception {
        logger.info("booting Camel for iDAAS-Connect");
        Main camelMain = new Main();
        camelMain.enableHangupSupport();

        logger.info("configuring Camel for iDAAS-Connect");
        camelMain.bind("hl7encoder", new HL7MLLPNettyEncoderFactory());
        camelMain.bind("hl7decoder", new HL7MLLPNettyDecoderFactory());
        camelMain.configure().addRoutesBuilder(new IdaasRouteBuilder());

        camelMain.run();
        logger.info("iDAAS-Connect is started");
    }

    /**
     * Entrypoint for iDAAS Connection Application.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }
}
