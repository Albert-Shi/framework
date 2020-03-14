package com.shishuheng.framework.authentication.service;

import com.alibaba.fastjson.JSON;
import com.shishuheng.framework.authentication.domain.client.Client;
import com.shishuheng.framework.authentication.domain.client.ClientRepository;
import com.shishuheng.framework.authentication.service.base.BaseAuthenticationService;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.status.Status;
import com.shishuheng.framework.common.module.service.CommonException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/3/6 3:58 下午
 */
@Data
@Slf4j
@Service
public class ClientService extends BaseAuthenticationService<Client> implements ClientDetailsService {
    @Autowired
    private ClientRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Set<Permission> initPermission() {
        return null;
    }

    @Override
    public void initStatus() {
        log.info("证明 ClientService 执行过 initStatus");
    }

    @Override
    public void initEntity() {
        if (repository.count() > 0) {
            return;
        }
        List<String> authorities = Arrays.asList("read", "update", "add", "delete");
        List<String> grantTypes = Arrays.asList("password", "client_credentials", "authorization_code", "refresh_token");
        Status enable = getStatus(getManagedEntity().getClassName() + "Enabled");


        // 授权
        Client authentication = new Client();
        authentication.setClientId("client-authentication");
        authentication.setClientSecret(passwordEncoder.encode("abcdef"));
        authentication.setAccessTokenExpire(500);
        authentication.setRefreshTokenExpire(700);
        authentication.setAuthoritiesJson(JSON.toJSONString(authorities));
        authentication.setGrantTypeJson(JSON.toJSONString(grantTypes));
        authentication.setDescription("授权服务客户端");
        authentication.setStatus(enable);
        authentication.setCreatedDate(new Date());
        repository.save(authentication);
        // 音乐
        Client music = new Client();
        music.setClientId("client-music");
        music.setClientSecret(passwordEncoder.encode("abcdef"));
        music.setAccessTokenExpire(500);
        music.setRefreshTokenExpire(700);
        music.setAuthoritiesJson(JSON.toJSONString(authorities));
        music.setGrantTypeJson(JSON.toJSONString(grantTypes));
        music.setDescription("音乐服务客户端");
        music.setStatus(enable);
        music.setCreatedDate(new Date());
        repository.save(music);
        log.info("证明 ClientService 执行过 initEntity");
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = repository.findClientByClientId(clientId);
        if (client == null) {
            log.info("未能找到客户端:" + clientId);
            new CommonException("未能通过clientId找到对应的客户端");
        }
        if (!client.getStatus().getCode().equals(getManagedEntity().getClassName() + "Enabled")) {
            log.info("客户端:" + clientId + "非启用状态");
            new CommonException("客户端: " + clientId + " 已被禁用");
        }
        return client;
    }
}
