package hello.mvc.domain;

import hello.mvc.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();   // 멀티쓰레드 환경에서는 ConcurrentHashMap 사용
    private static long sequence = 0L;  // 멀티쓰레드 환경에서는 Atomic Long 사용

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long itemId) {
        return store.get(itemId);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 정석으로 하려면 updateParam 을 Item 클래스가 아니라 ItemParamDto 등 새로운 클래스를 만들어야힘. 왜냐면 지금은 Item 클래스의 id 가 사용되지 않음.
    public void update(Long itemId, Item updateParam) {
        Item findItem = store.get(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {  // 테스트용
        store.clear();
    }
}
