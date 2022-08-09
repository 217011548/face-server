package com.coding.service;

import com.coding.FoodApp;
import com.coding.domain.UserDO;
import com.coding.pojo.param.AdminLoginParam;
import com.coding.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@SpringBootTest(classes = {FoodApp.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void adminLogin() {
        AdminLoginParam param = new AdminLoginParam();
        param.setUsername("admin");
        param.setPassword("123456");
        R<String> result = userService.adminLogin(param);
        log.info("result:{}", result);
    }

    @Test
    void saveUser() {
        UserDO param = new UserDO();
        param.setUsername("admin");
        param.setPassword("123456");
        R<String> result = userService.saveUser(param);
        log.info("result:{}", result);
    }
}
