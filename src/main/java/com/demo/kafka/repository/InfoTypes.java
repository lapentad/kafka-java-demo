package com.demo.kafka.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class InfoTypes {
    private static final Logger logger = LoggerFactory.getLogger(InfoTypes.class);

    private Map<String, InfoType> allTypes = new HashMap<>();

    public InfoTypes(Collection<InfoType> types) {
        for (InfoType type : types) {
            put(type);
        }
    }

    public synchronized InfoType get(String id) {
        return allTypes.get(id);
    }

    public synchronized InfoType getType(String id) throws Exception {
        InfoType type = allTypes.get(id);
        if (type == null) {
            throw new Exception("Could not find type: " + id + HttpStatus.NOT_FOUND.toString());
        }
        return type;
    }

    public static class ConfigFile {
        Collection<InfoType> types;
    }

    private synchronized void put(InfoType type) {
        logger.debug("Put type: {}", type.getId());
        allTypes.put(type.getId(), type);
    }

    public synchronized Iterable<InfoType> getAll() {
        return new Vector<>(allTypes.values());
    }

    public synchronized Collection<String> typeIds() {
        return allTypes.keySet();
    }

    public synchronized int size() {
        return allTypes.size();
    }

    public synchronized void clear() {
        allTypes.clear();
    }
}
