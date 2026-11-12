/*
	함수 <FUNCTION>
	단일행함수
	그룹함수
*/

SELECT
       DOCTOR_NAME
     , LENGTH(DOCTOR_NAME)
     , LENGTHB(DOCTOR_NAME) -- 해당 글자의 바이트 수 반환
  FROM 
       DOCTOR;
-- 한글 : 3Byte, 영문/숫자/특수문자 : 1Byte (한글을 하면 n * 3 으로 결과가 나옴)

/*
 * INSTR()
 * 지정한 위치부터 특정 문자를 검색하여 가장 먼저 찾은 위치 반환 
 * DUAL : 가짜 테이블, "더미" 테이블 연습으로만 쓰임 함수 반환 값, 함수 수행 결과
 */
SELECT INSTR('AABAACAABBAA', 'B') -- 3
  FROM 
       DUAL; -- 몇번째 글자인지 찾아줌
       
 SELECT INSTR('AABAACAABBAA', 'B', -1) -- 10
   FROM 
        DUAL; -- 제일 뒤에서부터 찾은 뒤 왼쪽에서 오른쪽으로 몇번째인지 확인 용도
/*
 * SUBSTR(문자열, 시작위치, 추출한 문자개수)
 */

SELECT
	   PATIENT_NAME
	 , SUBSTR(PATIENT_NAME, 1, 1) AS "성"
	 , SUBSTR(PATIENT_NAME, 2) AS "이름"
  FROM
       PATIENT;
-- 성, 이름 따로

/*
 * LPAD / RPAD (문자열, 최종길이, 덧붙일 문자) / 패딩
 */
SELECT
       PATIENT_NAME
     , LPAD(PATIENT_NAME, 10, '*')
     , RPAD(PATIENT_NAME, 10, '*')
  FROM 
       PATIENT;
-- 문자열 왼쪽, 오른쪽에 붙여줌
-- 정보 -> 마스킹처리(개인정보보호)
SELECT
       PATIENT_NAME
     , RPAD(SUBSTR(PATIENT_NAME, 1, 1), 4, '*') --성만 뽑고 마스킹 처리
     , SUBSTR(PATIENT_NAME, 1, 1) || '*' || SUBSTR(PATIENT_NAME, 3)
  FROM 
       PATIENT; -- 중간 이름만 마스킹 처리
--------------------------------------------------------------
/*
 * TRIM(문자열) : 양쪽 공백제거
 * LTRIM(문자열, 제거할문자) : 왼쪽에서 제거
 * RTRIM(문자열, 제거할문자) : 오른쪽에서 제거 
 */

SELECT '               데이터               '
     , TRIM ('               데이터               ')
     , LTRIM('aaaaa데이터', 'a')
     , RTRIM('데이터bbbbbbb', 'b')
  FROM 
       DUAL;
--------------------------------------------------------------
/*
 * 숫자 함수
 * ABS() : 절대값 
 * MOD(num1, num2) : 모듈러연산 
 * ROUND() : 반올림
 * CEIL() : 올림
 * FLOOR() : 내림
 * TRUNC() : 버림
 */

SELECT 
       MOD(10, 4)
     , ROUND(5.59999, 3)
     , CEIL(5.5)
     , FLOOR(5.5) -- 소수점에 대해서만 올리고 내리고
     , TRUNC(10.2323, 2) -- 버림
  FROM 
       DUAL;
-- 표준 체중과 실제 몸무게의 차이
SELECT 
       PATIENT_NAME
     , HEIGHT_CM - 100 "표준체중"
     , WEIGHT_KG
     , ABS(WEIGHT_KG - (HEIGHT_CM - 100)) "차이"
  FROM 
       PATIENT;
-----------------------------------------------------------
-- 날짜
-- SYSDATE
SELECT
       SYSDATE
  FROM
       DUAL;
-- 환자의 나이를 조회
SELECT
       PATIENT_NAME
     , FLOOR(FLOOR(SYSDATE - BIRTH_DATE) / 365) || '살'
     , FLOOR(MONTHS_BETWEEN(SYSDATE, BIRTH_DATE) / 12) || '살'
     , FLOOR(SYSDATE - ADMISSION_DATE) AS  "입원 일수"
  FROM  
       PATIENT;

-- 환자의 이름, 입원일 조회
SELECT 
       PATIENT_NAME
     , ADMISSION_DATE
     , LAST_DAY(ADMISSION_DATE) -- 해당 월의 가장 마지막 일
  FROM 
       PATIENT; 

SELECT
       NEXT_DAY(SYSDATE, '월요일') -- 다음 가장 가까운 요일의 날짜
  FROM     
       DUAL;

SELECT * FROM NLS_SESSION_PARAMETERS
 WHERE PARAMETER = 'NLS_DATE_LANGUAGE'
------------------------------------------------------------
/*
 * 형변환 함수
 * 
 * TO_CHAR(날짜|숫자) : 문자 타입으로 변환
 * TO_DATE(문자) 	: 날짜 타입으로 변환
 * TO_NUMBER(문자) 	: 숫자 타입으로 변환
 * 
 * < 날짜 포맷 >
 * YYYY : 연도 4자리 / YY : 2자리 / RR : 1 ~ 49 l 2000년대, 50 ~ 99 : 1900년대
 * MM : 월 2자리 / MON : 월이름(10월) 
 * DD : 일 2자리 / DAY : 요일 이름(목요일) / DY : 요일의 약자 (목)
 * HH : 12시간제 / HH24 : 24시간제
 * MI : 분, SS : 초
 */
SELECT
   	   PATIENT_NAME
  	 , ADMISSION_DATE
     --, TO_CHAR(ADMISSION_DATE, 'YYYY')
     --, TO_CHAR(ADMISSION_DATE, 'MM')
     --, TO_CHAR(ADMISSION_DATE, 'YYYY') - 2000
     , TO_CHAR(SYSDATE, 'YYY-MM-DD HH24:MI:SS DY')
  FROM
       PATIENT;
-- TO_DATE
SELECT
       PATIENT_NAME
     , ADMISSION_DATE
  FROM 
       PATIENT
 WHERE      
       ADMISSION_DATE >= TO_DATE('2026/10/20', 'YYYY-MM-DD');

SELECT 
       TO_CHAR('150000000', 'L999,999,999,999,999,999') AS "진료비"
  FROM
       DUAL;

SELECT TO_NUMBER('100') + 50 FROM DUAL;

-- NULL 처리 함수 ㅁ 중요

SELECT
       PATIENT_NAME
     , DISCHARGE_DATE
     , NVL(TO_CHAR(DISCHARGE_DATE, 'YYYY-MM-DD'), '입원 중')
  FROM  
       PATIENT;

SELECT 
       PATIENT_NAME
     , NVL(DOCTOR_ID, '미배정')
     , NVL2(DOCTOR_ID, '배정완료', '미배정')
  FROM
       PATIENT; -- 3항 연산자 느낌
------------------------------------------------------------------
/*
 * DECODE(비교대상, 조건1, 결과2, 조건2, 결과2 ..., 기본값
 * CASE WHEN 조건 THEN 결과 ... ELSE 기본값 END -- 한글은 3Byte, 영어는 1Byte
 */
SELECT
       PATIENT_NAME
     , DECODE (GENDER, 'M', '남', 'F', '여') GENDER
     , CASE GENDER 
            WHEN 'M' THEN '남성'
            WHEN 'F' THEN '여성'
            ELSE '성별 선택 안함'
        END 성별  
  FROM 
       PATIENT; 
