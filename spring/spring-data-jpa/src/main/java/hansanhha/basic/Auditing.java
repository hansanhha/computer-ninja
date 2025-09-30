package hansanhha.basic;


import jakarta.persistence.*;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


public class Auditing {

    @MappedSuperclass
    @EntityListeners(AuditingEntityListener.class)
    public static abstract class BaseEntity {
        @CreatedBy
        private String createdBy;

        @CreatedDate
        @Column(updatable = false)
        private LocalDateTime createdDate;

        @LastModifiedDate
        private LocalDateTime lastModifiedDate;

        @LastModifiedBy
        private String lastModifiedBy;
    }

    @Configuration
    public static class AuditorAwareConfig {

        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("test user");

            // 일반적으로 아래와 같이 스프링 시큐리티 인증 정보에서 사용자 정보를 추출한다
//            return () -> Optional.ofNullable(SecurityContextHolder.getContext()
//                            .getAuthentication())
//                            .map(Authentication::getName);
        }
    }

    @Entity
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Person extends BaseEntity {
        @Id
        @GeneratedValue
        private Long id;

        private String name;
    }
}
