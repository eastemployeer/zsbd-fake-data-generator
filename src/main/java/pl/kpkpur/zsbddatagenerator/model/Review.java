package pl.kpkpur.zsbddatagenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Review {
    private ReviewId id;
    private String description;
    private Integer rating;
    private Date date;
    private Integer likes;
}
