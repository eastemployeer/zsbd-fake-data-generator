package pl.kpkpur.zsbddatagenerator.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class ReviewId implements Serializable {
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "MOVIE_ID")
    private Long movieId;

    public ReviewId(Long customerId, Long movieId) {
        this.customerId = customerId;
        this.movieId = movieId;
    }
}
