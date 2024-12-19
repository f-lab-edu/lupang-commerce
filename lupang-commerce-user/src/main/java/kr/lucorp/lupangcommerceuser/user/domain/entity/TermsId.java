package kr.lucorp.lupangcommerceuser.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class TermsId implements Serializable {

  @Column
  private String name;

  @Column
  private String version;

  public TermsId(String name, String version) {
    this.name = name;
    this.version = version;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version);
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) return true;
    if(obj == null || getClass() != obj.getClass()) return false;
    TermsId termsId1 = (TermsId) obj;
    return Objects.equals(name, termsId1.name) && Objects.equals(version, termsId1.version);
  }
}
