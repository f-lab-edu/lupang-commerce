package kr.lucorp.lupangcommerceuser.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Embeddable
@RequiredArgsConstructor
public class TermsId implements Serializable {

  @Column(name = "version")
  private String version;

  @Column(name = "name")
  private String name;

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

  public TermsId(String version, String name) {
    this.version = version;
    this.name = name;
  }
}
