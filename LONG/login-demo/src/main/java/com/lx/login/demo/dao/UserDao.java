package com.lx.login.demo.dao;

import com.lx.login.demo.entity.SelfUserDetails;
import org.apache.ibatis.annotations.*;

import java.util.Set;

/**
 * @author longxin
 * @description: 用户dao
 * @date 2020/4/23 15:28
 */
public interface UserDao {
    @Results(id = "userResult", value = {
            @Result(property = "userName", column = "user_name", id = true),
            @Result(property = "passWord", column = "pass_word"),
            @Result(property = "userType", column = "user_type")
//            , @Result(property = "authorities", javaType = Set.class, column = "user_name", many = @Many(select = "listRoleByUserName"))
    })
    @Select("select * from user where user_name = #{userName}")
    SelfUserDetails getUser(@Param("userName") String username);
}
