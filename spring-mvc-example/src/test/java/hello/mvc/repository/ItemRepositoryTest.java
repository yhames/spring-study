package hello.mvc.repository;

import hello.mvc.domain.Item;
import hello.mvc.domain.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    @DisplayName("save item")
    void saveItem() {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(savedItem).isEqualTo(findItem);
    }

    @Test
    @DisplayName("findAll")
    void findAll() {
        // given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 20000, 20);
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // when
        List<Item> result = itemRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(itemA, itemB);
    }

    @Test
    @DisplayName("findById")
    void findById() {
        // given
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);

        // when
        Item findItem = itemRepository.findById(savedItem.getId());

        // then
        assertThat(findItem).isEqualTo(item);
    }

    @Test
    @DisplayName("update item")
    void updateItem() {
        // given
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);

        // when
        Item updateParam = new Item("item2", 20000, 20);
        itemRepository.update(savedItem.getId(), updateParam);
        Item findItem = itemRepository.findById(savedItem.getId());

        // then
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

}