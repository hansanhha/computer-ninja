#### 인덱스
- [CAST](#cast)
- [CONVERT](#convert)
- [ISNULL](#isnull)
- [COUNT](#count)
- [REPLACE](#replace)

## CAST

CAST는 ANSI SQL 표준 함수로 대부분의 DBMS에서 동일하게 사용할 수 있다

```sql
CAST(표현식 AS 데이터타입)
-- 표현식: 변환할 값
-- 데이터타입: 변환할 타입
```

```sql
-- 문자열 -> 숫자 변환
-- 문자열 '123'을 정수 타입으로 변환
SELECT CAST('123' AS INT);
```

```sql
-- 숫자 -> 문자열 변환
-- 숫자 100을 문자열 '100'으로 변환
SELECT CAST(100 AS VARCHAR(10));
```

```sql
-- 문자열 -> 날짜 변환
-- 문자열 '2026-03-07'을 DATE 타입으로 변환
SELECT CAST('2026-03-07' AS DATE);
```

```sql
-- 소수점 변환
-- 123.456 소수점 버림 처리 (123으로 변환)
SELECT CAST(123.456 AS INT);
```

## CONVERT

CONVERT는 MSSQL의 전용 포매팅 함수이다

CAST에 날짜 포맷을 지정할 수 있는 기능이 추가된 함수라고 보면 된다

```sql
CONVERT(데이터 타입, 표현식, 스타일 코드)
-- 데이터 타입: 변환할 타입
-- 표현식: 변환할 값
-- 스타일 코드: 날짜 형식 지정 (옵션)
```

날짜 형식
- 101: `MM/DD/YYYY`
- 102: `YYYY.MM.DD`
- 112: `YYYYMMDD`
- 120: `YYYY-MM-DD HH:MM:SS`

```sql
-- 문자열 -> 숫자 변환
-- '123'을 정수형으로 변환
SELECT CONVERT(INT, '123');
```

```sql
-- 날짜 -> 문자열 변환
-- 오늘 날짜를 120 포맷 스타일로 변환 ('2026-03-07')
SELECT CONVERT(VARCHAR(10), GETDATE(), 120);
```


## ISNULL

ISNULL은 NULL 값을 다른 값으로 대체하는 함수이다

데이터베이스에서 NULL의 의미: 값이 없음 / 알 수 없음 / 존재하지 않음

NULL이 포함된 연산은 대부분 결과가 NULL이 된다

```sql
SELECT 10 + NULL
-- 결과: NULL
```

```sql
ISNULL(표현식, 대체 값)
-- ISNULL 문법
-- 표현식: NULL 여부를 검사할 값 (NULL이 아니면 표현식의 값을 그대로 사용함)
-- 대체 값: NULL일 때 대신 사용할 값
```

```sql
-- price     discount
-- 100          10
-- 200         NULL

SELECT price - ISNULL(discount - 0)
FROM products;

-- 결과
-- 90
-- 200
```

```sql
-- 문자열 처리
-- nickname이 NULL이면 빈 문자열로 처리
SELECT name + ISNULL(nickname, '')
FROM users;
```

```sql
-- 집계 결과 처리
SELECT ISNULL(SUM(amount), 0)
FROM payments
WHERE user_id = 100;
```

## COUNT

COUNT는 행의 개수를 세는 집계 함수이다

`COUNT(*)`
- 테이블에 존재하는 모든 행을 계산한다
- 컬럼 값의 NULL 여부와 상관없이 행 자체를 센다

`COUNT(column)`
- 해당 컬럼이 NULL이 아닌 행만 카운트한다
- NULL 값이면 카운트에서 제외된다 

|department|salary|
|---|----|
|A|1000|
|A|NULL|
|B|2000|

```sql
SELECT
    department,
    COUNT(*)       -- 행 수
    COUNT(salary)  -- salary 값이 있는 행
FROM employees
GROUP BY department;
```

|deparment|COUNT(*)|COUNT(salary)|
|---|---|---|
|A|2|1|
|B|1|1|

### COUNT + ISNULL 패턴

```sql
-- salary가 NULL이어도 ISNULL 함수에서 0으로 변환된다
-- 결과적으로 COUNT(*)와 동일한 결과가 된다
SELECT COUNT(ISNULL(salary, 0))
FROM employees;
```


## REPLACE

문자열 안에서 찾은 모든 대상 문자열을 다른 문자열로 바꾸는 함수이다

```sql
REPLACE (문자열 표현식, 대상 문자열, 변환할 문자열)
```

```sql
-- World 문자열을 SQL로 변경
-- Hello SQL
SELECT REPLACE('Hello World', 'World', 'SQL');
```

| 코드         | 의미                       |
| ---------- | ------------------------ |
| `CHAR(9)`  | 탭 (Tab)                  |
| `CHAR(10)` | 줄바꿈 (Line Feed)          |
| `CHAR(13)` | 캐리지 리턴 (Carriage Return) |

```sql
-- A.Message의 캐리지 리턴(줄 바꿈)을 ''으로 변경
-- 줄바꿈을 완전히 제거하려면 아래와 같이 두개를 제거함
REPLACE(REPLACE(A.Message, CHAR(13), ''), CHAR(10), '')
```