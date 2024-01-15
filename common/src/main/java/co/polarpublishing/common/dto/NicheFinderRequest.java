package co.polarpublishing.common.dto;

import co.polarpublishing.common.model.StoreTypeName;
import com.keepa.api.backend.structs.AmazonLocale;
import lombok.Data;

@Data
public class NicheFinderRequest {

	private AmazonLocale marketplace;

	private StoreTypeName bookType;

	private Long marketplaceId;

	private Integer currentBSRGte;
	private Integer currentBSRLte;
	private Integer currentSalesGte;
	private Integer currentSalesLte;
	private Integer currentRatingGte;
	private Integer currentRatingLte;
	private Integer currentCountReviewGte;
	private Integer currentCountReviewLte;
	private Integer currentListPriceGte;
	private Integer currentListPriceLte;
	private Integer publicationDateGte;
	private Integer publicationDateLte;

	private Long rootCategory;
	private long[] subCategories;

	private String title;
	private String[] manufacturer;

	private Integer pageSize;
	private Integer pageNr;
	// private Integer keepaPageNr;

	private String[] sortDetails; // [0] - kind, [1] - asc or desc

	/**
	 * Because Keepa perPage min value is 50 and we need a way to adjust FE
	 * page which can have values less then 50
	 * 
	 * @return keepaPageNr
	 */
	public Integer getKeepaPageNr() {
		return this.pageNr * pageSize / 50;
	}

	public String getTitle() {
		// cache null or empty string as same object;
		if (this.title != null && this.title.isEmpty()) {
			return null;
		}
		return this.title;
	}

}
