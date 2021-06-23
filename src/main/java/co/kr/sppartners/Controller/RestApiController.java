package co.kr.sppartners.Controller;

import co.kr.sppartners.dto.UserDto;
import co.kr.sppartners.entitiy.User;
import co.kr.sppartners.service.UserService;
import co.kr.sppartners.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestApiController {

    private final UserService userService;

    @GetMapping("/main")
    public String main() {
        return "main page";
    }

    @GetMapping("/selectUser")
    public List<UserVo> selectUser() throws Exception {
        List<UserVo> userVos = userService.selectUserDto();

        return userVos;
    }
}
