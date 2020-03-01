package com.shishuheng.webdemo.service;

import com.shishuheng.webdemo.domain.base.Result;
import com.shishuheng.webdemo.domain.permission.Permission;
import com.shishuheng.webdemo.domain.permission.PermissionRepository;
import com.shishuheng.webdemo.domain.role.Role;
import com.shishuheng.webdemo.domain.role.RoleDto;
import com.shishuheng.webdemo.domain.role.RoleRepository;
import com.shishuheng.webdemo.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author shishuheng
 * @date 2020/2/27 2:05 下午
 */
@Slf4j
@Service
@DependsOn(value = "permissionService")
public class RoleService {
    @Autowired
    private RoleRepository repository;

    @Autowired
    private PermissionRepository permissionRepository;

    @PostConstruct
    public void init() {
        if (repository.count() < 1) {
            List<Permission> permissionList = permissionRepository.findAll();
            Set<Permission> permissions = new HashSet<>(permissionList);

            Role role = new Role();
            role.setLabel("管理员");
            role.setStatus(1);
            role.setPermissions(permissions);
            role.setCreatedDate(new Date());
            repository.save(role);
            log.info("***** 初始化角色成功 *****");
        }
    }

    /**
     * 新增角色
     *
     * @param dto
     * @return
     */
    public Result<String> addRole(RoleDto dto) {
        Role role = repository.findRoleByLabel(dto.getLabel());
        if (null != role) {
            return new Result<>("角色标签已存在");
        } else {
            Role addOne = new Role();
            addOne.setLabel(dto.getLabel());
            if (null != dto.getPermissionIds()) {
                List<Permission> permissions = permissionRepository.findAllById(dto.getPermissionIds());
                addOne.setPermissions(new HashSet<>(permissions));
            }
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
    public Result<List<Role>> findRoleList(RoleDto dto) {
        if (null == dto) {
            dto = new RoleDto();
        }
        List<Role> list = repository.findAll(buildSpecification(dto));
        return new Result<>(list);
    }

    /**
     * 创建查询条件
     *
     * @param dto
     * @return
     */
    private Specification<Role> buildSpecification(RoleDto dto) {
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
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
     * 角色更新
     *
     * @param roleDto
     * @return
     */
    public Result<String> updateRole(RoleDto roleDto) {
        Role updateRole = repository.selectById(roleDto.getId());
        CommonUtil.copyObject(roleDto, updateRole, false);
        repository.save(updateRole);
        return new Result();
    }
}
