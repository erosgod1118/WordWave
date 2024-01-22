package co.polarpublishing.userservice.entity;

import java.util.List;

import co.polarpublishing.dbcommon.entity.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Boolean banned;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "user_roles",
		joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")}
	)
	private List<UserRole> roles;
	
	private String resetToken;
	private String avatarLink;
	private String userCurrentPlan;
	private String confirmationToken;
	private String userSubscriptionStatus;
	private String userLastBillingDate;
	private String userNextBillingDate;

}
