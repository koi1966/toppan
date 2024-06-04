package toppan.example.toppan.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import toppan.example.toppan.security.ClientStatus;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private ClientStatus status;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final UserEntity that = (UserEntity) o;
        return getUsername() != null && Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
