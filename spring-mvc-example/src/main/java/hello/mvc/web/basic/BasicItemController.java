package hello.mvc.web.basic;

import hello.mvc.domain.Item;
import hello.mvc.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";   // viewName 반환, template/basic/items.html
    }

    @PostConstruct
    public void init() {  // 테스트용 데이터 추가
        itemRepository.save(new Item("ItemA", 10000, 20));
        itemRepository.save(new Item("ItemB", 20000, 30));
        itemRepository.save(new Item("ItemC", 30000, 40));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/item";    // viewName 반환, template/basic/item.html
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    //    @PostMapping("/add")
//    public String saveV1(@RequestParam String itemName, @RequestParam int price, @RequestParam int quantity, Model model) {
//        Item item = new Item(itemName, price, quantity);
//        itemRepository.save(item);
//        model.addAttribute("item", item);
//        return "basic/item";
//    }
//
//    /**
//     * ModelAttribute 하는 일
//     * 1. 요청 파라미터 처리
//     * 2. 응답 Model 추가, name(value) 속성을 사용하여 자동 추가
//     */
////    @PostMapping("/add")
//    public String saveV2(@ModelAttribute("item") Item item) {
//        itemRepository.save(item);
//        return "basic/item";
//    }
//
//    /**
//     * name(value) 생략시 클래스 이름의 lowercase 로 자동 추가
//     */
////    @PostMapping("/add")
//    public String saveV3(@ModelAttribute Item item) {
//        itemRepository.save(item);
//        return "/basic/item";
//    }
//
//    /**
//     * 아예 다 생략해도 ModelAttribute 적용된 것이기 때문에 Model 자동 추가
//     */
////    @PostMapping("/add")
//    public String saveV4(Item item) {
//        itemRepository.save(item);
//        return "basic/item";
//    }

////    @PostMapping("/add")
//    public String saveV5(@ModelAttribute Item item) {
//        itemRepository.save(item);
//        return "redirect:/basic/items/" + item.getId();
//    }

    @PostMapping("/add")
    public String saveV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);    // 쿼리파라미터로 넘어감
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editFrom(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute(findItem);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String update(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
}