package com.shishuheng.framework.authentication.service;

import com.shishuheng.framework.authentication.domain.base.Result;
import com.shishuheng.framework.authentication.domain.entity.ManagedEntity;
import com.shishuheng.framework.authentication.domain.entity.ManagedEntityRepository;
import com.shishuheng.framework.authentication.domain.permission.Permission;
import com.shishuheng.framework.authentication.domain.permission.PermissionDto;
import com.shishuheng.framework.authentication.domain.status.StatusRepository;
import com.shishuheng.framework.authentication.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author shishuheng
 * @date 2020/2/27 2:09 下午
 */
@Slf4j
@Service
//@DependsOn(value = "statusService")
public class ManagedEntityService extends BaseService<ManagedEntity> {
    @Autowired
    private ManagedEntityRepository repository;

    @Autowired
    private StatusRepository statusRepository;

    /**
     * 查询
     *
     * @return
     */
    public Result<List<ManagedEntity>> findManagedEntityList() {
        List<ManagedEntity> list = repository.findAll();
        return new Result<>(list);
    }

    /**
     * 创建查询条件
     *
     * @param dto
     * @return
     */
    private Specification<Permission> buildSpecification(PermissionDto dto) {
        Specification<Permission> specification = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (null != dto.getId()) {
                    Predicate idEquals = criteriaBuilder.equal(root.get("id"), dto.getId());
                    list.add(idEquals);
                }
                if (StringUtils.isNotBlank(dto.getPermission())) {
                    Predicate permissionEquals = criteriaBuilder.like(root.get("permission").as(String.class), "%" + dto.getPermission() + "%");
                    list.add(permissionEquals);
                }
                if (StringUtils.isNotBlank(dto.getLabel())) {
                    Predicate labelEquals = criteriaBuilder.equal(root.get("label"), dto.getLabel());
                    list.add(labelEquals);
                }
                if (list.size() > 0) {
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
                return criteriaBuilder.conjunction();
            }
        };
        return specification;
    }

    @Override
    public Set<Permission> initPermission() {
        return null;
    }

    @Override
    public void initStatus() {

    }

    @Override
    public void initEntity() {
    }
}
