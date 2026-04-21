**레플리케이션**

단일 DB를 여러 개로 만들어 읽기 부하를 수평 확장하는 기술

단일 지점으로 몰리는 부하(디스크 I/O, 네트워크 I/O)를 분산하기 위한 목적



기본 개념
- 대역폭 (Bandwidth)
- 지연시간 (Latency)
- 패킷 처리량 (PPS)
- 스루풋 (Throughput)
- 네트워크 I/O 병목
- Replication Lag
- Primary <-> Replica 간 Consistency Trade-off

레플리케이션 용어
- Replication
- Master/Primary Database
- Replica/Slave Database
- Source and Target/Destination
- Synchronizaiton
- Data Consistency
- Replication Key/Primary Key
- Data Integrity

레플리케이션 유형
- Synchronous
- Asynchronous
- Snapshot
- Transactional
- Trigger-based
- Log-based
- Merge
- Lazy/Eager
- Full Replacement
- Incremental Replacement
- Point-in-Time Recovery
- Logical
- Physical

장점 및 유즈케이스
- High Availability
- Fault Tolerance
- Disaster Recovery
- Load Balancing
- Performance Improvement
- Scalability (Read/Write)
- Data Locality

관련 개념
- Conflict Resolution
- Latency
- Bandwidth
- Downtime
- Global Transaction Identifiers (GTID, MySQL)
- Change Data Capture (CDC)
- Middleware