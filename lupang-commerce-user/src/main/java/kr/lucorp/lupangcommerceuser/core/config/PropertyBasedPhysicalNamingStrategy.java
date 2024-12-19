package kr.lucorp.lupangcommerceuser.core.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class PropertyBasedPhysicalNamingStrategy extends SpringBeanAutowiringSupport implements PhysicalNamingStrategy {

  @Autowired
  private Environment environment;

  public PropertyBasedPhysicalNamingStrategy() {
    super();
  }

  @Override
  public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
    // 현재 엔티티의 클래스명 (Hibernate context에서 사용)
    String entityName = jdbcEnvironment.getClass().getSimpleName();
    String fieldName = logicalName.getText();

    // 기본 프로퍼티 키 생성: entity.column.<엔티티명>.<필드명>
    String propertyKey = "entity.column." + entityName + "." + fieldName;

    // 복합키 지원: 필드 이름에 "." 포함된 경우 처리
    if (fieldName.contains(".")) {
      propertyKey = "entity.column." + entityName + "." + fieldName.replace(".", ".");
    }

    // yml에서 컬럼명 조회
    String configuredName = environment.getProperty(propertyKey);

    // 설정값이 존재하면 해당 컬럼명 반환
    if (configuredName != null && !configuredName.isEmpty()) {
      return Identifier.toIdentifier(configuredName);
    }

    // 기본값으로 필드명 그대로 반환
    return logicalName;
  }

  @Override
  public Identifier toPhysicalCatalogName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
    return logicalName;
  }

  @Override
  public Identifier toPhysicalSchemaName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
    return logicalName;
  }

  @Override
  public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
    return logicalName;
  }

  @Override
  public Identifier toPhysicalSequenceName(Identifier logicalName,
      JdbcEnvironment jdbcEnvironment) {
    return logicalName;
  }
}
