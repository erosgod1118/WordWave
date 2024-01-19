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
public class BookHistoryItem {

	protected long timeMinute;
	protected Object value;
	protected Long keywordResearchSessionId;

	@Builder(builderMethodName = "bookHistoryItemBuilder")
	public BookHistoryItem(long timeMinute, Object value, Long keywordResearchSessionId) {
		this.timeMinute = timeMinute;
		this.value = value;
		this.keywordResearchSessionId = keywordResearchSessionId;
	}
}
