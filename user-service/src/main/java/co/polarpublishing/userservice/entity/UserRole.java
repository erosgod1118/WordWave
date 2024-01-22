package co.polarpublishing.userservice.entity;

import co.polarpublishing.common.constant.RoleName;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private RoleName role;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		UserRole userRole = (UserRole) o;
		return role == userRole.role;
	}

}
