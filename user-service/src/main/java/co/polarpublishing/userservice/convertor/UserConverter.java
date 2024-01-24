package co.polarpublishing.userservice.convertor;

import co.polarpublishing.userservice.dto.UserBillingHistoryDto;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.entity.UserBillingHistory;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

	public UserBillingHistoryDto convertFromBillingHistoryDao(UserBillingHistory userBillingHistory) {
		UserBillingHistoryDto userBillingHistoryDto = new UserBillingHistoryDto();

		userBillingHistoryDto.setAmount(userBillingHistory.getAmount());
		userBillingHistoryDto.setCreatedAt(userBillingHistory.getCreatedAt());
		userBillingHistoryDto.setInvoiceLink(userBillingHistory.getInvoiceLink());

		return userBillingHistoryDto;
	}

	public List<UserBillingHistoryDto> convertFromListBillingHistoryDao(List<UserBillingHistory> userBillingHistoryList) {
		List<UserBillingHistoryDto> userBillingHistoryDtoList = new ArrayList<>();

		for (UserBillingHistory ubh : userBillingHistoryList) {
				userBillingHistoryDtoList.add(convertFromBillingHistoryDao(ubh));
		}

		return userBillingHistoryDtoList;
	}

	public UserBillingHistory convertFromBillingHistoryDto(UserBillingHistoryDto userBillingHistoryDto, User user) {
		UserBillingHistory userBillingHistoryDao = new UserBillingHistory();

		userBillingHistoryDao.setUser(user);
		userBillingHistoryDao.setAmount(userBillingHistoryDto.getAmount());
		userBillingHistoryDao.setCreatedAt(userBillingHistoryDto.getCreatedAt());
		userBillingHistoryDao.setInvoiceLink(userBillingHistoryDto.getInvoiceLink());

		return userBillingHistoryDao;
	}

}
