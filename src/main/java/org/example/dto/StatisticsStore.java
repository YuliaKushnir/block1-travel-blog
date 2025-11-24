package org.example.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "statistics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StatisticsStore {

    @XmlElement(name = "item")
    protected List<PostStatisticsDto> posts;

}
