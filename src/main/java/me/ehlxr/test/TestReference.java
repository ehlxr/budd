package me.ehlxr.test;

/**
 * Created by ehlxr on 2017/4/14.
 */
public class TestReference {
    public static void main(String[] args) {
        Person person = new Person();

        person.age = 20;
        person.name = "test20";
        System.out.println(person);
        TestReference testReference = new TestReference();
        testReference.test(person);
        System.out.println(person);
    }

    public void test(Person person) {
        person = new Person();
        person.age = 21;
        person.name = "test21";
    }

    static class Person {
        String name;
        int age;

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
