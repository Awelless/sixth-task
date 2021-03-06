package com.epam.task.sixth.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Terminal {

    private static final Logger LOGGER = LogManager.getLogger(Terminal.class);

    public void process(Van van) {

        LOGGER.info("Processing van with id: " + van.getId());

        boolean isLoaded = van.isLoaded();
        van.setLoaded(!isLoaded);
    }

}
