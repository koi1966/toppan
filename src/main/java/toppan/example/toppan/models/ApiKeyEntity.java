package toppan.example.toppan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import toppan.example.toppan.security.ClientStatus;

import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "api_keys")
public class ApiKeyEntity {

    @Id
    private String apiKey;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private ClientStatus status;

    private String client;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final ApiKeyEntity that = (ApiKeyEntity) o;
        return getApiKey() != null && Objects.equals(getApiKey(), that.getApiKey());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
