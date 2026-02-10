#### 인덱스
- [윈도우 함수 (Window Function)](#윈도우-함수-window-function)
- [윈도우 함수 전체 구조](#윈도우-함수-전체-구조)
- [윈도우 함수 종류](#윈도우-함수-종류)
- [집계 계열 윈도우 함수](#집계-계열-윈도우-함수)
  - [ORDER BY](#order-by)
- [순위 계열 윈도우 함수](#순위-계열-윈도우-함수)
  - [ROW\_NUMBER()](#row_number)
  - [RANK()](#rank)
  - [DENSE\_RANK()](#dense_rank)
- [위치 참조 계열 윈도우 함수](#위치-참조-계열-윈도우-함수)
  - [LAG(), LEAD()](#lag-lead)

## 윈도우 함수 (Window Function)

윈도우 함수는 행을 줄이지 않고 주변 행을 보면서 계산하는 함수다

기존 집계 함수와 윈도우 함수를 비교해보자

기존 집계 함수를 사용한 쿼리

```sql
-- 고객별 총 주문 금액 조회
-- 일반 집계 함수(GROUP BY)를 사용하면 유저당 1개의 행만 볼 수 있고, 개별 주문 정보는 사라진다
SELECT customer_id, SUM(amount)
FROM orders
GROUP BY customer_id;

-- 쿼리 결과
-- custmomer_id     sum
--      A           330
--      B           250
```

윈도우 함수를 사용한 쿼리

```sql
-- 주문은 그대로 두고, 고객별 합계도 같이 조회하는 쿼리
-- 행 수는 유지하며 추가 계산을 할 수 있다
SELECT
    order_id,
    customer_id,
    order_date,
    amount,
    SUM(amount) OVER (PARTITION BY customer_id) AS total_amount
FROM orders

-- 쿼리 결과
-- order id   customer_id   order_date   amount   total_amount
--    1           A         2026-02-01    100        330
--    2           A         2026-02-02    150        330
--    3           A         2026-02-03    80         330
--    4           B         2026-02-04    200        250
--    5           B         2026-02-05    50         250
```

**"윈도우"라는 단어는 각 행이 바라보는 "범위"를 의미한다**

```sql
OVER (PARTITION BY customer_id)
```
- customer_id = A인 행들만 모아서 그 묶음을 기준으로 계산한다
- 이 묶음을 윈도우라고 한다


## 윈도우 함수 전체 구조

```sql
윈도우함수(인자)        -- 일반 함수와 동일
OVER (
    PARTITION BY 컬럼
    ORDER BY 컬럼
    FRAME 절
)

SUM(amount)         -- 집계 함수
SUM(amount) OVER () -- 윈도우 함수, OVER가 붙어야만 윈도우 함수가 된다
```

`OVER(...)` 절에서 이 함수를 어떤 범위(window)에서 계산할지 정의한다

`PARTITION BY`는 데이터를 논리적으로 그룹화하는 데 사용된다
- GROUP BY와 달리 행을 줄이지 않는다
- 계산할 기준만 잡는다

`GROUP BY` -> 행 압축 (X행부터 Y행까지 압축)

`PARTITION BY` -> 계산 범위 분리 (X행부터 Y행까지만 계산 범위로 지정)

`OVER`절 내부의 `ORDER BY`는 윈도우 안에서의 행 순서를 지정하여 누적 합, 이전/다음 행, 순위 계산을 할 수 있게 한다

```sql
SELECT
    customer_id,
    order_date,
    amount,
    SUM(amount) OVER (
        PARTITION BY customer_id
        ORDER BY order_date  -- customer_id 묶음 데이터를 order_date를 기준으로 내림차순 정렬
    ) AS total_amount
FROM orders;
```

`FRAME` 절은 현재 행을 기준으로 실계 계산에 포함할 범위를 지정한다

`ROWS BETWEEN <범위> AND CURRENT ROW`
- 범위에 지정할 수 있는 값
- `UNBOUNDED PRECEDING`: 맨 처음 
- `N PRECEDING`: 이전 N개 행
- `N FOLLOWING`: 다음 N개 행

```sql
-- 최근 3개의 주문만 합산
SUM(amount) OVER (
    PARTITION BY customer_id
    ORDER BY order_date
    ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
)
```


## 윈도우 함수 종류

윈도우 함수는 주로 역할별로 분류된다

**집계 계열(Aggregate Window Functions)**
- 기존 집계 함수 + `OVER(...)`
- `SUM`: 합계
- `COUNT`: 개수
- `AVG`: 평균
- `MIN/MAX`: 최소/최대

```sql
SUM(amount) OVER (PARTITION BY customer_id)
```

**순위 계열(Ranking Functions)**
- 순서와 등수를 구분할 때
- `ROW_NUMBER`: 1,2,3...
- `RANK`
- `DENSE_RANK`
- `NTILE(n)`

**위치 참조 계열 (Offset Functions)**
- 이전/다음 행을 참조해야 할 때
- `LAG`: 이전 행
- `LEAD`: 다음 행

```sql
LAG(amount, 1) OVER (ORDER BY order_date)
```

**누적/이동 분석**
- 시간 흐름, 이동 평균, 트렌드 분석, 슬라이딩 윈도우
- `ORDER BY + FRAME`

```sql
AVG(amount) OVER (
    ORDER BY order_date
    ROWS BETWEEN 6 PRECEDING AND CURRENT ROW
)
```

**통계/분포 계열**
- 데이터 분석용
- `PERCENT_RANK`: 백분위
- `CUME_DIST`: 누적 분포
- `PERCENTILE_CONT`: 연속 백분위


## 집계 계열 윈도우 함수

예시 테이블 orders

|order_id|customer_id|order_date|amount|
|---|----|----|----|
|1|A|2026-02-01|100|
|2|A|2026-02-03|200|
|3|A|2026-02-05|150|
|4|B|2026-02-01|300|
|5|B|2026-02-03|100|

`GROUP BY`를 하면 아래와 같이 결과 행 수가 줄어들며 그룹화되지 않은 정보는 사라진다

```sql
SELECT
    customer_id,
    SUM(amount) AS total_amount
FROM orders
GROUP BY customer_id;
```

|customer_id|total_amount|
|----|---|
|A|450|
|B|400|

윈도우 함수를 사용하면 행을 유지하며 고객별 총 주문 금액을 계산할 수 있다 

SUM 함수 뿐만 아니라 평균, 최소, 최대 등 다른 집계 함수도 모두 동일하다

```sql
SELECT
    order_id,
    customer_id,
    order_date,
    amount,
    SUM(amount) OVER (PARTITION BY customer_id) AS total_amount
FROM orders;
```

|order_id|customer_id|order_date|amount|total_amount|
|---|----|----|----|---|
|1|A|2026-02-01|100|450|
|2|A|2026-02-03|200|450|
|3|A|2026-02-05|150|450|
|4|B|2026-02-01|300|400|
|5|B|2026-02-03|100|400|

아래와 같이 `OVER()`만 사용하면 전체 행에 대한 집계를 구할 수 있다

```sql
SELECT
    order_id,
    customer_id,
    amount,
    amount - AVG(amount) OVER () AS diff_from_avg -- 구매 금액 - 전체 평균
FROM orders; 
```

### ORDER BY

윈도우 집계 함수에 `ORDER BY`가 추가되면 '누적' 데이터를 계산할 수 있다

고객벽 누적 주문 금액

```sql
SELECT
    order_id,
    customer_id,
    order_date,
    amount,
    SUM(amount) OVER (
        PARTITION BY customer_id
        ORDER BY order_date
    ) AS running_total
FROM orders;
```

|order_id|customer_id|order_date|amount|total_amount|
|---|----|----|----|---|
|1|A|2026-02-01|100|100|
|2|A|2026-02-03|200|300|
|3|A|2026-02-05|150|450|
|4|B|2026-02-01|300|300|
|5|B|2026-02-03|100|400|


## 순위 계열 윈도우 함수

SQL에서 순위란 정렬 기준에 따라 행에 '번호' 또는 '등수'를 매기는 것을 말한다

```sql
순위함수()
OVER (
    PARTITION BY 컬럼 (등수 매기는 단위)
    ORDER BY 정렬 컬럼 (등수 기준)
)
```

예시 테이블 scores

|student|subject|score|
|---|----|----|
|Kim|Math|90|
|Lee|Math|80|
|Park|Math|80|
|Choi|Math|70|
|Kim|Eng|85|
|Lee|Eng|85|
|Park|Eng|60|

### ROW_NUMBER()

`ROW_NUMBER()` 함수는 비교 대상 컬럼의 값이 동일해도 무조건 순위 값을 구분한다 - 정렬된 행에 줄 번호를 붙이는 느낌

```sql
SELECT
    student,
    subject,
    score,
    ROW_NUMBER() OVER (
        PARTITION BY subject
        ORDER BY score DESC
    ) AS rank
FROM scores;
```

Math 순위 결과

|student|score|rank|
|---|----|---|
|Kim|90|1|
|Lee|80|2|
|Park|80|3|
|Choi|70|4|

### RANK()

`RANK()` 함수는 `ROW_NUMBER()`과 달리 동점을 같은 순위로 취급하며 다음 순위는 건너뛴다

```sql
SELECT
    student,
    subject,
    score,
    RANK() OVER (
        ORDER BY score DESC
        PARTITION BY subject
    ) AS rank
FROM scores;
```

Math 순위 결과

|student|score|rank|
|---|----|---|
|Kim|90|1|
|Lee|80|2|
|Park|80|2|
|Choi|70|4|

### DENSE_RANK()

`RANK()`처럼 동점을 같은 순위로 취급하지만, 다음 순위를 건너뛰지 않는다

```sql
SELECT
    student,
    subject,
    score,
    DENSE_RANK() OVER (
        ORDER BY score DESC
        PARTITION BY subject
    ) AS rank
FROM scores;
```

Math 순위 결과

|student|score|rank|
|---|----|---|
|Kim|90|1|
|Lee|80|2|
|Park|80|2|
|Choi|70|3|


## 위치 참조 계열 윈도우 함수

위치 참조는 현재 행을 기준으로 이전 또는 다음 행의 값을 가져오는 함수를 말한다

단일 행이 아닌 행과 행 사이의 관계를 나타내어 시계열, 로그, 변경 이력 분석 등에서 사용된다

```sql
-- offset: 현재 행으로부터 offset 값만큼 떨어진 행 참조 (생략 시 1)
-- default: 참조할 행이 없을 때 반환값 (생략 시 1)
함수(컬럼, offset, default)
OVER (
    PARTITION BY 컬럼
    ORDER BY 정렬 컬럼
)
```

### LAG(), LEAD()

`LAG()`: 바로 이전 행 값 참조

```sql
LAG(amount) OVER (ORDER BY order_date)
```

`LEAD()`: 다음 행 값 참조

```sql
LEAD(amount) OVER (ORDER BY order_date)
```

```sql
-- 이전 날 주문 금액 보기
SELECT
    order_date,
    amount,
    LAG(amount) OVER (
        PARTITION BY customer_id
        ORDER BY order_date
    ) AS prev_amount
FROM orders;

-- 쿼리 결과
--  order_date    amount    prev_amount
--  2026-02-01     100         NULL
--  2026-02-02     150         100
--  2026-02-03     80          150
```