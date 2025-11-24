package org.example.service.statistics;

import org.example.dto.PostStatisticsDto;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParallelStatisticsRunner {

    public List<PostStatisticsDto> runParallelParsing(List<File> jsonFiles, String attribute, int threadCount) throws InterruptedException, ExecutionException {
        List<Future<Map<String, Integer>>> futures = submitTasks(jsonFiles, attribute, threadCount);
        Map<String, Integer> mergedStats = mergeStatistics(futures);
        return new StatisticsCalculator().toSortedDtoList(mergedStats);
    }

    private List<Future<Map<String, Integer>>> submitTasks(List<File> files, String attribute, int threadCount) {
        
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();

        for (File file : files) {
            futures.add(executor.submit(new JsonStatisticsTask(file, attribute)));
        }

        executor.shutdown();

        return futures;
    }

    private Map<String, Integer> mergeStatistics(List<Future<Map<String, Integer>>> futures) throws InterruptedException, ExecutionException {
        Map<String, Integer> globalStats = new HashMap<>();

        for (Future<Map<String, Integer>> future : futures) {
            Map<String, Integer> localStats = future.get();
            localStats.forEach((key, value) ->
                    globalStats.merge(key, value, Integer::sum)
            );
        }

        return globalStats;
    }

}
