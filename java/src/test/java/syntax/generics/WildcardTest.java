package syntax.generics;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class WildcardTest {
    

    @Test
    void extends는_여러자식타입을_허용한다() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog());
        animals.add(new Animal());
        animals.add(new Cat());

        printAnimals_withExtendsWildcard(animals);
    }

    @Test
    void extends는_새로운요소를_삽입할수없다() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog());
        animals.add(new Animal());
        animals.add(new Cat());

        // extends 키워드는 컬렉션의 타입 안정성을 유지하기 위해 새로운 요소를 삽입하면 컴파일 에러를 발생시킨다
        addAnimals_withExtendsWildcard(animals, new Cat());
    }

    @Test
    void extends는_null을_추가할수있다() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog());
        animals.add(new Animal());
        animals.add(new Cat());

        // null은 모든 참조 타입의 값이므로 허용된다
        addNull_withExtendsWildcard(animals);
    }

    @Test
    void 새로운요소를_삽입할때는_super를사용한다() {
        List<Animal> animals = new ArrayList<>();   
        List<Dog> dogs = new ArrayList<>();   
     
        addDogs(animals);
        addDogs(dogs);
    }

    @Test
    void super는_데이터를_읽을수없다() {
        List<Dog> dogs = new ArrayList<>();   
     
        addDogs(dogs);
        printdogs_withSuperWildcard(dogs);
    }

    private void printAnimals_withExtendsWildcard(List<? extends Animal> animals) {
        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }

    private void addAnimals_withExtendsWildcard(List<? extends Animal> animals, Animal animal) {
        // extends 키워드는 컬렉션의 타입 안정성을 유지하기 위해 새로운 요소를 삽입하면 컴파일 에러를 발생시킨다
        // animals.add(animal);
    }

    private void addNull_withExtendsWildcard(List<? extends Animal> animals) {
        animals.add(null);
    }

    private void addDogs(List<? super Dog> dogs) {
        dogs.add(new Dog());
    }

    private void printdogs_withSuperWildcard(List<? super Dog> dogs) {
        // super 키워드는 Object까지 허용하므로 컬렉션에 포함된 요소가 Object일 수 있기 때문에 컴파일 에러를 발생시킨다
        // Dog dog = dogs.getFirst();
        // Animal animal = dogs.getFirst();

        // Object 타입으로 데이터를 조회하는 경우에만 컴파일이 가능하다
        Object obj = dogs.getFirst();
    }

    private class Animal {}
    private class Dog extends Animal{}
    private class Cat extends Animal{}
}
