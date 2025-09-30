```yaml
spring:
  datasource:
    url: // db url
    username: // db username
    password: // db password
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20         # 커넥션 풀 크기 (서비스 규모에 따라 조정)
      minimum-idle: 5
      idle-timeout: 30000
      connection-timeout: 30000
      max-lifetime: 1800000

  jpa:
    hibernate:
      ddl-auto: validate            # dev: create/update, prod: validate
    show-sql: false                 # 하이버네이트 SQL 로깅 끔 (로거로 제어)
    properties:
      hibernate:
        format_sql: true
        highlight_sql: true
        use_sql_comments: true

        # 배치 처리 최적화
        jdbc.batch_size: 50
        order_inserts: true
        order_updates: true

        # N+1 문제 방지
        default_batch_fetch_size: 100

        # 캐시 관련 (필요 시)
        cache.use_second_level_cache: true
        cache.use_query_cache: false
        cache.region.factory_class: org.hibernate.cache.jcache.JCacheRegionFactory

  # 트랜잭션 관련
  transaction:
    default-timeout: 30s
    rollback-on-commit-failure: true

logging:
  level:
    com.p6spy: DEBUG // p6spy를 사용하는 경우 하이버네이트 SQL 로깅을 꺼도 된다
    org.hibernate.SQL: DEBUG          # SQL 로그 출력
```