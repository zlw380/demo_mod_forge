public class ImplMethodTest {

    public static void main(String[] args) {
        //MethodTestInterface methodTest = new MethodTestImpl();//接口中的有参method方法，参数之和为3 实现类中的无参method方法。
        MethodTestInterface methodTest = new MethodTestChild();//实现类子类中的有参method方法，参数之和为3 实现类的子类中的无参method方法。
        methodTest.method(1,2);
        methodTest.method();

        MethodTestImpl methodTestImpl = getMethodTestImpl();
        methodTestImpl.method(2,3);//实现类子类中的有参method方法，参数之和为5
    }

    public static MethodTestImpl getMethodTestImpl(){
        return new MethodTestChild();
    }
}

interface MethodTestInterface{

    default void method() {
        System.out.println("接口中的无参method方法。");
    }

    default void method(Integer figure1,Integer figure2){

        int sum = figure1 + figure2;
        System.out.println("接口中的有参method方法，参数之和为" + sum);
    }
}

class MethodTestImpl implements MethodTestInterface{

    @Override
    public void method(){
        System.out.println("实现类中的无参method方法。");
    }
}

class MethodTestChild extends MethodTestImpl{

    @Override
    public void method(){
        System.out.println("实现类的子类中的无参method方法。");
    }

    @Override
    public void method(Integer figure1,Integer figure2){

        int sum = figure1 + figure2;
        System.out.println("实现类子类中的有参method方法，参数之和为" + sum);
    }
}

/*当创建的对象为实现类对象，实现类与实现类子类都继承了接口的同名同参数列表方法时，优先执行实现类中的方法。
* 当创建的对象为实现类对象，但只有实现类子类继承了接口的同名同参数列表方法时，优先执行接口中的方法。
* 当创建的对象为实现类子类对象，则优先执行实现类子类的方法。*/
