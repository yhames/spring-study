package hello.upload.domain;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

	private final Map<Long, Item> store = new HashMap<>();

	private long seq = 0L;

	public Item save(Item item) {
		item.setId(++seq);
		store.put(item.getId(), item);
		return item;
	}

	public Item findById(Long id) {
		return store.get(id);
	}
}
