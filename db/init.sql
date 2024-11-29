-- 스키마 생성
CREATE DATABASE IF NOT EXISTS lupang_schema;
USE lupang_schema;

-- 사용자 생성
DROP USER IF EXISTS 'admin'@'%';
CREATE USER 'admin'@'%';

-- 암호 설정
ALTER USER 'admin'@'%' IDENTIFIED BY 'admin';

-- 권한 부여
GRANT ALL PRIVILEGES ON lupang_schema.* TO 'admin'@'%';
FLUSH PRIVILEGES;

-- 약관 동의 테이블
CREATE TABLE terms (
   id BIGINT AUTO_INCREMENT NOT NULL,     -- id는 자동증가
   version VARCHAR(30) NOT NULL,          -- 약관 버전
   order_type INT DEFAULT 2,              -- 필수(1) 또는 선택(2)
   name VARCHAR(100),                     -- 약관 이름
   content VARCHAR(255),                  -- 약관 설명
   created_at TIMESTAMP,                  -- 생성 일자
   updated_at TIMESTAMP,                  -- 수정 일자
   PRIMARY KEY (id, version)              -- 복합키 (id + version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 사용자 테이블
CREATE TABLE user_info (
     id BIGINT AUTO_INCREMENT NOT NULL,           -- id
     email VARCHAR(255) UNIQUE NOT NULL,          -- 이메일 암호화
     phone_number VARCHAR(255) UNIQUE NOT NULL,   -- 핸드폰 번호 (하이푼 제거!)
     user_name VARCHAR(255) NOT NULL,             -- 회원 이름
     passwd_enc VARCHAR(255) NOT NULL,            -- 비밀번호 암호화
     created_at TIMESTAMP,                        -- 생성 일자
     updated_at TIMESTAMP,                        -- 업데이트 일자
     PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 사용자 + 약관 동의에 관한 맵핑 테이블
CREATE TABLE user_terms_agreement (
     id BIGINT AUTO_INCREMENT NOT NULL,         -- id
     user_id BIGINT NOT NULL,                   -- 회원 id (user_info )
     terms_id BIGINT NOT NULL,                   -- 약관 동의 id (terms)
     created_at TIMESTAMP,                      -- 생성 일자
     updated_at TIMESTAMP,                      -- 수정 일자
     PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

commit;