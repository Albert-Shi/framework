package com.shishuheng.framework.common.module.domain.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author shishuheng
 * @date 2020/2/26 9:49 上午
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    /**
     * 根据id查询实体对象
     *
     * @param id
     * @return
     */
    @Query(value = "select e from #{#entityName} as e where e.id = ?1")
    T selectById(Long id);
}
