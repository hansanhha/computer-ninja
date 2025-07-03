package record;


// 기존 클래스 정의
// 장황하고 반복되는 코드가 많다
public class Person_ {

    private final String name;
    private final int age;

    public Person_(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + age;
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person_ person_ &&
                this.name.equals(person_.name) &&
                this.age == person_.age;
    }

    @Override
    public String toString() {
        return "Person_ name: " + name + " age: " + age;
    }

}
