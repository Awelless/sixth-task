package com.epam.task.sixth;

import com.epam.task.sixth.entity.Van;
import com.epam.task.sixth.entity.Vans;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        List<Van> vans = readData();

        ExecutorService executorService = Executors.newFixedThreadPool(vans.size());

        List<Future<?>> futures = vans.stream()
                .map(executorService::submit)
                .collect(Collectors.toList());

        for (Future<?> future : futures) {
            future.get();
        }

        executorService.shutdown();

        vans.forEach(System.out::println);
    }

    private static List<Van> readData() throws IOException {
        try {
            Path inputPath = Paths.get(INPUT);
            String inputData = String.join("\n", Files.readAllLines(inputPath));

            ObjectMapper objectMapper = new ObjectMapper();
            Vans vansWrapper = objectMapper.readValue(inputData, Vans.class);
            return vansWrapper.getVans();

        } catch (IOException e) {
            LOGGER.fatal(e.getMessage(), e);
            throw e;
        }
    }
}
