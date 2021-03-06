package com.epam.task.sixth.data;

import com.epam.task.sixth.entity.Van;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JsonReaderTest {
    private final JsonReader reader = new JsonReader();

    private static final String VALID_INPUT = "./src/test/resources/input.json";
    private static final String INVALID_INPUT = "./src/test/resources/input123.json";

    @Test
    public void testReadShouldReadWhenValidApplied() throws DataException {
        List<Van> expected = Arrays.asList(
            new Van(1, true, true),
            new Van(2, true, false),
            new Van(3, false, false),
            new Van(4, false, true),
            new Van(5, true, false),
            new Van(6, true, false),
            new Van(7, true, false),
            new Van(8, false, false),
            new Van(9, false, true),
            new Van(10, true, true)
        );

        List<Van> actual = reader.read(VALID_INPUT);

        Assert.assertEquals(10, actual.size());

        //check equality when equals not implemented
        for (int i = 0; i < 10; ++i) {
            Van expectedVan = expected.get(i);
            Van actualVan = actual.get(i);

            Assert.assertEquals(expectedVan.getId(), actualVan.getId());
            Assert.assertEquals(expectedVan.isLoaded(), actualVan.isLoaded());
            Assert.assertEquals(expectedVan.isPriority(), actualVan.isPriority());
        }
    }

    @Test(expected = DataException.class)
    public void testReadShouldThrowExceptionWhenInvalidApplied() throws DataException {
        reader.read(INVALID_INPUT);
    }
}
