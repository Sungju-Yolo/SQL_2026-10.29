/*
 * SELECT => Data Query Language DQL / 질의
 * 
 * INSERT, UPDATE, DELETE => Data Manipulation Language DML / 조작
 */

-- < INSERT > / 
/*
 * 새로운 행을 추가하는 구문
 * INSERT INTO 티에블명 VALUES(값, 값, 값...);
 */
-- 새로운 진료과 추가
INSERT INTO DEPT VALUES('D9', '안과', '기타');

SELECT * FROM DEPT;

-- 새로운 병동 추가
INSERT 
  INTO 
       WARD 
VALUES
       (
       'W6'
     , '재활병동'
     , '일반'
	 , 6     
       );
SELECT * FROM WARD;

INSERT
  INTO
       DOCTOR(DOCTOR_ID, DOCTOR_NAME, POSITION_ID, HIRE_DATE)
VALUES      
       (
       'DR13'
	 , '홍실동'
     , 'J4'  
     , SYSDATE  
       );
SELECT * FROM DOCTOR;
--------------------------------------------------------------- INSERT 끝
-- DELETE
/*
 * DELETE FROM 테이블명 WHERE 조건;
 * 
 * 테이블에서 행을 삭제하는 구문
 */

SELECT * FROM DOCTOR;
DELETE FROM DOCTOR WHERE DOCTOR_ID = 'DR13';

/*
 * TRUNCATE -> 전체삭제(롤백 불가, 빠름)
 * DELETE 	-> 전체삭제(롤백 가능)
 */
--------------------------------------------------------------- DELETE 끝
/*
 * UPDATE
 *  
 * UPDATE
 * 		  테이블명
 *    SET
 *        컬렴명 = 바꿀값
 *  WHERE  
 *        조건;
 */
SELECT * FROM DEPT;

UPDATE
 	   DEPT
   SET
	   DEPT_TYPE = '안과'
	 , DEPT_NAME = '안과'  
 WHERE
       DEPT_ID = 'D9';



















