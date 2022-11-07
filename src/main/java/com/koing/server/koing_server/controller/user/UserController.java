package com.koing.server.koing_server.controller.user;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.common.success.SuccessStatusCode;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "User")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("User - 유저 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - 유저 정보 가져오기 성공"),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰입니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/users")
    public SuccessResponse<List<User>> getUser() {

        List<User> users = userService.getUsers();
        return SuccessResponse.success(SuccessCode.GET_USERS_SUCCESS, users);
    }


}
