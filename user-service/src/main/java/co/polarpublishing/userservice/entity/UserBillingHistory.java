package co.polarpublishing.userservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_billing_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserBillingHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private Integer amount;

	@Column(name = "created_at")
	private Long createdAt;

	@Column(name = "invoice_link")
	private String invoiceLink;

}
