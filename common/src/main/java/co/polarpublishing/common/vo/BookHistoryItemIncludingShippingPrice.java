package co.polarpublishing.common.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BookHistoryItemIncludingShippingPrice extends BookHistoryItem {

	private double shippingPrice;

	@Builder(builderMethodName = "bookHistoryItemIncludingShippingPriceBuilder")
	public BookHistoryItemIncludingShippingPrice(double shippingPrice, long timeMinute, Object value) {
		super(timeMinute, value, null);
		this.shippingPrice = shippingPrice;
	}

}
