package com.lx.login.demo.dao;

import com.lx.login.demo.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * @author longxin
 * @description: 角色dao
 * @date 2020/4/23 16:13
 */
public interface RoleDao {

    /**
     * 通过用户名查询角色
     *
     * @param userName 用户名
     * @return
     */
    @Select("select authority from role where user_name = #{userName}")
    Set<String> listRoleByUserName(@Param("userName") String userName);

}
