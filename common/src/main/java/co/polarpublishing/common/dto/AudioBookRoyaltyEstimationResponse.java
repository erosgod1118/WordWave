package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AudioBookRoyaltyEstimationResponse {

	private String audioBookPrice;
	private String royaltyRange;

}
