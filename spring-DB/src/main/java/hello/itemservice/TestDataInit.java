package hello.itemservice;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;

    /**
     * 확인용 초기 데이터 추가
     * @@PostConstruct : AOP 같은 부분이 처리되지 않은 시점에 호출될 수 있음, 예를들어 @Transactional 과 관련된 AOP 가 적용되지 않은 상태로 호출될 수 있음.
     * @@EventListener : AOP 를 포함한 스프링 컨테이너가 완전히 초기화 된 후 호출되기 때문에 문제없음.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
