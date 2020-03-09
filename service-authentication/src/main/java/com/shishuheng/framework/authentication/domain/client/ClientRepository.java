package com.shishuheng.framework.authentication.domain.client;

import com.shishuheng.framework.authentication.domain.base.BaseRepository;

/**
 * @author shishuheng
 * @date 2020/3/6 4:00 下午
 */
public interface ClientRepository extends BaseRepository<Client> {
    /**
     * 根据clientId查询
     *
     * @param clientId
     * @return
     */
    Client findClientByClientId(String clientId);
}
