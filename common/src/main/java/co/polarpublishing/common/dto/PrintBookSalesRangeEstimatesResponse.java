package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PrintBookSalesRangeEstimatesResponse {

	private int storeRanking;

	private String dailySales;
	private String monthly;

}
