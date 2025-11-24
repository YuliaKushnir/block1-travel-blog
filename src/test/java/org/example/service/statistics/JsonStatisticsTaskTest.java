package org.example.service.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonStatisticsTaskTest {

    private File file;
    private final String json = """
                [
                {
                  "title": "Surfing in Australia",
                  "content": "Waves, wetsuits, and beach bonfires.",
                  "username": "surf_sam",
                  "country": "Australia",
                  "category": ["beach", "sport"]
                },
                {
                    "title": "Snowboarding in the Rockies",
                    "content": "Powder runs and mountain lodges.",
                    "username": "snow_sophie",
                    "country": "USA",
                    "category": ["sport", "mountains"]
                  }
                ]
                """;

    @TempDir
    Path tempDir;

    @BeforeEach
    void createJsonFile() throws IOException {
        file = tempDir.resolve("test.json").toFile();
    }

    @Test
    void testWhenJsonArrayIsValidAndAttributeIsValid() throws Exception {

        Files.writeString(file.toPath(), json);

        JsonStatisticsTask task = new JsonStatisticsTask(file, "username");

        Map<String, Integer> stats = task.call();

        assertEquals(1, stats.get("surf_sam"));
        assertEquals(1, stats.get("snow_sophie"));
    }

    @Test
    void testWhenJsonArrayIsInvalidAndAttributeIsValidThenThrowException() throws Exception {
        String invalidJson = """
                {
                  "title": "Surfing in Australia",
                  "content": "Waves, wetsuits, and beach bonfires.",
                  "username": "surf_sam",
                  "country": "Australia",
                  "category": ["beach", "sport"]
                }
                """;
        Files.writeString(file.toPath(), invalidJson);

        JsonStatisticsTask task = new JsonStatisticsTask(file, "username");

        assertThrows(Exception.class, task::call);
    }

    @Test
    void testWhenJsonArrayIsValidAndAttributeIsInvalidThenThrowException() throws Exception {

        Files.writeString(file.toPath(), json);

        JsonStatisticsTask task = new JsonStatisticsTask(file, "test");

        assertThrows(IllegalArgumentException.class, task::call);
    }
}