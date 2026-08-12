package hello.spring_basic.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    // 단 하나의 객체 만들기

    public static SingletonService getInstance() {
        return instance;
    }
    // 외부에서 이 서비스를 사용하고 싶다면 SingletonService.getInstance()를 통해서만 객체를 얻어갈 수 있다

    private SingletonService(){}
    // 외부 클래스에서 객체를 생성하지 못하도록 막음

    public void logic() {
        System.out.println("싱글톤 객체 로직을 호출");
    }
}
