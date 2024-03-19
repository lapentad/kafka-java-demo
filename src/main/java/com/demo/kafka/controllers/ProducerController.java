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

package com.demo.kafka.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.kafka.r1.ProducerJobInfo;
import com.demo.kafka.repository.InfoType;
import com.demo.kafka.repository.InfoTypes;
import com.demo.kafka.repository.Job.Parameters;
import com.demo.kafka.repository.Jobs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ProducerController {
    private static final Logger log = LoggerFactory.getLogger(ProducerController.class);

    private static Gson gson = new GsonBuilder().create();

    private final Jobs jobs;
    private final InfoTypes types;

    public ProducerController(@Autowired Jobs jobs, @Autowired InfoTypes types) {
        this.jobs = jobs;
        this.types = types;
        InfoType type1 = InfoType.builder().build();
        type1.setId("type1");
        type1.setKafkaInputTopic("mytopic");
        type1.setInputJobType("type1");
        type1.setInputJobDefinition(null);
        types.put(type1);
    }

    @PostMapping("/producer/job/{infoJobId}")
    public void jobCallback(@RequestBody String requestBody, @PathVariable String infoJobId) {
        ProducerJobInfo request = gson.fromJson(requestBody, ProducerJobInfo.class);
        try {
            log.info("adding producer job info "+request.toString());
            this.jobs.addJob(request.id, types.getType(request.typeId), request.owner,
                    toJobParameters(request.jobData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/producer/job")
    public void jobCallbackNoId(@RequestBody String requestBody) {
        ProducerJobInfo request = gson.fromJson(requestBody, ProducerJobInfo.class);
        try {
            log.info("adding producer job info "+request.toString());
            this.jobs.addJob(request.id, types.getType(request.typeId), request.owner,
                    toJobParameters(request.jobData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Parameters toJobParameters(Object jobData) {
        String json = gson.toJson(jobData);
        return gson.fromJson(json, Parameters.class);
    }

    @GetMapping("/producer/job")
    public ResponseEntity<String> getJobs() {
        try {
            log.info("Get all jobs");
            return new ResponseEntity<>(this.jobs.getAll().toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/producer/job/{infoJobId}")
    public ResponseEntity<String> deleteJob(@PathVariable String infoJobId) {
        try {
            log.info("Delete Job" + infoJobId);
            this.jobs.delete(infoJobId);
            return new ResponseEntity<>("Deleted job:" + infoJobId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/producer/supervision")
    public ResponseEntity<String> getSupervision() {
        log.info("Get Supervision");
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
