package hansanhha.bulk;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bulkAccount")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nickname;

    private Boolean activated;

    private LocalDateTime lastLogin;

    public Account(String nickname) {
        this.nickname = nickname;
        this.activated = true;
        this.lastLogin = LocalDateTime.now();
    }
}
