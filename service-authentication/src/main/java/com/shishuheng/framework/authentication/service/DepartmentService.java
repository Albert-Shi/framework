package com.shishuheng.framework.authentication.service;

import com.shishuheng.framework.authentication.domain.department.DepartmentRepository;
import com.shishuheng.framework.authentication.domain.permission.PermissionRepository;
import com.shishuheng.framework.authentication.service.base.BaseAuthenticationService;
import com.shishuheng.framework.common.module.domain.department.Department;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/3/23 4:04 下午
 */
@Service
@DependsOn(value = "permissionService")
public class DepartmentService extends BaseAuthenticationService<Department> {
    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Set<Permission> initPermission() {
        return null;
    }

    @Override
    public void initStatus() {

    }

    @Override
    public void initEntity() {
        List<Permission> permissions = permissionRepository.findAll();
        Set<Permission> permissionSet = new LinkedHashSet<>(permissions);
        Department root = new Department("根部门", "ROOT_DEPART",
                0, permissionSet, null);
        root.setStatus(getStatus("DepartmentEnabled"));
        repository.save(root);
    }
}
