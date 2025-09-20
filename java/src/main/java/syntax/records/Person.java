package syntax.records;


// 레코드 타입 정의
// 레코드는 불변 객체를 만들기 위한 간결한 방법을 제공한다
// Address 레코드를 포함한다
public record Person(String name, int age, Address address) {

    /*
        암묵적으로 생성되는 코드

        private final String name;
        private final int age;

        // Canonical constructor
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String name() {
            return name;
        }

        public int age() {
            return age;
        }

        equals, hashCode, toString 메서드도 자동으로 생성된다
     */

    // Compact Constructor
    public Person {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다");
        }

        if (age < 0) {
            throw new IllegalArgumentException("나이는 음수일 수 없습니다");
        }
    }

    // Non-canonical Constructor
    public Person(String firstName, String lastName, int age, Address address) {
        this(firstName + " " + lastName, age, address);
    }

    // Non-canonical Constructor
    public Person() {
        this("이름 없음", 0, null);
    }

}
