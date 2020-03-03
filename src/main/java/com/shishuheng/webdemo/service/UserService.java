package com.shishuheng.webdemo.service;

import com.shishuheng.webdemo.domain.base.Result;
import com.shishuheng.webdemo.domain.permission.Permission;
import com.shishuheng.webdemo.domain.role.Role;
import com.shishuheng.webdemo.domain.role.RoleRepository;
import com.shishuheng.webdemo.domain.status.Status;
import com.shishuheng.webdemo.domain.status.StatusRepository;
import com.shishuheng.webdemo.domain.user.User;
import com.shishuheng.webdemo.domain.user.UserDto;
import com.shishuheng.webdemo.domain.user.UserRepository;
import com.shishuheng.webdemo.service.base.BaseService;
import com.shishuheng.webdemo.utils.CommonUtil;
import com.shishuheng.webdemo.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
@DependsOn(value = "roleService")
public class UserService extends BaseService<User> implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

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
    public Result<List<User>> findUserList(UserDto dto) {
        if (null == dto) {
            dto = new UserDto();
        }
        List<User> list = repository.findAll(buildSpecification(dto));
        return new Result<>(list);
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
            Status enabled = statusRepository.findStatusByCodeAndEffectEntity("enabled", getManagedEntity());
            if (null == enabled) {
                log.info("获取启用状态出错");
                return;
            }
            Set<Role> roles = new HashSet<>(roleRepository.findAll());
            User user = new User();
            user.setUsername("admin");
            user.setPassword(MD5Util.encode("123456"));
            user.setStatus(enabled);
            user.setCreatedDate(new Date());
            user.setRoles(roles);
            repository.save(user);
            log.info("***** 初始化管理员 admin *****");
        }
    }
}
