package ua.knu.persistence.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NewsCategory {
    private Integer id;
    private String name;
    private Integer ageLimit;
}
