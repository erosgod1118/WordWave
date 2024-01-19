package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Resource list querying parameter data transfer object class.
 *
 * @author FMRGJ
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder(builderMethodName = "listQueryParameterDtoBuilder")
public class ListQueryParameterDto {

	private String sortingProperty;

	@Builder.Default
	private String sortingDirection = "ASC";

	@Builder.Default
	private int page = 0;

	@Builder.Default
	private int pageSize = 10;

}
