package co.polarpublishing.common.dto;

// import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpportunityScoreRespone {
	private Integer competitors;
	private Integer reviews;

	private String opportunityScore;

	private Integer salesRank;

	public OpportunityScoreRespone(Integer competitors, Integer reviews, String opportunityScore, Integer salesRank) {
		this.competitors = competitors;
		this.reviews = reviews;
		this.opportunityScore = opportunityScore;
		this.salesRank = salesRank;
	}

	public OpportunityScoreRespone() {
	}
}
