package com.epam.task.sixth.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Van implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Van.class);

    private int id;
    private boolean loaded;
    private boolean priority;

    public Van() {
    }

    public Van(int id, boolean loaded, boolean priority) {
        this.id = id;
        this.loaded = loaded;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isPriority() {
        return priority;
    }

    @Override
    public void run() {
        Base base = Base.getInstance();

        try {
            base.addVan(this);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "Van{" +
                "id=" + id +
                ", loaded=" + loaded +
                ", priority=" + priority +
                '}';
    }
}
