package co.polarpublishing.userservice.assembler;

import co.polarpublishing.dbcommon.entity.Marketplace;
import org.springframework.stereotype.Component;

import co.polarpublishing.common.dto.keys.MarketplaceKeysDto;

@Component
public class MarketplaceAssembler {

	public MarketplaceKeysDto toDto(Marketplace marketplace) {
		return MarketplaceKeysDto.builder()
						.id(marketplace.getId())
						.marketplace(marketplace.getName())
						.build();
	}

}

