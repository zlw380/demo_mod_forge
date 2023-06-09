
public class InnerClassTest {

    public static void main(String[] args) {
        InnerClassTest innerClassTest = new InnerClassTest();
        innerClassTest.method1(new MyInterface() {
            @Override
            public void add() {
                System.out.println("执行了add方法，这是一个在匿名内部类中实现的方法。");
            }
        });
        //匿名内部类，简化掉了接口的实现类。直接把抽象方法的实现写在了匿名内部类里面。
    }

    private void method1(MyInterface myInterface){

        myInterface.add();
    }
}

interface MyInterface{
    void add();
}

