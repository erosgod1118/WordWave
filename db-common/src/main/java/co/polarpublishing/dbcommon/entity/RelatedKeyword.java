package co.polarpublishing.dbcommon.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "related_keyword")
@AllArgsConstructor
@NoArgsConstructor
public class RelatedKeyword {
    @EmbeddedId
    private RelatedKeywordId id;

    @ManyToOne
    @MapsId("related_keyword_id")
    @JoinColumn(name = "related_keyword_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Keyword relatedKeyword;

    @ManyToOne
    @MapsId("keyword_id")
    @JoinColumn(name = "keyword_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Keyword keyword;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class RelatedKeywordId implements Serializable {
        @Column(name = "related_keyword_id")
        private Long relatedKeywordId;
        @Column(name = "keyword_id")
        private Long keywordId;
    }
}
