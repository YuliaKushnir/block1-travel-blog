package org.example.service.statistics;

import org.example.model.Post;
import org.example.service.parsers.JsonFileParser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class JsonStatisticsTask implements Callable<Map<String, Integer>> {
    private final File file;
    private final String attribute;

    public JsonStatisticsTask(File file, String attribute) {
        this.file = file;
        this.attribute = attribute;
    }


    @Override
    public Map<String, Integer> call() throws Exception {
        JsonFileParser parser = new JsonFileParser();
        List<Post> posts = parser.parseJsonFile(file);

        StatisticsCalculator calculator = new StatisticsCalculator();
        return calculator.countStatistics(posts, attribute);
    }

}
