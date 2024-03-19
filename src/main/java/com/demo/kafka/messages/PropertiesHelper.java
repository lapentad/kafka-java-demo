/*-
 * ========================LICENSE_START=================================
 * O-RAN-SC
 * 
 * Copyright (C) 2024 Nordix Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================LICENSE_END===================================
 */

package com.demo.kafka.messages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.demo.kafka.producer.SimpleProducer;

/**
 * The type PropertiesHelper is a class that represents the value stored
 * in the properties file named config.properties.
 */
public class PropertiesHelper {
    /**
     * Gets a Properties object that contains the keys and values defined
     * in the file src/main/resources/config.properties
     *
     * @return a {@link java.util.Properties} object
     * @throws Exception Thrown if the file config.properties is not available
     *         in the directory src/main/resources
     */
    public static Properties getProperties() throws Exception {

        Properties props = null;
        // try to load the file config.properties
        try (InputStream input = SimpleProducer.class.getClassLoader().getResourceAsStream("config.properties")) {

            props = new Properties();

            if (input == null) {
                throw new Exception("Sorry, unable to find config.properties");
            }

            // load a properties file from class path, inside static method
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return props;
    }

}
