package com.shishuheng.framework.authentication.service;

import com.shishuheng.framework.authentication.domain.department.DepartmentRepository;
import com.shishuheng.framework.authentication.domain.role.RoleRepository;
import com.shishuheng.framework.authentication.domain.status.StatusRepository;
import com.shishuheng.framework.authentication.domain.user.UserRepository;
import com.shishuheng.framework.authentication.service.base.BaseAuthenticationService;
import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.department.Department;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.role.Role;
import com.shishuheng.framework.common.module.domain.status.Status;
import com.shishuheng.framework.common.module.domain.user.User;
import com.shishuheng.framework.common.module.domain.user.UserDto;
import com.shishuheng.framework.common.module.utils.CommonUtil;
import com.shishuheng.framework.common.module.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author shishuheng
 * @date 2020/2/27 10:11 上午
 */
@Slf4j
@Service
@DependsOn(value = {"roleService", "departmentService"})
public class UserService extends BaseAuthenticationService<User> implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserByUsername(username);
    }

    /**
     * 新增权限
     *
     * @param dto
     * @return
     */
    public Result<String> addUser(UserDto dto) {
        User user = repository.findUserByUsername(dto.getUsername());
        if (null != user) {
            return new Result<>("用户名已存在");
        } else {
            User addOne = new User();
            addOne.setPassword(MD5Util.encode(dto.getPassword()));
            List<Role> roles = roleRepository.findAllById(dto.getRoleIds());
            Set<Role> roleSet = new HashSet<>(roles);
            addOne.setRoles(roleSet);
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
    public Result<List<UserDto>> findUserList(UserDto dto) {
        if (null == dto) {
            dto = new UserDto();
        }
        List<User> list = repository.findAll(buildSpecification(dto));
        List<UserDto> dtos = trans(list);
        return new Result<>(dtos);
    }

    /**
     * 数据转换
     *
     * @param users
     * @return
     */
    private List<UserDto> trans(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        if (null == users || users.size() < 1) {
            return userDtos;
        }
        for (User u : users) {
            UserDto dto = new UserDto();
            CommonUtil.copyObject(u, dto, true, "status", "password");
            dto.setStatus(u.getStatus().getId());
            dto.setStatusCode(u.getStatus().getCode());
            dto.setStatusLabel(u.getStatus().getLabel());
            userDtos.add(dto);
        }
        return userDtos;
    }

    /**
     * 创建查询条件
     *
     * @param dto
     * @return
     */
    private Specification<User> buildSpecification(UserDto dto) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (null != dto.getId()) {
                    Predicate idEquals = criteriaBuilder.equal(root.get("id"), dto.getId());
                    list.add(idEquals);
                }
                if (StringUtils.isNotBlank(dto.getUsername())) {
                    Predicate labelEquals = criteriaBuilder.equal(root.get("label"), dto.getUsername());
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
    public Result<String> updateRole(UserDto dto) {
        User updateOne = repository.selectById(dto.getId());
        CommonUtil.copyObject(dto, updateOne, false);
        repository.save(updateOne);
        return new Result();
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
        if (repository.count() < 1) {
            Status enabled = statusRepository.findStatusByCodeAndEffectEntity(getManagedEntity().getClassName() + "Enabled", getManagedEntity());
            if (null == enabled) {
                log.info("获取启用状态出错");
                return;
            }
            Set<Role> roles = new HashSet<>(roleRepository.findAll());
            Set<Department> departments = new HashSet<>();
            departments.add(departmentRepository.findDepartmentByCode("ROOT_DEPART"));

            User user = new User();
            user.setLabel("管理员");
            user.setUsername("admin");
            user.setPassword(MD5Util.encode("123456"));
            user.setStatus(enabled);
            user.setCreatedDate(new Date());
            user.setRoles(roles);
            user.setDepartments(departments);
            repository.save(user);
            log.info("***** 初始化管理员 admin *****");
        }
    }
}
