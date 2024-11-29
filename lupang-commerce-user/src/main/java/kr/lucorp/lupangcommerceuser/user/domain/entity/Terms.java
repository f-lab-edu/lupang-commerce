package kr.lucorp.lupangcommerceuser.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import kr.lucorp.lupangcommerceuser.common.client.model.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name = "terms")
@IdClass(Terms.TermCompositeId.class)
public class Terms extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;            // 자동 증가 ID

  @Id
  @Column(name = "version")
  private String version;     // 버전

  @Column(name = "order_type")
  private Integer orderType;    // 필수 or 선택 (1 or 2)

  @Column(name = "name")
  private String name;        // 약관 이름

  @Column(name = "content")
  private String content;     // 약관 설명


  @EqualsAndHashCode
  @RequiredArgsConstructor
  public static class TermCompositeId implements Serializable {
    private Long id;
    private String version;
  }
}

