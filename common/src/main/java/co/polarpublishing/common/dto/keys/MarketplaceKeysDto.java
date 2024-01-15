package co.polarpublishing.common.dto.keys;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketplaceKeysDto {

	@NotNull(message = "Missing required parameter 'marketplace'.")
	private long id;

	@ApiModelProperty(hidden = true)
	private String marketplace;

}
