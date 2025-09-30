#### 인덱스
- [Querydsl](#querydsl)
- [Q타입 클래스](#q타입-클래스)
- [`JPAQueryFactory`, `JPAExpressions`](#jpaqueryfactory-jpaexpressions)
- [동적 쿼리](#동적-쿼리)
- [스프링 데이터 JPA에서 지원하는 기능](#스프링-데이터-jpa에서-지원하는-기능)


## Querydsl

스프링 데이터 JPA에서 제공하는 `@Query`나 메서드 이름 파생 쿼리 기능은 복잡한 쿼리를 표현하기에 한계가 있고 JPQL을 문자열로 작성하게 되면 런타임 오류가 발생할 가능성이 높다

이에 **쿼리를 문자열이 아닌 타입 세이프한 자바 코드로 구성**하여 컴파일 타임 오류 검출 및 쿼리의 재사용성, 가독성, 복잡한 쿼리 표현 등을 높일 수 있는 프레임워크를 사용한다

대표적으로 JPA 스펙 자체에서 제공하는 Criteria API와 오픈소스 진영의 Querydsl, Blaze-Persistence가 있다

Querydsl 프로젝트 자체는 현재 개발 중단 상태에 가까우며 공식 홈페이지 또한 다른 기업에 판매한 듯하다

웬만한 비즈니스에는 여전히 유효하게 사용할 수 있다는게 중론이나 별로 탐탁치 않으면 [포크한 프로젝트](https://github.com/OpenFeign/querydsl) 또는 기타 프레임워크를 선택하면 된다

|구분|Criteria API|Querydsl|Blaze-Persistence|
|---|---|---|---|
|표준성|JPA 스펙|오픈소스|오픈소스|
|생태계|거의 안쓰임|주로 많이 쓰임(스프링 데이터 JPA 지원)|성장 중|
|메타모델|Static Metamodel 필요|Q 클래스 자동 생성|Querydsl과 유사한 DSL|
|가독성|장황|직관적 DSL, SQL-like|직관적 DSL|
|동적 쿼리|불편(Predicate 합성)|BooleanBuilder, where 가변 인자|강력|
|지원 쿼리 범위|JPQL 수준(서브쿼리 제약)|JPQL 수준(서브쿼리 제약)|JPQL 한계 극복(CTE, Window, Keyset 등)|

### 메커니즘

Querydsl의 핵심은 엔티티를 기반으로 한 **Q타입 클래스**이다

컴파일 시점에(`compileJava`) Querydsl의 어노테이션 프로세서가 엔티티를 감지하고 각 엔티티에 대응하는 Q타입 클래스를 `build/generated/sources/annotationProcessor` 디렉토리(기본 생성 경로)에 생성한다

`build/generated/sources/...`: 어노테이션 프로세서, 코드 제네레이터가 생성한 소스 코드(`.java` 파일)

`build/classes/java`: main 소스셋의 컴파일 결과(`.class` 파일)

그레이들은 어노테이션 프로세서를 쓸 경우 자동으로 `build/generated/sources/...` 디렉토리를 메인 소스셋에 추가시킨다

따라서 `src/main/java`(개발자 작성 코드)와 `build/generated/sources`(Querydsl AOT 코드)가 둘 다 같은 메인 클래스 패스(완전히 동일한 패키지 경로)에 있게 되어 Querydsl을 사용하는 자바 파일(커스텀 리포지토리)에서 Q타입 클래스에 문제없이 접근(import)할 수 있다

IDE는 그레이들이 관리하는 소스셋 정보를 가져와서 Querydsl이 생성한 디렉토리를 자동으로 소스 경로로 등록하여 Q타입 클래스를 외부 클래스가 아니라 같은 프로젝트 내 소스코드로 인식한다

최종적으로 Q타입 클래스도 `@Entity` 자바 파일과 마찬가지로 컴파일되어 `build/classes/java` 파일에 `.class` 상태로 포함된다

개발자가 Q타입을 이용해 DSL(자바) 기반 쿼리를 작성하면 `JPAQueryFactory`가 JPQL 문자열로 변환하고 이후 JPA에 실행을 위임한 뒤 결과를 반환한다 (Querydsl도 DTO 프로젝션를 지원한다)

전체 흐름 요약
- 빌드(컴파일 타임): 엔티티 분석 후 Q타입 클래스 생성
- 개발: 개발자가 Q타입을 기반으로 DSL 쿼리 작성
- 실행: Querydsl이 JPQL 문자열로 변환 -> 엔티티 매니저 실행
- 결과 매핑: 엔티티/DTO로 결과 반환

**Querydsl = Q타입 생성기 + 타입 안전 DSL + JPQL 빌더**

### 빌드 파일 설정

```kotlin
// 의존성
dependencies {
    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api") // java 17 ~
    annotationProcessor("jakarta.persistence:jakarta.persistence-api") // java 17 ~
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta") // java 17 ~ 
}

// 컴파일 시점 클래스 패스에 어노테이션 프로세서 추가
configurations.compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
}

// 컴파일 시 Q타입 클래스 생성 경로 설정
val queyrdslDir = "src/main/querydsl"
tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(file(queyrdslDir))
}

// "./gradlew clean" 시 querydsl 디렉토리 삭제 (직접 설정한 경우)
tasks.withType<Delete> {
    delete(queyrdslDir)
}
```

위의 Q타입 클래스 생성 경로 설정은 Querydsl에만 국한되는 것이 아니라 **그레이들의 `JavaCompile` 태스크의 출력 디렉토리 자체를 변경하는 것이다**

그레이들은 지정된 경로(`src/main/querydsl`)를 소스 디렉토리로 취급하고 메인 소스셋에 포함시킨다

따라서 컴파일 시 `build/classes/java/main` 경로에 패키지 경로에 맞게 `.class` 파일이 생성되며 IDE도 해당 디렉토리를 소스 루트로 인식하게 된다


## Q타입 클래스

예시 엔티티 클래스인 Member와 Team은 `ManyToOne` 양방향 연관관계를 가진다

```java
@Entity
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private Boolean activated;

    private LocalDateTime lastLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
```

```java
@Entity
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();
}
```

Querydsl의 어노테이션 프로세서가 컴파일 시점에 생성한 Q타입 클래스의 구조는 아래와 같다

기본적으로 Q타입 클래스는 엔티티 클래스 이름에 'Q' 접두사를 붙인 이름을 가진다

```java
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    // Member 엔티티에 대한 static final Q타입 인스턴스
    public static final QMember member = new QMember("member1");

    // Member 엔티티의 각 필드마다 자료형에 맞게 'Path' 객체를 생성한다
    // 필드 이름을 오름차순으로 정렬하여 생성하는 듯하다
    public final BooleanPath activated = createBoolean("activated");
    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final DateTimePath<java.time.LocalDateTime> lastLogin = createDateTime("lastLogin", java.time.LocalDateTime.class);
    public final StringPath nickname = createString("nickname");

    // 연관관계 필드도 Q타입으로 생성한다
    public final QTeam team;

}
```

```java
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeam extends EntityPathBase<Team> {

    public static final QTeam team = new QTeam("team");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Member, QMember> members = this.<Member, QMember>createList("members", Member.class, QMember.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");
}
```

`EntityPathBase<T>`: 엔티티 자체의 경로

`Path`: 쿼리에서 변수/컬렉션 등의 경로를 표현하는 인터페이스 (Q타입 클래스의 각 필드가 구현한다)

`SimplePath`: 단순 경로를 표현하며 자료형에 따라 하위 클래스로 나뉜다
- `StringPath`: 문자열 필드를 나타낸다. `like()`, `startsWith()` 등의 문자열 전용 메서드를 가진다
- `NumberPath`: 숫자 필드를 나타낸다. `gt()`, `lt()`, `between()` 등의 숫자 숫자 전용 메서드를 가진다
- `BooleanPath`, `EnumPath`, `DatePath` 등 타입별 `Path` 구현체 지원

Q타입의 메서드 호출 결과는 최종 값이 아니라 표현식(`Expression`) 객체이다

`member.username.eq("userA")`를 호출하면 `true`나 `false`가 반환되는 것이 아니라 `member.username = 'userA'`라는 조건을 나타내는 `BooleanExpression` 객체가 생성된다

`Expression` 객체들은 쿼리 빌더용 컴포넌트로 `JPAQueryFactory`가 이들을 조립하여 최종적으로 실행가능한 JPQL 문자열로 변환하고 파라미터를 바인딩한다

기본적으로 제공되는 기본 인스턴스로 대부분의 단일 엔티티 쿼리를 해결할 수 있다

만약 같은 테이블을 두 번 조인해야할 경우 Q타입의 생성자를 이용하여 새로운 별칭을 가진 인스턴스를 만들 수 있다

```java
// memberSub라는 별칭 생성
QMember member = new QMember("memberSub");

// member 엔티티의 평균 나이보다 높은 엔티티 조회
List<Member> results = queryFactory
                            .selectFrom(member)
                            .join(member.team, team)
                            .where(member.age.gt(
                                JPAExpressions
                                    .select(memberSub.age.avg())
                                    .from(memberSub)
                                    .where(memberSub.team.eq(team))
                            ))
                            .fetch();
```


## `JPAQueryFactory`, `JPAExpressions`

`JPAQueryFactory`: Querydsl의 JPQL 빌더 (Fluent API 제공, 내부적으로 엔티티 매니저 사용)

```java
@Configuration
public class QuerydslConfig {

    // 스프링 부트 자동구성 엔티티 매니저 주입
    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
```

```java
// 보통 커스텀 리포지토리 구현체에서 주입받아서 사용한다
@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    
    private final JPAQueryFactory query;
}
```

`JPAQueryFactory` 실행 결과 반환 메서드

`fetch()`: 쿼리 결과를 `List<T>`로 반환한다 (없으면 빈 리스트 반환)

`fetchOne()`: 단 건 결과를 반환한다 (없으면 `null` 반환, 2개 이상이면 `NonUniqueResultException` 발생)

`fetchFirst()`: 첫 번째 결과 하나만 반환한다 (`limit(1).fetchOne()`과 동일)

`fetchCount()`: 카운트 쿼리 (deprecated, `select(member.count()).from(member).fetchOne()`으로 대체)

`fetchResults()`: 쿼리 결과와 카운트 쿼리(페이지네이션) (deprecated, 두 개의 쿼리로 분리하여 실행 권장)

`JPAExpressions`: `select()`, `where()`나 `from()`에서 사용되는 서브쿼리를 생성할 수 있는 정적 팩토리 클래스

단일 값(스칼라)을 반환하는 스칼라 서브쿼리를 만들 때 주로 사용된다

모든 메서드가 `static`이므로 객체 생성없이 사용할 수 있으며 `JPAQueryFactory`의 `select()`, `where()`, `from()` 절에 인자로 전달된다


## 동적 쿼리

동적 쿼리란 사용자의 입력값이나 특정 조건에 따라 쿼리의 `WHERE` 절이 동적으로 변경되는 쿼리를 말한다

e.g., 사용자가 '이름'만 입력하면 이름으로 검색하고 '나이'까지 입력하면 이름과 나이로 함께 검색하는 경우

Querydsl은 동적 쿼리를 구성할 수 있는 `BooleanBuilder`와 `BooleanExpression`을 제공한다

`BooleanBuilder`: 쿼리의 `WHERE`절에 들어가는 조건(`Predicate`)들을 담는 컨테이너 클래스

`if` 문을 통해 조건이 유효할 때마다 빌더에 `.and()` 또는 `.or()` 메서드로 조건을 추가하는 방식이다

```java
public List<Member> searchWithBuilder(String usernameCond, Integer ageCond) {
    BooleanBuilder builder = new BooleanBuilder();

    if (StringUtils.hasText(usernameCond)) {
        builder.and(member.username.eq(usernamdCond));
    }

    if (ageCond != null) {
        builder.and(username.age.eq(ageCond));
    }

    return queryFactory
            .selectFrom(member)
            .where(builder) // 완성된 빌더를 where 절에 전달
            .fetch();
}
```

`BooleanExpression`: 특정 조건을 나타내는 객체

**각각의 `WHERE` 조건절을 생성하는 로직을 `BooleanExpression`을 반환하는 메서드로 분리한 뒤 이들을 조립하여 조건들을 구성할 수 있다**

조건에 맞지 않으면(파라미터가 `null`인 경우 등) 해당 메서드는 `null`을 반환한다

Querydsl의 `where()` 메서드는 인자로 전달된 `null`을 자동으로 무서힌다

```java
public List<Member> searchWithExpression(String usernameCond, Integer ageCond) {
    return queryFactory
            .selectFrom(member)
            .where(
                usernameEq(usernameCond),
                ageEq(ageCond)
            )
            .fetch();
}

private BooleanExpression usernameEq(String username) {
    return StringUtils.hasText(username) ? member.username.eq(username) : null;
}

private BooleanExpression ageEq(Integer age) {
    return age != null ? member.age.eq(age) : null;
}
```


## 스프링 데이터 JPA에서 지원하는 기능

`QuerydslPredicateExecutor`: 단일 엔티티에 대한 단순 동적 조회를 지원하는 인터페이스

`Prediate`(`BooleanExpression`) 기반 조회 기능 제공과 더불어 페이징(`QPageRequest`), 정렬(`QSort`)을 지원한다

다만 조인, DTO 프로젝션을 할 수 없으므로 단일 테이블에 대한 동적 쿼리 조회 용도로 적합하다

```java
public interface MemberRepository 
        extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

}
```

```java
QMember member = QMember.member;
List<Member> members = memberRepository.findAll(member.age.gt(20).and(member.name.contains("han")));
```

나머지는 deprecated, 웹 MVC 통합이거나 없어도 구현하는데 지장이 없을 정도의 소소한 유틸 기능 정도에 불과하여 직접 커스텀 리포지토리를 구현하는 방식이 일반적이다