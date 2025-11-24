package org.example.service.parsers;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.example.dto.PostStatisticsDto;
import org.example.dto.StatisticsStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class XmlStatisticsWriterTest {
    @TempDir
    Path tempDir;

    @Test
    void testWriteXmlDataToFile() throws Exception {
        StatisticsStore store = new StatisticsStore(
                Arrays.asList(
                        new PostStatisticsDto("Italy", 10),
                        new PostStatisticsDto("Japan", 5),
                        new PostStatisticsDto("Canada", 3)
                )
        );

        File outputFile = tempDir.resolve("stats.xml").toFile();
        XmlStatisticsWriter writer = new XmlStatisticsWriter();

        writer.writeToXmlFile(store, outputFile.getAbsolutePath());

        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);

        JAXBContext jaxbContext = JAXBContext.newInstance(StatisticsStore.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StatisticsStore unmarshalled = (StatisticsStore) unmarshaller.unmarshal(outputFile);

        assertNotNull(unmarshalled.getPosts());
        assertEquals(3, unmarshalled.getPosts().size());
        assertEquals("Italy", unmarshalled.getPosts().get(0).getValue());
        assertEquals(10, unmarshalled.getPosts().get(0).getCount());
    }


}