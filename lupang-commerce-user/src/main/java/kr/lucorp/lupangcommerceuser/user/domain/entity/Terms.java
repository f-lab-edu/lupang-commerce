package kr.lucorp.lupangcommerceuser.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import kr.lucorp.lupangcommerceuser.common.client.model.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "terms", indexes = @Index(name = "idx_name_version", columnList = "name, version"))
public class Terms extends BaseEntity {

  @EmbeddedId
  private TermsId termsId;

  @Column(name = "order_type")
  private Boolean orderType = false;    // 필수(true) or 선택(false)

  @Column(name = "content")
  private String content;     // 약관 설명
}

