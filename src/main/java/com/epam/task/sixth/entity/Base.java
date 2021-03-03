package com.epam.task.sixth.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Base {

    private static final int TOTAL_TERMINALS = 2;
    private static final Logger LOGGER = LogManager.getLogger(Base.class);

    private static final Lock LOCKING = new ReentrantLock();
    private static final AtomicReference<Base> INSTANCE = new AtomicReference<>();

    private static final Semaphore SEMAPHORE = new Semaphore(TOTAL_TERMINALS);

    private Base() {
    }

    public static Base getInstance() {
        if (INSTANCE.get() == null) {
            createInstance();
        }

        return INSTANCE.get();
    }

    private static void createInstance() {
        try {
            LOCKING.lock();
            if (INSTANCE.get() == null) {
                INSTANCE.getAndSet(new Base());
            }

        } finally {
            LOCKING.unlock();
        }
    }

    public void process(Van van) {
        try {
            SEMAPHORE.acquire();
            boolean loaded = van.isLoaded();
            van.setLoaded(!loaded);

        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            SEMAPHORE.release();
        }
    }
}
