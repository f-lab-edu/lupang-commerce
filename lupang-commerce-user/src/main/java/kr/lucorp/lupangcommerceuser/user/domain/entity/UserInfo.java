package kr.lucorp.lupangcommerceuser.user.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import kr.lucorp.lupangcommerceuser.common.client.model.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String email;

  @Column
  private String phoneNumber;

  @Column
  private String userName;

  @Column
  private String passwdEnc;

  @OneToMany(mappedBy = "userInfo", cascade = CascadeType.PERSIST)
  private List<UserTermsAgreement> aggremtTermList;
}
