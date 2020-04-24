package com.lx.login.demo.dao;

import com.lx.login.demo.entity.UserAuth;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/23 16:44
 */
public interface UserAuthDao {
    @Select("select request_url as url,authority from request_authorities")
    List<UserAuth> listUserAuth();
}
