package com.lx.login.demo.dao;

import com.lx.login.demo.entity.MyClientDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/24 16:49
 */
public interface MyClientDetailDao {

    /**
     * 通过客户端id查询客户端
     *
     * @param clientId
     * @return
     */
    @Select(" select client_Id as clinetId, resource_Ids as resourceIds, authorized_Grant_Types as authorizedGrantTypes" +
            " ,scopes as scope, authorities as authorities, secret as secret" +
            " from client where client_Id = #{clientId}")
    MyClientDetails selectByClientId(@Param("clientId") String clientId);

    @Select(" select client_Id as clinetId, resource_Ids as resourceIds, authorized_Grant_Types as authorizedGrantTypes" +
            " ,scopes as scope, authorities as authorities, secret as secret" +
            " from client")
    List<MyClientDetails> listClient();
}
