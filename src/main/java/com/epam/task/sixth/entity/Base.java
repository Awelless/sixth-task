package com.epam.task.sixth.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

public final class Base {

    private static final Logger LOGGER = LogManager.getLogger(Base.class);
    private static final int TOTAL_TERMINALS = 2;

    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final AtomicReference<Base> INSTANCE = new AtomicReference<>();

    private final Semaphore semaphore = new Semaphore(TOTAL_TERMINALS);

    private final Lock terminalLock = new ReentrantLock();
    private final Queue<Terminal> terminals;

    private final Lock vanLock = new ReentrantLock();
    private final Queue<Van> vans;

    private Base() {
        this.terminals = IntStream.range(0, TOTAL_TERMINALS)
                .mapToObj(value -> new Terminal())
                .collect(Collectors.toCollection(LinkedList::new));

        this.vans = new PriorityQueue<>((first, second) ->
                -Boolean.compare(first.isPriority(), second.isPriority()));
    }

    public static Base getInstance() {
        if (INSTANCE.get() == null) {
            try {
                INSTANCE_LOCK.lock();
                if (INSTANCE.get() == null) {
                    Base base = new Base();
                    INSTANCE.getAndSet(base);
                }

            } finally {
                INSTANCE_LOCK.unlock();
            }
        }

        return INSTANCE.get();
    }

    private void processNextVan() throws Exception {
        try {
            semaphore.acquire();

            vanLock.lock();
            Van van = vans.poll();

            terminalLock.lock();
            Terminal terminal = terminals.poll();

            if (van == null || terminal == null) {
                //if everything is correct, it will never be executed
                LOGGER.error("Van or terminal is null");
                throw new Exception("Van or terminal is null");
            }

            terminal.process(van);

            terminals.add(terminal);

        } finally {
            semaphore.release();
            vanLock.unlock();
            terminalLock.unlock();
        }
    }

    public void addVan(Van van) throws Exception {
        try {
            vanLock.lock();
            vans.add(van);
        } finally {
            vanLock.unlock();
        }

        processNextVan();
    }
}
