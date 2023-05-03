package hello.mvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

/**
 * 회원관리 API 예시
 * - 회원 목록(전체) : GET "/uesrs"
 * - 회원 등록 : POST "/users"
 * - 회원 조회(단일) : GET "/users/{userId}"
 * - 회원 수정 : PATCH "/users/{userId}"
 * - 회원 삭제 : DELETE "/users/{userId}"
 */
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {
    @GetMapping
    public String findAll() {
        return "회원 목록";
    }

    @PostMapping
    public String save() {
        return "회원 등록";
    }

    @GetMapping("/{userId}")
    public String findById(@PathVariable String userId) {
        return "회원 조회 = " + userId;
    }

    @PatchMapping("/{userId}")
    public String update(@PathVariable String userId) {
        return "회원 수정 = " + userId;
    }

    @DeleteMapping("/{userId}")
    public String delete(@PathVariable String userId) {
        return "회원 삭제 = " + userId;
    }
}
