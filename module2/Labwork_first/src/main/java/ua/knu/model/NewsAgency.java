package ua.knu.model;

import lombok.Value;

import java.util.List;

@Value
public class NewsAgency {
    List<NewsCategory> newsCategories;
}
