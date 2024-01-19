package co.polarpublishing.userservice.assembler;

import co.polarpublishing.dbcommon.entity.Marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.polarpublishing.common.dto.keys.AmazonKeysDto;
import co.polarpublishing.userservice.entity.AmazonKeys;
import co.polarpublishing.userservice.entity.User;

@Component
public class AmazonKeysAssembler {

	private MarketplaceAssembler marketplaceAssembler;

	@Autowired
	public AmazonKeysAssembler(MarketplaceAssembler marketplaceAssembler) {
		this.marketplaceAssembler = marketplaceAssembler;
	}

	public AmazonKeysDto toDto(AmazonKeys keys) {
		return AmazonKeysDto.builder()
						.id(keys.getId())
						.accessKey(keys.getAccessKey())
						.associateId(keys.getAssociateId())
						.secretKey(keys.getSecretKey())
						.marketplace(marketplaceAssembler.toDto(keys.getMarketplace()))
						.build();
	}

	public AmazonKeys toEntity(long userId, AmazonKeysDto keys) {
			return AmazonKeys.builder()
							.associateId(keys.getAssociateId())
							.accessKey(keys.getAccessKey())
							.secretKey(keys.getSecretKey())
							.marketplace(
								Marketplace.builder()
									.id(keys.getMarketplace().getId())
									.build())
							.user(
									User.builder()
										.id(userId)
										.build())
							.build();
	}

}
