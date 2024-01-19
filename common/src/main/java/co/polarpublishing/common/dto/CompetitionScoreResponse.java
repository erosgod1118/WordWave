package co.polarpublishing.common.dto;

// import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompetitionScoreResponse {

	private Integer competitors;
	private Integer reviews;
	private String competitorScore;

	public CompetitionScoreResponse() {
	}

	public CompetitionScoreResponse(Integer competitors, Integer reviews, String competitorScore) {
		this.competitors = competitors;
		this.reviews = reviews;
		this.competitorScore = competitorScore;
	}

}
