

public class LambdaTest {

    public static void main(String[] args) {
        LambdaTest lambdaTest  = new LambdaTest();
        lambdaTest.method1( () -> {
            System.out.println("执行了add方法，这是一个通过lambda表达式实现的方法。");
        });
        //使用lambda表达式实现MyInterface接口的add抽象方法，本质上是一个匿名方法。
        //lambda表达式，简化掉了匿名内部类。在接口只有一个抽象方法的情况下，直接编写其实现的方法体。
    }

    private void method1(MyInterface myInterface){

        myInterface.add();
    }
}

/*
* 有相当一部分接口实际上只有一个抽象方法，
* 对于这样的接口，我们称之为函数式接口。比如Comparator、Runnable等接口。
*
* 对于函数式接口，Java8引入lambda表达式来进一步简化匿名内部类的写法，
* 因此非函数式接口是不能用lambda表达式的形式来创建接口的实例。
*
*
* Lambda表达式的形式：
    (参数1,参数2,...) -> {
        // 抽象方法实现的代码块
        ...
    }
    1.如果参数只有1个，则可以省略掉括号
    2.如果代码块中只有一行代码，则可以省略掉花括号和代码块结尾的分号
    3.如果代码块中只有一条语句，且该语句为return语句，则可以将return省略
*/



