package ua.knu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class News {

    private String title;

    private String author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return title.equals(news.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
