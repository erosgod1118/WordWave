package co.polarpublishing.dbcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_book_notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserBookNotification extends AbstractEntity {

	@EmbeddedId
	private Id id;

	@Embeddable
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	public static class Id implements Serializable {

		private Long userId;
		private String asin;
		private Long marketplaceId;
			
	}
}
