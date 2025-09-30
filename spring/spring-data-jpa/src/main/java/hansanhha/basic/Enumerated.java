package hansanhha.basic;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enumerated {

    @Id
    @GeneratedValue
    private Long id;

    @jakarta.persistence.Enumerated(EnumType.STRING)
    private Role role;

    @RequiredArgsConstructor
    @Getter
    public enum Role {
        ADMIN("A"),
        MANAGER("M"),
        USER("U");

        private final String code;
    }

}
