package org.example;

import org.example.controller.PostStatisticsController;
import org.example.view.View;

/**
 * Запуск з Maven:
 * mvn package
 * mvn exec:java "-Dexec.args=src/main/resources/json-data-files category"
 *
 * Для роботи програми передати два аргументи:
 * 1) шлях до папки: src/main/resources/json-data-files
 * 2) атрибути: category, username або country

 Приклад:
        src/main/resources/json-data-files category
        src/main/resources/json-data-files username
        src/main/resources/json-data-files country
 */

public class Main {

    public static void main(String[] args) throws Exception {

        View view = new View();

        PostStatisticsController controller = new PostStatisticsController(view);

//        long start = System.nanoTime();
        controller.launchStatisticsGeneration(args);
//        long end = System.nanoTime();
//        System.out.println("Виконання launchStatisticsGeneration: " + (end - start)/1_000_000 + " ms");

    }
}