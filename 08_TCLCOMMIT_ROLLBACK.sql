/*
 *  중요중요중요중요 ㅁ
 * 
   < TCL (Transction Control Language) > 중요중요중요
 * 
 * < 트랜잭션 > 
 * 논리적 작업 단위 - 테이블을 어떻게 설계했나 어떻게 작업했나
 * 한 꺼번에 수행되어야 할 DML 명령어들을 하나로 묶은 것
 * 
 * 예) 영화보기 => 영화 예매 앱 => 극장(영화) => 시간 => // 여기서부터 TCL 좌석 => 결제
 *    은행거래 => 출금 UPDATE => 입금 UPDATE 
 *    입원처리 => 환자 INSERT => 담당의 배정 UPDATE => 진료기록 INSERT
 */

-- 현재 상태 확인
SELECT * FROM PATIENT WHERE PATIENT_ID = 'P36';

-- DML 수행
UPDATE PATIENT
   SET HEIGHT_CM = 999
 WHERE PATIENT_ID = 'P36';  

SELECT * FROM PATIENT WHERE PATIENT_ID = 'P36';
--> 이 시점에서 다른 세션(접속)에서 조회하면 여전히 170으로 고정
--> 확정을 짓지 않았기 때문

-- ROLLBACK
ROLLBACK; -- 변경을 취소 // TCL 처리 / COMMIT 이후에는 롤백을 해도 되돌릴 수 없다.

UPDATE PATIENT
   SET HEIGHT_CM = 171
 WHERE PATIENT_ID = 'P36'; 

COMMIT; -- 이시점에서 변경사항을 확정 저장
SELECT * FROM PATIENT WHERE PATIENT_ID = 'P36';

----------------------------------------------------------------
-- 시나리오 : 조예준(P07)환자에게 담당의 배정 + 병동 이동

SELECT PATIENT_NAME, WARD_ID, DOCTOR_ID 
  FROM PATIENT WHERE PATIENT_ID = 'P07';

UPDATE PATIENT SET DOCTOR_ID = 'DR02' WHERE PATIENT_ID = 'P07';
UPDATE PATIENT SET WARD_ID = 'W2' WHERE PATIENT_ID = 'P07';

-- 두번째 UPDATE 에서 문제가 생겼다고 가정!
ROLLBACK;
-- 담당의 배정도 같이 취소 됨. """""""""""ALL OR NOTHING"""""""""""
-- TCL의 네가지 특성 ACID A: 원자성 C: 일관성 I : 고립성 D : 내구성/ 지속성
-----------------------------------------------------------------
-- 자동 커밋
-- DBeaver : 기본이 자동 커밋 - 재생을 누르면 커밋이 됨 트랜잭션 실습 하고 싶으면 Manual Commit으로 전환
-- SQL Devloper : 기본이 수동 커밋. 환경설정 -> 데이터베이스 -> 고급 -> 자동커밋 체크박스
-- SQL Plus : 정상종료(EXIT) 시 자동 COMMIT, 비정상 종료 시 ROLLBACK
-- 자바를 이용해서 DBSM 작업 -> 자동 커밋
-----------------------------------------------------------------
-- SAVEPOINT 중간저장

INSERT INTO DEPT 
VALUES ('01', 'KH과', '기타'); -- 새로운 트랜잭션이 생김

SAVEPOINT SP1; --- 중간 저장

INSERT INTO DEPT 
VALUES ('02', 'KH과', '기타'); -- 잘못 적었을 시?

ROLLBACK TO SP1; -- SP1 롤백

SELECT * FROM DEPT; -- 테이블 조회
------------------------------------------------------------------
-- 주의 : DDL을 수행하면 자동으로 COMMIT / DDL : CREAT, ALTER, DROP

