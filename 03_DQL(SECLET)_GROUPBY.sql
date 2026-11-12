-- ===================================================
-- 그룹 함수
-- 매우 중요 ★☆★ 

SELECT 
	   COUNT(*) "전체 환자 수"
  FROM 
       PATIENT;

SELECT 
       COUNT(DISCHARGE_DATE) "퇴원한 환자의 수"
     , COUNT(DOCTOR_ID) "배정된 환자의 수"
     , COUNT(*) - COUNT(DISCHARGE_DATE) "입원중인 환자의 수"
  FROM 
       PATIENT;
-- COUNT() : 행 수 반환 ☆★☆
-- 가장 높은 숫자나 문자를 지정
SELECT 
       MAX(WEIGHT_KG)    -- 가장 높은 몸무게
     , MIN(WEIGHT_KG) 	 -- 가장 낮은 몸무게
     , TO_CHAR(MAX(BIRTH_DATE),	'YYYY-MM-DD') -- 가장 어린 환자의 생년월일 / MAX가 고령
     , MIN(PATIENT_NAME) -- 가장 앞의 글자
     , SUM(WEIGHT_KG) 	 -- 컬럼값의 합계
     , AVG(HEIGHT_CM) 	 -- 컬럼값의 평균
  FROM  
       PATIENT;
/*
 * COUNT() : 행 수  ☆★☆
 * MIN()   : 최소값
 * MAX()   : 최대값
 * SUM()   : 합계
 * AVG()   : 평균
 */
------------------------------------------------------------------

SELECT * FROM PATIENT;

SELECT
       COUNT(*)
  FROM 
       PATIENT
 WHERE     
       DEPT_ID = 'D4';
-- 진료과별 환자수 조회

SELECT
       DEPT_ID
     , COUNT(*)
  FROM     
	   PATIENT
 GROUP  
	BY   
	   DEPT_ID
 ORDER
    BY 
       DEPT_ID;

-- 성별별 환자 수
SELECT
	   GENDER
	 , COUNT(*)
  FROM  
       PATIENT 
 GROUP 
    BY
       GENDER;
-- 병동별 환자수
SELECT
       WARD_ID
     , COUNT(*)  
  FROM 
       PATIENT
 GROUP
    BY 
  	   WARD_ID
 ORDER
    BY 
       COUNT(*);
 -- GROUP BY절에 없는 일반 컬럼을 SELECT문에 쓰면 오류
 -- 성별로 묶었는데 환자 이름을 고를 순 없음
 
 -- 병동별, 성별 환자수
SELECT 
       WARD_ID
     , GENDER
  FROM 
       PATIENT 
 GROUP 
    BY
       WARD_ID 
     , GENDER;

SELECT EXTRACT(YEAR FROM SYSDATE)
    ,  EXTRACT(MONTH FROM SYSDATE)
    ,  EXTRACT(DAY FROM SYSDATE)
  FROM     
       DUAL; -- 날짜만 쏙 뽑는 경우 
        
-- GROUP BY
SELECT
       TO_CHAR(ADMISSION_DATE, 'YYYY-MM') AS 입원일
     , COUNT(*)  
  FROM
       PATIENT
 WHERE 
       EXTRACT(MONTH FROM ADMISSION_DATE) = 9      
 GROUP 
    BY 
       TO_CHAR(ADMISSION_DATE, 'YYYY-MM')
 ORDER 
    BY 
       COUNT(*);
-------------------------------------------------------------
/*
 * 
 * 혈액형별로 환자수가 10명 이상인 혈액형이랑 환자수만 조회
 */
SELECT
       BLOOD_TYPE
     , COUNT(*) 
  FROM 
       PATIENT
       
HAVING -- 그룹 함수에 대한 조건을 달 수 있다.
       COUNT(*) >= 10
 ORDER
    BY 
       COUNT(*);

-- 진료과별 환자수
SELECT 
       DEPT_ID
     , COUNT(*)
  FROM
       PATIENT
 GROUP
    BY 
       ROLLUP(DEPT_ID);
--> 마지막 행 DEPT_ID NULL 행이 => 전체합계(COUNT(*)의 결과)
-------------------- 정리!
/*
 * SELCET	  - 5
 *   FROM     - 1
 *  WHERE     - 2
 *  GROUP BY  - 3
 * HAVING     - 4
 *  ORDER BY  - 6
 */
SELECT
       COUNT(BLOOD_TYPE)
  FROM 
       PATIENT;






       