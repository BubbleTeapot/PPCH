package com.lx.login.demo.auth;

import com.lx.login.demo.dao.MyClientDetailDao;
import com.lx.login.demo.entity.MyClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/24 16:37
 */
@Component
public class MyClientDetailsService implements org.springframework.security.oauth2.provider.ClientDetailsService {
    @Resource
    MyClientDetailDao myClientDetailDao;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        MyClientDetails clientDetails = myClientDetailDao.selectByClientId(clientId);
        clientDetails.setSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(clientDetails.getClientSecret()));
        return clientDetails;
    }
}
