package entity.reservation;

import entity.Audit;
import entity.contstants.ScoreType;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Builder
@Table(name = "review")
public class Review extends Audit {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = true)
    private String comment;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ScoreType score;

    @Tolerate
    public Review() {}

    public static Review of(
            String comment,
            ScoreType score
    ) {
        return builder()
                .comment(comment)
                .score(score)
                .build();
    }
}
