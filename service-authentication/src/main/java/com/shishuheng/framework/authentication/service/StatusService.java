package com.shishuheng.framework.authentication.service;

import com.shishuheng.framework.authentication.domain.status.StatusRepository;
import com.shishuheng.framework.authentication.service.base.BaseAuthenticationService;
import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.permission.PermissionDto;
import com.shishuheng.framework.common.module.domain.status.Status;
import com.shishuheng.framework.common.module.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/2/27 2:09 下午
 */
@Slf4j
@Service
public class StatusService extends BaseAuthenticationService<Status> {
    @Autowired
    private StatusRepository repository;

    /**
     * 新增状态
     *
     * @param dto
     * @return
     */
    public Result<String> addStatus(Status dto) {
        Status status = repository.findStatusByCodeAndEffectEntity(dto.getCode(), getManagedEntity());
        if (null != status) {
            return new Result<>("状态已存在");
        } else {
            repository.save(dto);
            return new Result<>();
        }
    }

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    public Result<List<Status>> findStatusList(Status dto) {
        if (null == dto) {
            dto = new Status();
        }
        List<Status> list = repository.findAll();
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

    /**
     * 权限更新
     *
     * @param dto
     * @return
     */
    public Result<String> updateStatus(Status dto) {
        Status updateOne = repository.selectById(dto.getId());
        CommonUtil.copyObject(dto, updateOne, false);
        repository.save(updateOne);
        return new Result();
    }

    /**
     * 根据状态和实体类型查询
     *
     * @param code
     * @param managedEntityId
     * @return
     */
    public Status findStatusByCodeAndEffectEntityId(String code, Long managedEntityId) {
        return repository.findStatusByCodeAndEffectEntityId(code, managedEntityId);
    }

    /**
     * 根据类型实体id查询所有状态
     *
     * @param managedEntityId
     * @return
     */
    public List<Status> findStatusesByEffectEntityId(@RequestParam(value = "managedEntityId") Long managedEntityId) {
        return repository.findStatusesByEffectEntityId(managedEntityId);
    }

    /**
     * 批量保存
     *
     * @param statuses
     * @return
     */
    public Result<String> addAllStatus(List<Status> statuses) {
        repository.saveAll(statuses);
        return new Result<>();
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
