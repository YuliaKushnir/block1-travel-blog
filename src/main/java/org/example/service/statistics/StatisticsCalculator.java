package org.example.service.statistics;

import org.example.dto.PostStatisticsDto;
import org.example.model.Post;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsCalculator {

    public Map<String, Integer> countStatistics(List<Post> posts, String attribute) {
        Map<String, Integer> stats = new HashMap<>();

        for (Post post : posts) {
            switch (attribute) {
                case "username" -> stats.merge(post.getUsername(), 1, Integer::sum);
                case "country" -> stats.merge(post.getCountry(), 1, Integer::sum);
                case "category" -> {
                    if (post.getCategory() != null) {
                        for (String category : post.getCategory()) {
                            stats.merge(category.trim(), 1, Integer::sum);
                        }
                    }
                }
                default -> throw new IllegalArgumentException("Невідомий атрибут: " + attribute);
            }
        }

        return stats;
    }

    public List<PostStatisticsDto> toSortedDtoList(Map<String, Integer> stats) {
        return stats.entrySet().stream()
                .map(e -> new PostStatisticsDto(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingInt(PostStatisticsDto::getCount).reversed())
                .toList();
    }

}