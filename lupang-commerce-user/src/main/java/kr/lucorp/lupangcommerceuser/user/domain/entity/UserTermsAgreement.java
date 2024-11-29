package kr.lucorp.lupangcommerceuser.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.lucorp.lupangcommerceuser.common.client.model.BaseEntity;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "user_terms_agreement")
@RequiredArgsConstructor
public class UserTermsAgreement extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserInfo userInfo;

  @Column(name = "terms_id")
  private Long termsId;
}
