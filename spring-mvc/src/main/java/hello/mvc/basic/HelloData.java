package hello.mvc.basic;

import lombok.Data;

@Data   // getter, setter, toString, equalsAndHashcode, RequiredArgsConstructor 자동생성
public class HelloData {

    private String username;
    private int age;
}
