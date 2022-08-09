package com.coding.service;

import com.coding.domain.*;
import com.coding.mapper.*;
import com.coding.pojo.param.*;
import com.coding.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public PageResult<UserDO> userPage(SearchRequestPageParam request) {
        WeekendSqls<UserDO> sql = WeekendSqls.custom();
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sql.andLike(UserDO::getRemark, "%" + request.getKeyword() + "%");
        }
        PageHelper.startPage(request.getPage(), request.getSize(), "id desc");
        List<UserDO> list = userMapper.selectByExample(Example.builder(UserDO.class).where(sql).build());
        PageInfo<UserDO> pageInfo = new PageInfo<>(list);
        return PageResult.success(pageInfo.getTotal(), pageInfo.getList());
    }


    public R<String> adminLogin(AdminLoginParam param) {
        if (StringUtils.isBlank(param.getUsername())) {
            return R.createByErrorMessage("Login Fail");
        }
        UserDO user = new UserDO();
        user.setUsername(param.getUsername());
        UserDO record = userMapper.selectOne(user);
        if (record == null) {
            return R.createByErrorMessage("Login Fail");
        }
        boolean matches = passwordEncoder.matches(param.getPassword(), record.getPassword());
        if (!matches) {
            return R.createByErrorMessage("Login Fail");
        }
        String token = JwtTokenUtils.createToken(String.valueOf(record.getId()), "admin");
        return R.createBySuccess(token);
    }


    public R<List<UserDO>> searchList(String keyword, Integer limit) {
        WeekendSqls<UserDO> whereSql = WeekendSqls.custom();
        Example.Builder where = Example.builder(UserDO.class).where(whereSql);
        if (StringUtils.isNotBlank(keyword)) {
            WeekendSqls<UserDO> searchSql = WeekendSqls.custom();
            searchSql.orLike(UserDO::getUsername, "%" + keyword + "%");
            where.where(searchSql);
        }

        Example example = where.build();
        PageHelper.startPage(1, limit, "id desc");
        List<UserDO> list = userMapper.selectByExample(example);
        return R.createBySuccess(list);
    }


    public R<String> saveUser(UserDO param) {
        int resultCount = 0;
        if (param.getId() == null) {
            if (userExist(param.getUsername())) {
                return R.createByErrorMessage("user already exist");
            }
            param.setPassword(passwordEncoder.encode(param.getPassword()));
            resultCount = userMapper.insertSelective(param);
        } else {
            if (StringUtils.isNotBlank(param.getPassword())) {
                param.setPassword(passwordEncoder.encode(param.getPassword()));
            }
            resultCount = userMapper.updateByPrimaryKeySelective(param);
        }
        return R.createBySimple(resultCount > 0, "Save Successfully", "Save Failure");
    }

    private boolean userExist(String username) {
        if (StringUtils.isBlank(username)) {
            return true;
        }
        UserDO record = new UserDO();
        record.setUsername(username);
        return userMapper.selectCount(record) > 0;
    }

    public R<String> deleteUser(Long userId, Long id) {
        userMapper.deleteByPrimaryKey(id);
        return R.createBySuccess();
    }
}
