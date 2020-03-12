package com.shishuheng.framework.authentication.domain.user;

import com.shishuheng.framework.common.module.domain.base.BaseRepository;
import com.shishuheng.framework.common.module.domain.user.User;

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
