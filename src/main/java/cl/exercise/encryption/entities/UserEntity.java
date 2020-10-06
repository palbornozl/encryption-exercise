package cl.exercise.encryption.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "tbl_user", schema = "exercise")
@IdClass(UserEntity.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@SelectBeforeUpdate
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "email")
  @NotNull(message = "email obligatorio")
  private String email;

  @Column(name = "name")
  private String name;

  @Column(name = "username")
  @NotNull(message = "username obligatorio")
  private String userName;

  @Column(name = "phone")
  private Long phone;

  @Column(name = "created_at", updatable = false, nullable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @PrePersist
  protected void prePersist() {
    if (this.createdAt == null) {
      createdAt = Timestamp.from(Instant.now());
    }
    if (this.updatedAt == null) {
      this.updatedAt = Timestamp.from(Instant.now());
    }
  }
}
