package co.polarpublishing.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfo {

	Long userId;
	String email;
	String plan;

	@Override
	public String toString() {
		return email + "::" + userId;
	}
	
}
