package org.example.service.parsers;

import jakarta.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RawTextReader {

    public List<String> getRawTextFromXml(String relativePath) {
        List<String> fileContent = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(relativePath))){
            String line;
            while((line = reader.readLine()) != null){
                fileContent.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileContent;
    }

}
