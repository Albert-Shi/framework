package com.shishuheng.webdemo.service;

import com.shishuheng.webdemo.domain.base.Result;
import com.shishuheng.webdemo.domain.permission.Permission;
import com.shishuheng.webdemo.domain.permission.PermissionDto;
import com.shishuheng.webdemo.domain.permission.PermissionRepository;
import com.shishuheng.webdemo.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shishuheng
 * @date 2020/2/27 2:09 下午
 */
@Slf4j
@Service
public class PermissionService {
    @Autowired
    private PermissionRepository repository;

    @PostConstruct
    public void init() {
        if (repository.count() < 1) {
            Permission all = new Permission("全部", "/**", "全部访问权限");
            Permission login = new Permission("登录", "/login", "访问登录页面");
            Permission logout = new Permission("退出", "/logout", "访问退出页面");
            repository.saveAll(Arrays.asList(all, login, logout));
            log.info("***** 初始化权限成功 *****");
        }
    }

    /**
     * 新增权限
     *
     * @param dto
     * @return
     */
    public Result<String> addRole(PermissionDto dto) {
        Permission permission = repository.findPermissionByLabel(dto.getLabel());
        if (null != permission) {
            return new Result<>("权限标签已存在");
        } else {
            Permission addOne = new Permission();
            CommonUtil.copyObject(dto, addOne, true);
            repository.save(addOne);
            return new Result<>();
        }
    }

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    public Result<List<Permission>> findPermissionList(PermissionDto dto) {
        if (null == dto) {
            dto = new PermissionDto();
        }
        List<Permission> list = repository.findAll(buildSpecification(dto));
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
    public Result<String> updatePermission(PermissionDto dto) {
        Permission updateOne = repository.selectById(dto.getId());
        CommonUtil.copyObject(dto, updateOne, false);
        repository.save(updateOne);
        return new Result();
    }
}
