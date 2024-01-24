package co.polarpublishing.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.polarpublishing.dbcommon.entity.Marketplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "amazon_keys")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AmazonKeys {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "marketplace_id")
	private Marketplace marketplace;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "associate_id")
	private String associateId;

	@Column(name = "access_key")
	private String accessKey;

	@Column(name = "secret_key")
	private String secretKey;

}
