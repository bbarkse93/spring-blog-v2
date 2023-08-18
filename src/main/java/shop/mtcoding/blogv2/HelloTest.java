package shop.mtcoding.blogv2;

class MyController {
    MyService myService = new MyService();

    void home(boolean check) {
        myService.홈가기(check);
    }
}

class MyService {
    MyRepository myRepository = new MyRepository();

    void 홈가기(boolean check) {
        try {
            myRepository.findHome(check);
        } catch (Exception e) {

            System.out.println("괜찮아");
        }
    }
}

class MyRepository {
    void findHome(boolean check) throws Exception {
        if (check) {
            System.out.println("조회 완료");
        } else {

            throw new RuntimeException("조회 오류");

        }

    }
}

public class HelloTest {
    public static void main(String[] args) {
        MyController myController = new MyController();
        myController.home(false);
    }
}
