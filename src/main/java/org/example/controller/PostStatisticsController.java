package org.example.controller;

import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dto.PostStatisticsDto;
import org.example.dto.StatisticsStore;
import org.example.service.parsers.RawTextReader;
import org.example.service.parsers.XmlStatisticsWriter;
import org.example.service.statistics.ParallelStatisticsRunner;
import org.example.util.ParametersValidator;
import org.example.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PostStatisticsController {

    Logger log = LogManager.getLogger(PostStatisticsController.class);

    View view;
    ParametersValidator parametersValidator = new ParametersValidator();
    ParallelStatisticsRunner runner = new ParallelStatisticsRunner();

    String folderPath;
    String attribute;

    public PostStatisticsController(View view) {
        this.view = view;
    }

    public void launchStatisticsGeneration(String[] args){
        if(!parametersValidator.hasRequiredArgsCount(args)) return;

        folderPath = args[0];
        attribute = args[1];
        String relativePathGeneratedXml = String.format("src/main/resources/xml-generated-statistics/statistics_by_%s.xml", attribute);

        if(!parametersValidator.isAttributeSupported(attribute)) return;

        File directory = new File(folderPath);
        if(!parametersValidator.checkIsDirectory(directory)) return;

        List<File> jsonFiles = findJsonFiles(directory);
        if(jsonFiles.isEmpty()) return;

        List<PostStatisticsDto> postStatisticsDtoList = getPostStatisticsDtoList(runner, jsonFiles);
        view.printSuccessParsingMessage(folderPath);

        StatisticsStore statisticsStore = new StatisticsStore(postStatisticsDtoList);
        writeToXmlFile(statisticsStore, relativePathGeneratedXml);

        view.printSuccessMessage(attribute);
        view.printXmlFileContent(new RawTextReader().getRawTextFromXml(relativePathGeneratedXml));
    }

    private void writeToXmlFile(StatisticsStore statisticsStore, String attribute) {
        XmlStatisticsWriter writer = new XmlStatisticsWriter();
        try {
            writer.writeToXmlFile(statisticsStore, attribute);
        } catch (JAXBException e) {
            log.error("Помилка маршалінгу! Не вдалось записати дані в .xml файл", e);
            System.exit(1);
        }
    }

    public List<PostStatisticsDto> getPostStatisticsDtoList(ParallelStatisticsRunner runner, List<File> jsonFiles){
        List<PostStatisticsDto> postStatisticsDtoList = new ArrayList<PostStatisticsDto>();
        try {
            postStatisticsDtoList = runner.runParallelParsing(jsonFiles, attribute, 2);
        } catch (InterruptedException e) {
            log.error("Потік було перервано під час паралельного парсингу", e);
            throw new RuntimeException("Парсинг перервано", e);
        } catch (ExecutionException e) {
            log.error("Помилка виконання під час паралельного парсингу", e.getCause());
            throw new RuntimeException("Помилка під час парсингу", e.getCause());
        }
        return postStatisticsDtoList;
    }


    public List<File> findJsonFiles(File directory) {

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (files == null || files.length == 0) {
            log.error("Не знайдено JSON-файлів в директорії: " + directory.getAbsolutePath());
        }

        return Arrays.asList(files);
    }
}

