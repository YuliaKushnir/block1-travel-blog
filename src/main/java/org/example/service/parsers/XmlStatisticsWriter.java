package org.example.service.parsers;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.example.view.View;
import org.example.dto.StatisticsStore;

import java.io.File;

public class XmlStatisticsWriter {

    public void writeToXmlFile(StatisticsStore statisticsStore, String fileName) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(StatisticsStore.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(statisticsStore, new File(fileName));
    }

}
