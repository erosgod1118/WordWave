package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.userservice.assembler.AmazonKeysAssembler;
import co.polarpublishing.userservice.assembler.MarketplaceAssembler;
import co.polarpublishing.common.dto.keys.AmazonKeysDto;
import co.polarpublishing.common.dto.keys.MarketplaceKeysDto;
import co.polarpublishing.userservice.entity.AmazonKeys;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.exception.KeysForSuchMarketplaceAlreadyExistsException;
import co.polarpublishing.userservice.exception.KeysNotFoundException;
import co.polarpublishing.userservice.repository.read.MarketplaceReadRepository;
import co.polarpublishing.userservice.repository.write.MarketplaceWriteRepository;
import co.polarpublishing.userservice.repository.write.AmazonKeysWriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AmazonKeysServiceImpl implements AmazonKeysService {

    private AmazonKeysWriteRepository amazonKeysWriteRepository;
    private AmazonKeysWriteRepository amazonKeysReadRepository;
    private AmazonKeysAssembler assembler;
    private MarketplaceWriteRepository marketplaceWriteRepository;
    private MarketplaceReadRepository marketplaceReadRepository;
    private MarketplaceAssembler marketplaceAssembler;

    @Override
    public List<MarketplaceKeysDto> getListOfMarketplaces() {
        return marketplaceReadRepository.findAll().stream()
            .map(marketplaceAssembler::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public AmazonKeysDto addKeys(Long userId, AmazonKeysDto dto) throws KeysForSuchMarketplaceAlreadyExistsException {
        AmazonKeys keys = amazonKeysWriteRepository.findByUserAndMarketplace(userId, dto.getMarketplace().getId());
        if (keys != null) {
            throw new KeysForSuchMarketplaceAlreadyExistsException();
        }

        keys = amazonKeysWriteRepository.save(assembler.toEntity(userId, dto));
        keys.setMarketplace(marketplaceWriteRepository.getOne(keys.getMarketplace().getId()));

        return assembler.toDto(keys);
    }

    @Override
    public AmazonKeysDto updateKeys(Long keysId, AmazonKeysDto dto) throws KeysNotFoundException {
        AmazonKeys keys = amazonKeysWriteRepository.getOne(keysId);
        keys.setAssociateId(dto.getAssociateId());
        keys.setAccessKey(dto.getAccessKey());
        keys.setSecretKey(dto.getSecretKey());
        keys = amazonKeysWriteRepository.save(keys);
        keys.setMarketplace(marketplaceWriteRepository.getOne(
                keys.getMarketplace().getId()));

        return assembler.toDto(keys);
    }

    @Override
    public void deleteKeys(Long id) throws KeysNotFoundException {
        try {
            amazonKeysWriteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new KeysNotFoundException();
        }
    }

    @Override
    public List<AmazonKeysDto> getKeysForUser(User user) {
        return amazonKeysReadRepository.findByUser(user).stream().map(assembler::toDto)
                .collect(Collectors.toList());
    }

}
