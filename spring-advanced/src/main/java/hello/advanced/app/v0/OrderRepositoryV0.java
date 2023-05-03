package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId) {
        // 저장 로직 추가

        // 예외 발생 상황 가정
        if (itemId.equals("ex")) {
            throw new IllegalArgumentException("예외 발생!");
        }

        // 저장 프로세스 1초 걸린다고 가정
        sleep(1000);


    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
