package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.userservice.entity.UserLimit;
import co.polarpublishing.userservice.repository.write.UserLimitRepository;
import co.polarpublishing.userservice.service.UserLimitService;

import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// @Slf4j
@Service
@AllArgsConstructor
public class UserLimitServiceImpl implements UserLimitService {

	private static final Set<UserLimit.UserLimitBuilder> TRIAL_LIMITS = 
		Collections.unmodifiableSet(
			new HashSet<>(
				Arrays.asList(
					UserLimit
						.builder()
						.name("POST:/book-tracker-service/api/v1/tracking-books")
						.type("BOOK_TRACKER_BOOKS")
						.value(0)
						.max(1),
					UserLimit
						.builder()
						.name("POST:/book-tracker-service/api/v1/niche-finder")
						.type("NICHE_FINDER_SEARCHES")
						.value(0)
						.max(2),
					UserLimit
						.builder()
						.name("GET:/administration-service/api/v1/configurations/limit")
						.type("CHROME_EXTENSION")
						.value(0)
						.max(15),
					UserLimit
						.builder()
						.name("GET:/book-tracker-service/api/v1/suggestions/limit")
						.type("KEYWORD_GENERATOR_SEARCHES")
						.value(0)
						.max(3),
					UserLimit
						.builder()
						.name("POST:/keyword-research-service/api/v1/keyword-research-updates/start")
						.type("KEYWORD_RESEARCH_SEARCHES")
						.value(0)
						.max(3),
					UserLimit
						.builder()
						.name("GET:/category-explorer-service/api/v1/categoryexplorer/subcategories/")
						.type("CATEGORY_EXPLORER_SEARCHES")
						.value(0)
						.max(2)
				)
			)
		);

	UserLimitRepository userLimitRepository;

	public boolean exceeds(String endpoint, String type, Long userId, String plan) {
		if (!"Trial".equals(plan)) {
			return false;
		}

		UserLimit userLimit = userLimitRepository.findByUserIdAndType(userId, type);
		if (userLimit == null) {
			userLimit = UserLimit.builder().userId(userId).type(type).name(endpoint).value(0).max(2).build();
		}

		if (userLimit.isExceeded()) {
			return true;
		}

		userLimit.increment();
		userLimitRepository.save(userLimit);

		return false;
	}

	public void insertUserLimits(Long userId) {
		List<UserLimit> limits = new ArrayList<>();

		for (UserLimit.UserLimitBuilder limit : TRIAL_LIMITS) {
			limits.add(limit.userId(userId).build());
		}

		userLimitRepository.saveAll(limits);
	}

	public List<UserLimit> getAllLimits(Long userId) {
		return userLimitRepository.findAllByUserId(userId);
	}

	public UserLimit getSpecificLimit(Long userId, String type) {
		return userLimitRepository.findByUserIdAndType(userId, type);
	}

}