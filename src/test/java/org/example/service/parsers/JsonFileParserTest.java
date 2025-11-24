package org.example.service.parsers;

import org.example.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonFileParserTest {

    private File file;
    private final JsonFileParser parser = new JsonFileParser();

    @TempDir
    Path tempDir;

    @BeforeEach
    void createJsonFile() throws IOException {
        file = tempDir.resolve("test.json").toFile();
    }

    @Test
    void testWhenJsonArrayIsValid() throws Exception {
        String validJson = """
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

        Files.writeString(file.toPath(), validJson);

        List<Post> posts = parser.parseJsonFile(file);

        assertEquals(2, posts.size());
        assertEquals("Snowboarding in the Rockies", posts.get(1).getTitle());
        assertEquals("Australia", posts.get(0).getCountry());
    }

    @Test
    @DisplayName("Test when data is not valid json array then throws exception")
    void testWhenJsonIsNotInAnArrayThenThrowsException() throws Exception {
        String invalidJson = """
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
                """;
        Files.writeString(file.toPath(), invalidJson);

        assertThrows(IOException.class, () -> parser.parseJsonFile(file));
    }

    @Test
    void testWhenJsonArrayIsNullThenReturnsEmptyList() throws Exception {
        String json = "[]";

        Files.writeString(file.toPath(), json);

        List<Post> posts = parser.parseJsonFile(file);

        assertTrue(posts.isEmpty());
    }
}