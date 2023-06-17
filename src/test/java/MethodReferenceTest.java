
public class MethodReferenceTest {

    public static void main(String[] args) {
        MethodReferenceTest methodReferenceTest = new MethodReferenceTest();
        //使用匿名内部类
        /*
        Student student = methodReferenceTest.method1(new MethodReferenceInterface() {
            @Override
            public Student createStu(String name, Integer age) {
                Student student1 = new Student(name,age);
                return student1;
            }
        });
        */

        //使用lambda表达式
        /*Student student = methodReferenceTest.method1((String name,Integer age) -> {
            return new Student(name,age);
        });*/
        //Student student = methodReferenceTest.method1((String name,Integer age) -> new Student(name, age));
        //Student student = methodReferenceTest.method1((name,age) -> new Student(name, age));

        /*对于上面的lambda表达式，都是这样的一种情况：
        lambda的形参作为某个方法的实参传入，在实际编程中有太多类似的这样的情况，
        因此对于这种代码，引入了方法引用进行简化*/
        //使用方法引用
        Student student = methodReferenceTest.method1(Student::new);
        System.out.println("使用方法引用创建的学生对象信息：" + student);
    }

    private Student method1(MethodReferenceInterface methodReferenceInterface){

        Student lisi = methodReferenceInterface.createStu("lisi1", 30);

        return lisi;
    }
}

//函数式接口
@FunctionalInterface
interface MethodReferenceInterface{

    Student createStu(String name,Integer age);
}

class Student{

    public String name;
    public Integer age;

    public Student(){}

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
