package com.shishuheng.framework.authentication.domain.user;

import com.shishuheng.framework.authentication.domain.base.BaseRepository;

/**
 * @author shishuheng
 * @date 2020/2/26 9:54 上午
 */
public interface UserRepository extends BaseRepository<User> {
    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
