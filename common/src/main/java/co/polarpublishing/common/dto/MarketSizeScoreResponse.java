package co.polarpublishing.common.dto;

// import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MarketSizeScoreResponse {

	private Integer marketSizeScore;
	private String marketAverageRanking;

	public MarketSizeScoreResponse() {
	}

	public MarketSizeScoreResponse(Integer marketSizeScore, String marketAverageRanking) {
		this.marketSizeScore = marketSizeScore;
		this.marketAverageRanking = marketAverageRanking;
	}

}
