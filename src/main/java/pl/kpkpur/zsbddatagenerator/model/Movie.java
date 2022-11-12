package pl.kpkpur.zsbddatagenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private String director;
    private Integer length;
    private Date premiereDate;
    private String genre;
}
