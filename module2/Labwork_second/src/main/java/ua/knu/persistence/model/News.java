package ua.knu.persistence.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class News {
    Integer id;
    String title;
    String text;
    Integer categoryId;
}
