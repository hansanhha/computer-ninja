package hansanhha.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    
    private final JPAQueryFactory query;
    private final QMember member = QMember.member;    

    
}
