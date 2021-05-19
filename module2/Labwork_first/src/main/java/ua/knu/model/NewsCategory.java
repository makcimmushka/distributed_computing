package ua.knu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsCategory {
    private String name;
    private Integer ageLimit;
    private List<News> news;
}
