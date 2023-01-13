package example;

import mypackage.HelloWorld;
import mypackage.HelloWorldService;

public class HelloWorldClient {
  public static void main(String[] argv) {
//      HelloWorld service = new HelloWorldService().getPort();
      HelloWorld service = new HelloWorldService().getHelloWorldPort();
//      //invoke business method
      String result = service.sayHelloWorldFrom("aadfjlajlfja");
      System.out.println("结果：" + result);
  }
}
