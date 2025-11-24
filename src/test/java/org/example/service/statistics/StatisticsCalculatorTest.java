package org.example.service.statistics;

import org.example.dto.PostStatisticsDto;
import org.example.model.Post;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsCalculatorTest {

    private final StatisticsCalculator statisticsCalculator = new StatisticsCalculator(); // клас з методом toSortedDtoList
    private final List<Post> posts = List.of(
            new Post("Sakura Season in Osaka", "Blossoms, bento, and riverside walks.", "hanami_hope", "Japan", List.of("nature", "culture")),
            new Post("Tokyo Nights and Traditions", "From neon lights to ancient temples — Tokyo has it all.", "hanami_hope", "Japan", List.of("city", "culture")),
            new Post("Snowboarding in the Rockies", "Powder runs and mountain lodges.", "snow_sophie", "USA", List.of("city", "culture", "adventure"))
    );

    @Test
    void testToSortedDtoListWhenMapContainsThreeValuesThenReturnSortedDtoList() {
        Map<String, Integer> stats = Map.of(
                "Italy", 10,
                "Japan", 5,
                "Canada", 20
        );

        List<PostStatisticsDto> result = statisticsCalculator.toSortedDtoList(stats);

        assertEquals(3, result.size());
        assertEquals("Canada", result.get(0).getValue());   // найбільше значення
        assertEquals("Italy", result.get(1).getValue());
        assertEquals("Japan", result.get(2).getValue()); // найменше значення
    }


    @Test
    void testToSortedDtoListWhenMapIsEmptyThenReturnEmptyList() {
        Map<String, Integer> stats = new HashMap<>();

        List<PostStatisticsDto> result = statisticsCalculator.toSortedDtoList(stats);

        assertTrue(result.isEmpty());
    }


    @Test
    void testToSortedDtoListWhenMapContainsEqualCountsThenOrderIsStable() {
        Map<String, Integer> stats = Map.of(
                "USA", 5,
                "Italy", 10,
                "Japan", 10,
                "Canada", 20
        );

        List<PostStatisticsDto> result = statisticsCalculator.toSortedDtoList(stats);

        assertEquals(4, result.size());
        assertEquals("Canada", result.get(0).getValue());
        assertEquals("USA", result.get(3).getValue());
    }

    @Test
    void testCountStatisticsByUsername() {
        Map<String, Integer> stats = statisticsCalculator.countStatistics(posts, "username");

        assertEquals(2, stats.get("hanami_hope"));
        assertEquals(1, stats.get("snow_sophie"));
    }

    @Test
    void testCountStatisticsByCountry() {
        Map<String, Integer> stats = statisticsCalculator.countStatistics(posts, "country");

        assertEquals(2, stats.get("Japan"));
        assertEquals(1, stats.get("USA"));
    }

    @Test
    void testCountStatisticsByCategory() {
        Map<String, Integer> stats = statisticsCalculator.countStatistics(posts, "category");

        assertEquals(3, stats.get("culture"));
        assertEquals(2, stats.get("city"));
        assertEquals(1, stats.get("nature"));
    }

    @Test
    void givenInvalidAttribute_whenCountStatistics_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> statisticsCalculator.countStatistics(posts, "test"));
    }
}
