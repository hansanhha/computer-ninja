package hansanhha.basic;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;


@Converter(autoApply = true) // JPA가 모든 Role 타입 필드에 대해 자동으로 컨버터를 적용한다
public class EnumeratedConverter implements AttributeConverter<Enumerated.Role, String> {

    @Override
    public String convertToDatabaseColumn(Enumerated.Role role) {
        if (role == null) return null;
        return role.getCode();
    }

    @Override
    public Enumerated.Role convertToEntityAttribute(String code) {
        if (code == null) return null;
        return Stream.of(Enumerated.Role.values())
                .filter(r -> r.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
