package co.polarpublishing.userservice.repository.write;

import co.polarpublishing.dbcommon.repository.BaseReadWriteRepository;
import co.polarpublishing.userservice.entity.TrialQuestionnaire;

public interface TrialQuestionnaireWriteRepository extends BaseReadWriteRepository<TrialQuestionnaire, Long> {

	TrialQuestionnaire findByUserId(Long userId);
	
}
