package com.coding.controller.admin;

import com.coding.config.Const;
import com.coding.config.ReactiveRequestContextHolder;
import com.coding.domain.UserDO;
import com.coding.pojo.param.*;
import com.coding.service.UserService;
import com.coding.utils.PageResult;
import com.coding.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author guanweiming
 */
@Slf4j
@Api(tags = "user controller")
@RequestMapping(Const.ADMIN + "user")
@RequiredArgsConstructor
@RestController
public class AdminUserController {

    private final UserService userService;


    @ApiOperation("login")
    @PostMapping("login")
    public R<String> login(@RequestBody @Validated AdminLoginParam param) {
        return userService.adminLogin(param);
    }


    @ApiOperation("User Search")
    @GetMapping("page")
    public PageResult<UserDO> page(@Validated SearchRequestPageParam page) {
        return userService.userPage(page);
    }

    @ApiOperation("Search")
    @GetMapping("searchList")
    public Mono<R<List<UserDO>>> searchList(@Validated SearchParam searchParam) {
        return ReactiveRequestContextHolder.getUserId()
                .map(userId -> userService.searchList(searchParam.getKeyword(), searchParam.getLimit()));
    }


    @ApiOperation("Add user")
    @PostMapping("save")
    public R<String> saveUser(@RequestBody @Validated UserDO param) {
        return userService.saveUser(param);
    }

    @ApiOperation("delete user")
    @PostMapping("delete")
    public Mono<R<String>> deleteUser(@RequestBody @Validated DeleteParam param) {
        return ReactiveRequestContextHolder.getUserId().map(userId -> userService.deleteUser(userId, param.getId()));
    }


}
