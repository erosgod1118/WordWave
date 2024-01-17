package co.polarpublishing.dbcommon.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "resources")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Resource {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ResourceCategory resourceCategory;

    @Column(name = "url")
    private String url;

    @Column(name = "extension")
    private String extension;

    @Column(name = "video_id")
    private String videoId;

}
