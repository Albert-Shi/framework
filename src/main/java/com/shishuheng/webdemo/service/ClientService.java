package com.shishuheng.webdemo.service;

import com.shishuheng.webdemo.domain.client.Client;
import com.shishuheng.webdemo.domain.client.ClientRepository;
import com.shishuheng.webdemo.domain.permission.Permission;
import com.shishuheng.webdemo.domain.status.Status;
import com.shishuheng.webdemo.service.base.BaseService;
import com.shishuheng.webdemo.service.base.CommonException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

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
        log.info("证明 ClientService 执行过 initEntity");
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = repository.findClientByClientId(clientId);
        if (client == null) {
            new CommonException("未能通过clientId找到对应的客户端");
        }
        if (client.getStatus().getCode().equals(getManagedEntity().getClassName() + "Enabled")) {
            new CommonException("客户端: " + clientId + " 已被禁用");
        }
        return client;
    }
}
