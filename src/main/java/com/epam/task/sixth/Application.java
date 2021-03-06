package com.epam.task.sixth;

import com.epam.task.sixth.data.DataException;
import com.epam.task.sixth.data.JsonReader;
import com.epam.task.sixth.entity.Van;
import com.epam.task.sixth.entity.Vans;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Application {

    private static final Logger LOGGER = LogManager.getLogger(Application.class);
    private static final String INPUT = "./src/main/resources/vans.json";

    public static void main(String[] args) throws ExecutionException, InterruptedException, DataException {
        JsonReader jsonReader = new JsonReader();

        List<Van> vans;

        try {
            vans = jsonReader.read(INPUT);
        } catch (DataException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(vans.size());

        List<Future<?>> futures = vans.stream()
                .map(executorService::submit)
                .collect(Collectors.toList());

        executorService.shutdown();

        for (Future<?> future : futures) {
            future.get();
        }

        System.out.println("Vans after processing:");
        vans.forEach(System.out::println);
    }
}
