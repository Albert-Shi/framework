package com.shishuheng.framework.authentication.service;

import com.alibaba.fastjson.JSON;
import com.shishuheng.framework.authentication.domain.client.Client;
import com.shishuheng.framework.authentication.domain.client.ClientRepository;
import com.shishuheng.framework.authentication.domain.permission.Permission;
import com.shishuheng.framework.authentication.domain.status.Status;
import com.shishuheng.framework.authentication.service.base.BaseService;
import com.shishuheng.framework.authentication.service.base.CommonException;
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
public class ClientService extends BaseService<Client> implements ClientDetailsService {
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
        Client test = new Client();
        test.setClientId("test-client");
        test.setClientSecret(passwordEncoder.encode("test-password"));
        test.setAccessTokenExpire(500);
        test.setRefreshTokenExpire(700);
        List<String> authorities = Arrays.asList("read", "update", "add", "delete");
        test.setAuthoritiesJson(JSON.toJSONString(authorities));
        List<String> grantTypes = Arrays.asList("password", "client_credentials", "authorization_code", "refresh_token");
        test.setGrantTypeJson(JSON.toJSONString(grantTypes));
        test.setDescription("这是初始化用来测试的");
        Status enable = getStatus(getManagedEntity().getClassName() + "Enabled");
        test.setStatus(enable);
        test.setCreatedDate(new Date());
        repository.save(test);
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
