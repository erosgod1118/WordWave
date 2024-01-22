package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.common.dto.keys.AmazonKeysDto;
import co.polarpublishing.common.dto.keys.MarketplaceKeysDto;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.exception.KeysForSuchMarketplaceAlreadyExistsException;
import co.polarpublishing.userservice.exception.KeysNotFoundException;

import java.util.List;

public interface AmazonKeysService {

	List<MarketplaceKeysDto> getListOfMarketplaces();

	AmazonKeysDto addKeys(Long userId, AmazonKeysDto dto) throws KeysForSuchMarketplaceAlreadyExistsException;

	AmazonKeysDto updateKeys(Long userId, AmazonKeysDto dto) throws KeysNotFoundException;

	void deleteKeys(Long id) throws KeysNotFoundException;

	List<AmazonKeysDto> getKeysForUser(User user);

}
