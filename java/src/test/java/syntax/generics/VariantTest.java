package syntax.generics;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VariantTest {
    
    @Test
    void 제네릭은_무공변이므로_부모제네릭타입과_자식제네릭타입은_별개이다() {
        List<Animal> animals;
        List<Dog> dogs = new ArrayList<>();

        // List<Animal>과 List<Dog>는 서로 다른 제네릭 타입이므로 컴파일 에러가 발생한다
        //animals = dogs;

        // 제네릭 타입끼리만 베타적이고 지정된 타입 매개변수의 자식 인스턴스까지 제한하지는 않는다
        // List<Animal>의 Animal은 Dog, Cat의 부모이므로 아래의 코드는 정상적으로 컴파일된다
        animals = new ArrayList<>();
        animals.add(new Cat());
        animals.add(new Dog());

        // List<Dog> 제네릭 타입은 Dog 타입까지만 허용하므로 상위 타입인 Animal과 형제 타입 Cat은 허용하지 않는다
        dogs.add(new Dog());
        //dogs.add(new Animal());
        //dogs.add(new Cat());
    }

    @Test
    void 배열은_공변이므로_부모배열은_자식배열을_포함한다() {
        Animal[] animals;
        Dog[] dogs = new Dog[10];
        
        // 배열은 공변이므로 aniaml 배열은 dog 배열의 부모로 취급된다
        // 따라서 아래의 코드는 컴파일 에러가 발생하지 않는다
        animals = dogs;

        // 아래 코드는 컴파일이 되지만 배열은 런타임에 실제 타입 정보를 알게 되므로
        // ArrayStoreException 예외가 발생한다
        Assertions.assertThatThrownBy(() -> {
            animals[0] = new Cat();
        }).isInstanceOf(ArrayStoreException.class);
    }



    private class Animal {}
    private class Dog extends Animal{}
    private class Cat extends Animal{}
}
