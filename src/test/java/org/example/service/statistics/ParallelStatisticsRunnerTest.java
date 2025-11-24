package org.example.service.statistics;

import org.example.dto.PostStatisticsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStatisticsRunnerTest {

    @TempDir
    Path tempDir;

    private final ParallelStatisticsRunner service = new ParallelStatisticsRunner();

    private final String json_1 = """
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

    private final String json_2 = """
                [
                {
                  "title": "Surfing in Australia",
                  "content": "Waves, wetsuits, and beach bonfires.",
                  "username": "surf_sam",
                  "country": "Australia",
                  "category": ["beach", "sport"]
                }
                ]
                """;

    @Test
    void testWhenRunParallelParsingMultipleJsonFilesByUsernameThenReturnMergedStatistics() throws Exception {
        File file1 = tempDir.resolve("test_posts_1.json").toFile();
        File file2 = tempDir.resolve("test_posts_2.json").toFile();
        File file3 = tempDir.resolve("test_posts_3.json").toFile();

        Files.writeString(file1.toPath(), json_1);
        Files.writeString(file2.toPath(), json_2);
        Files.writeString(file3.toPath(), json_2);

        List<PostStatisticsDto> result = service.runParallelParsing(List.of(file1, file2, file3), "username", 2);

        assertEquals(2, result.size());
        assertEquals("surf_sam", result.get(0).getValue());
        assertEquals(3, result.get(0).getCount());
        assertEquals("snow_sophie", result.get(1).getValue());
        assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testWhenRunParallelParsingWithEmptyFileListThenReturnEmptyList() throws Exception {
        List<File> files = List.of();

        List<PostStatisticsDto> result = service.runParallelParsing(files, "username", 2);

        assertTrue(result.isEmpty());
    }

    @Test
    void testWhenRunParallelParsingWithInvalidJsonArrayThenThrowException() throws Exception {
        String json = """
                [
                {
                  "title": "Surfing in Australia",
                  "username": "surf_sam",
                  "country": "Australia",
                }
                ]
                """;
        File file = tempDir.resolve("invalid.json").toFile();
        Files.writeString(file.toPath(), json);

        assertThrows(ExecutionException.class,
                () -> service.runParallelParsing(List.of(file), "username", 2));
    }
}