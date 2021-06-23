package co.kr.sppartners.Controller;

import co.kr.sppartners.service.UserBatisService;
import co.kr.sppartners.service.UserService;
import co.kr.sppartners.vo.UserVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserBatisService userBatisService;

    public UserController(UserService userService, UserBatisService userBatisService) {
        this.userService = userService;
        this.userBatisService = userBatisService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    /**************** JPA **********************/
//    @PostMapping("/signup")
//    public ResponseEntity<User> signup(
//            @Valid @RequestBody UserDto userDto
//    ) {
//        return ResponseEntity.ok(userService.signup(userDto));
//    }

//    @GetMapping("/user")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<User> getMyUserInfo() {
//        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
//    }


//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
//    }

    /**************** mybatis ********************/
    //Mybatis 가입
    @PostMapping("/signup")
    public ResponseEntity<UserVo> signupMybatis(
            @Valid @RequestBody UserVo userVo
    ) throws Exception {
        return ResponseEntity.ok(userBatisService.signup(userVo));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserVo> getMyUserInfo() {
        return ResponseEntity.ok(userBatisService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserVo> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userBatisService.getUserWithAuthorities(username).get());
    }
}
