package org.example.service.parsers;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.dto.PostStatisticsDto;
import org.example.dto.StatisticsStore;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class XmlStatisticsReader {

    public StatisticsStore readFromXmlFile(String fileName) throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(StatisticsStore.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StatisticsStore store = (StatisticsStore) unmarshaller.unmarshal(new FileReader(fileName));

        return store;
    }

}
