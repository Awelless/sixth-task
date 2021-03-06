package com.epam.task.sixth.data;

import com.epam.task.sixth.entity.Van;
import com.epam.task.sixth.entity.Vans;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {

    public List<Van> read(String filename) throws DataException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            File file = new File(filename);

            Vans vansWrapper = objectMapper.readValue(file, Vans.class);

            return vansWrapper.getVans();

        } catch (IOException e) {
            throw new DataException(e);
        }
    }

}
