package pl.kpkpur.zsbddatagenerator.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewId implements Serializable {
    private Long customerId;
    private Long movieId;
}
