package co.polarpublishing.userservice.entity;

import co.polarpublishing.dbcommon.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "user_limits")
@NoArgsConstructor
@AllArgsConstructor
public class UserLimit extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String type;
    private Integer max;
    private Integer value;

    public boolean isExceeded() {
        return value >= max;
    }

    public void increment() {
        this.value++;
    }

    public Integer remaining() {
        return max - value;
    }
}

