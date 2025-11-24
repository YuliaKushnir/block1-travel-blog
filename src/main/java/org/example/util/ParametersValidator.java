package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Set;

public class ParametersValidator {
    Logger log = LogManager.getLogger(ParametersValidator.class);
    private static final Set<String> SUPPORTED_ATTRIBUTES = Set.of("username", "country", "category");

    public boolean hasRequiredArgsCount(String[] args) {
        if (args.length != 2) {
            log.error("Очікується 2 аргументи — шлях до папки та назва атрибута.\n Приклад: mvn exec:java \"-Dexec.args=src/main/resources/json-data-files category\"\nСпробуйте ще раз");
            return false;
        }
        return true;
    }

    public boolean isAttributeSupported(String attribute) {
        if (!SUPPORTED_ATTRIBUTES.contains(attribute)) {
            log.error("Непідтримуваний атрибут: " + attribute
                    + ".\nПідтримувані атрибути: " + SUPPORTED_ATTRIBUTES
                    + "\nСпробуйте ще раз");
            return false;
        }
        return true;
    }

    public boolean checkIsDirectory(File folderName){
        if (!folderName.exists() || !folderName.isDirectory()) {
            log.error("Шлях не є директорією: " + folderName.getAbsolutePath());
            return false;
        }
        return true;
    }
}
