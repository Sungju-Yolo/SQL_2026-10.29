/*
 * < DDL (Data Definition Language) > -> 정의언어
 *  
 * 데이터 정의 언어
 * 객체(테이블, 뷰, 시퀀스, 프로시져, 트리거, 함수 등등...)를
 * 생성(CREAT), 변경(ALTER), 삭제(DROP)하는 구문
 */

-- CREATE TABLE
/*
 * CREATE TABLE 테이블명 (
 *   컬럼명 자료형,
 *   컬럼명 자료형,
 *   ...
 * );
 * 
 * CHAR(1~2000) - Byte 단위 : 고정길이문자
 * NVARCHAR2(1~4000) : 영어로 1글자부터 4000글자까지
 * TIMESTAMP : 0.000초까지 기록하므로 경주나 레코드 기록에 좋음
 * 
 * NVARCHAR2(4000) 는 VARCHAR2(4000 CHAR)로 대체할 수 있음
 * --
 * VARCHAR2(크기) : 문자 / 문자 저장할 때 사용하는 자료형 // VARIABLE CHAR 2  직역 : 가변 차 2배짜리 -> 가변길이문자 : 최대 4000Byte
 * NUMBER 		 : 숫자 / 저장할 때 사용하는 자료형 (정수/실수)
 * DATE			 : 날짜 / 저장할 때 사용하는 자료형 -> 년월일시분초
 * -- 
 * CLOB			 : 대용량 문자 / VARCHAR2 로 감당이 안될 때 사용
 */
-- 진료기록 테이블 만들기 -> 환자가 진료를 받을 때마다 한행씩 쌓이는 테이블
/*
 * 제약 조건
 * - NOT NULL : NULL값을 허용하지 않음
 * - UNIQUE : 중복값을 허용하지 않음
 * - PRIMARY KEY : NOT NULL + UNIQUE -- 식별자에게 써준다 하나만 가능 : PK
 * - CHECK : 지정한 조건에 맞는 값만 허용 (조건식) : CK
 * - FOREIGN KEY : 다른 테이블의 컬럼값만 허용(참조 무결성) -- 참조무결성 제약조건 외래키 제약조건 : FK
 * 
 * 
 *  CONSTRAINT TREAT_COST_CK - 제약조건 이름 짓기
 * 제약조건은 아닌데, => 기본값 설정 : DEFAULT 는 제약조건이 아님
 * [제약조건 명시 방법]
 * 컬럼명 자료형 제약조건 -> 컬럼 레벨
 */
CREATE TABLE TREATMENT(
   RECORD_ID NUMBER, -- PK : 
   PATIENT_ID CHAR(3) NOT NULL, -- 제약조건
   DOCTOR_ID CHAR(4) NOT NULL REFERENCES DOCTOR (DOCTOR_ID),
   TREAT_DATE DATE DEFAULT SYSDATE, -- 현재시작 기본설정
   PRESCRIPTION VARCHAR2(200),
   COST NUMBER,
   PRIMARY KEY (RECORD_ID),
   FOREIGN KEY (PATIENT_ID) REFERENCES PATIENT (PATIENT_ID),
   CONSTRAINT TREAT_COST_CK CHECK (COST >= 100000)
);

DELETE FROM TREATMENT WHERE RECORD_ID = '2'; -- 행 지우기
COMMENT ON COLUMN TREATMENT.RECORD_ID IS '기록번호'; -- 커멘트 달기

SELECT * FROM TREATMENT; -- 전체 조회

DROP TABLE TREATMENT; -- 테이블 지우기
SELECT *
  FROM USER_TAB_COLUMNS
 WHERE TABLE_NAME = 'TREATMENT'; 

INSERT INTO TREATMENT(RECORD_ID, PATIENT_ID, DOCTOR_ID)
VALUES(1, 'P01', 'DR02');
INSERT INTO TREATMENT
VALUES(2, 'P02', 'DR03', SYSDATE, '위소매절제술', 10000);

SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, SEARCH_CONDITION
  FROM USER_CONSTRAINTS
 WHERE TABLE_NAME = 'TREATMENT';

/*
 * 웹사이트를 가입하고 나서 회원 탈퇴
 * 
 * -> DELETE 탈퇴요청 처리
 * 
 * FK => 삭제 옵션
 * -옵션 없음(기본) : 자식이 있으면 부모 삭제 불가
 * - ON DELETE CASCADE : 부모 삭제 시 자식도 함께 삭제
 * - ON DELETE SET NULL : 부모 삭제 시 자식의 FK를 NULL로
 */
---------------------------------------------------------

-- ALTER : 구조수정 명령문

-- 컬럼을 추가
ALTER TABLE TREATMENT ADD (NEXT_VISIT DATE);

-- 컬럼을 수정
ALTER TABLE TREATMENT MODIFY (PRESCRIPTION VARCHAR2(1000));

-- 안되는 케이스
ALTER TABLE TREATMENT MODIFY (PRESCRIPTION NUMBER); 

-- 컬럼명 변경
ALTER TABLE TREATMENT RENAME COLUMN NEXT_VISIT TO VISIT_DATE;

-- 제약 조건 추가
ALTER TABLE TREATMENT ADD CONSTRATINT CONSTRATINT VISIT_UQ UNIQUE(VISIT_DATE);

-- NOT NULL
ALTER TABLE TREATMENT MODIFY (VISIT_DATE NOT NULL);

--제약 조건 삭제
ALTER TABLE TREATMENT DROP CONSTRAINT VISIT_UQ;

SELECT * FROM TREATMENT; -- 전체 조회

-----------------------------------------------------------

-- DROP TABLE
/*
 * DROP TABLE 테이블명;
 * DROP TABLE 테이블명 CASCADE CONSTRAINTS; -> 이 테이블을 참조하는 FK까지 같이 제거
 * DROP TABLE 테이블명 PURGE; : 휴지통에 안넣고 바로 지우므로 공부용으로만 쓰는게 좋다.
 * 
 * FLASHBACK TABLE TREATMENT TO BEFORE DROP; 휴지통에 있는거 되살리기
 * PURGE RECYCLEBUN; 휴지통 비우기. 플래쉬 백 하고 비우기는 거의 안쓴다 / 개발자는 이것을 볼 일이 없다. (DB 전문가들)
 */

