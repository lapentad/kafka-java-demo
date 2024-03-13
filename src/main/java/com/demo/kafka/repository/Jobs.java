package com.demo.kafka.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.kafka.repository.Job.Parameters;

@Component
public class Jobs {
    private static final Logger logger = LoggerFactory.getLogger(Jobs.class);

    private Map<String, Job> allJobs = new HashMap<>();


    public Jobs() {
    }

    public synchronized Job getJob(String id) throws Exception {
        Job job = allJobs.get(id);
        if (job == null) {
            throw new Exception("Could not find job: " + id);
        }
        return job;
    }

    public synchronized Job get(String id) {
        return allJobs.get(id);
    }

    public void addJob(String id, InfoType type, String owner, String lastUpdated, Parameters parameters)
    {
        Job job = new Job(id, type, owner, lastUpdated, parameters);
        this.put(job);
    }

    private synchronized void put(Job job) {
        logger.debug("Put job: {}", job.getId());
        allJobs.put(job.getId(), job);
    }

    public synchronized Iterable<Job> getAll() {
        return new Vector<>(allJobs.values());
    }

    public synchronized int size() {
        return allJobs.size();
    }
}
