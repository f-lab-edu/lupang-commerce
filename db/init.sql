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
   version VARCHAR(30) NOT NULL,          -- 약관 버전
   order_type BOOLEAN DEFAULT FALSE,      -- 필수(true) 또는 선택(false)
   name VARCHAR(100) NOT NULL,            -- 약관 이름
   content VARCHAR(255),                  -- 약관 설명
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 일자
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정 일자
   PRIMARY KEY (name, version),           -- 복합키 (name + version)
   KEY idx_name_version (name, version)   -- 추가 복합 인덱스
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
   user_id BIGINT NOT NULL,                   -- 회원 id (user_info)
   terms_name VARCHAR(100) NOT NULL,          -- 약관 이름 (terms.name)
   terms_version VARCHAR(30) NOT NULL,        -- 약관 버전 (terms.version)
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일자
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정 일자
   PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES user_info(id) ON DELETE CASCADE,
   FOREIGN KEY (terms_name, terms_version) REFERENCES terms(name, version) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- term 데이터 넣기
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', true, '만 14세 이상입니다', '필수 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', true, '쿠팡 이용약관 동의', '필수 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', true, '전자금융거래 이용약관 동의', '필수 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', true, '개인정보 수집 및 이용 동의', '필수 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', true, '개인정보 제3자 제공 동의', '필수 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', false, '마케팅 목적의 개인정보 수집 및 이용 동의', '선택 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', false, '이메일 수신 동의', '선택 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', false, 'SMS, SNS 수신 동의', '선택 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.0', false, '앱 푸시 수신 동의', '선택 약관 내용');

INSERT INTO terms (version, order_type, name, content) VALUES ('1.3', true, '만 14세 이상입니다', '필수 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('1.3', true, '개인정보 제3자 제공 동의', '필수 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('2.0', false, '마케팅 목적의 개인정보 수집 및 이용 동의', '선택 약관 내용');
INSERT INTO terms (version, order_type, name, content) VALUES ('2.0', false, '이메일 수신 동의', '선택 약관 내용');





commit;