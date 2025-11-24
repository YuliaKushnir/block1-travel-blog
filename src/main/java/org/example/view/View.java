package org.example.view;

import java.io.File;
import java.util.List;

public class View {

    public final String  SUCCESS_PARSING_MESSAGE = "Проаналізовано .json файли в папці";
    public final String SUCCESS_STATISTICS_MESSAGE = String.format("Успішно створено файл зі статистикою за атрибутом ");
    private final String FILE_LOCATION = "Розташування файлу: ";
    public final String FILE_CONTENT = "\nВміст файлу: ";

    public void printSuccessParsingMessage(String filePath){
        System.out.println(SUCCESS_PARSING_MESSAGE + " " + filePath);
    }

    public void printSuccessMessage(String attribute) {
        String relativePath = String.format("src/main/resources/xml-generated-statistics/statistics_by_%s.xml", attribute);
        File outputFile = new File(relativePath);

        System.out.println(SUCCESS_STATISTICS_MESSAGE + attribute);
        System.out.println(FILE_LOCATION + outputFile.getAbsolutePath());
    }

    public void printXmlFileContent(List<String> fileContent){
        System.out.println(FILE_CONTENT);
        fileContent.stream().forEach(System.out::println);
    }


}
