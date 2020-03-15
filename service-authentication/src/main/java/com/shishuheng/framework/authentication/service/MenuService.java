package com.shishuheng.framework.authentication.service;

import com.shishuheng.framework.authentication.domain.menu.MenuRepository;
import com.shishuheng.framework.authentication.domain.role.RoleRepository;
import com.shishuheng.framework.authentication.service.base.BaseAuthenticationService;
import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.menu.Menu;
import com.shishuheng.framework.common.module.domain.menu.MenuDto;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.role.Role;
import com.shishuheng.framework.common.module.domain.status.Status;
import com.shishuheng.framework.common.module.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020年 03月 15日 14:35:18
 */
@Service
@DependsOn(value = "roleService")
public class MenuService extends BaseAuthenticationService<Menu> {
    @Autowired
    private MenuRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Set<Permission> initPermission() {
        return null;
    }

    @Override
    public void initStatus() {

    }

    @Override
    public void initEntity() {
        Status enable = getStatus("MenuEnabled");
        List<Menu> menus = new ArrayList<>();
        Menu system = repository.save(new Menu("系统配置", 0, enable));
        menus.add(system);
        Menu user = new Menu("用户配置", "/user", 1, system, enable);
        menus.add(user);
        Menu role = new Menu("角色配置", "/role", 2, system, enable);
        menus.add(role);
        Menu permission = new Menu("权限配置", "/permission", 3, system, enable);
        menus.add(permission);
        Menu status = new Menu("状态配置", "/status", 4, system, enable);
        menus.add(status);
        Menu menu = new Menu("菜单配置", "/menu", 5, system, enable);
        menus.add(menu);
        repository.saveAll(menus);
        Role admin = roleRepository.findRoleByCode("ADMIN");
        admin.setMenus(new LinkedHashSet<>(menus));
        roleRepository.save(admin);
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    public Result<String> addMenu(MenuDto dto) {
        Menu menu = new Menu();
        CommonUtil.copyObject(dto, menu, true);
        menu = repository.save(menu);
        List<Role> roles = roleRepository.findRolesByIdIn(dto.getRoles());
        for (Role role : roles) {
            Set<Menu> menus = role.getMenus();
            menus.add(menu);
            role.setMenus(menus);
        }
        roleRepository.saveAll(roles);
        return new Result<>();
    }

    /**
     * 更新
     *
     * @param dto
     * @return
     */
    public Result<String> updateMenu(MenuDto dto) {
        Menu menu = new Menu();
        CommonUtil.copyObject(dto, menu, false);
        repository.save(menu);
        return new Result<>();
    }

    /**
     * 条件查询
     *
     * @param dto
     * @return
     */
    public Result<List<Menu>> findByConditions(MenuDto dto) {
        if (null == dto) {
            dto = new MenuDto();
        }
        List<Menu> menus = repository.findAll(buildSpecification(dto));
        return new Result<>(menus);
    }

    /**
     * 创建查询条件
     *
     * @param dto
     * @return
     */
    private Specification<Menu> buildSpecification(MenuDto dto) {
        Specification<Menu> specification = new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(dto.getLabel())) {
                    Predicate labelEquals = criteriaBuilder.equal(root.get("label"), dto.getLabel());
                    list.add(labelEquals);
                }
                if (null != dto.getParentId()) {
                    Predicate parentIdEquals = criteriaBuilder.equal(root.get("parentId"), dto.getParentId());
                    list.add(parentIdEquals);
                }
                if (null != dto.getLevel()) {
                    Predicate levelEquals = criteriaBuilder.equal(root.get("level"), dto.getLevel());
                    list.add(levelEquals);
                }
                if (null != dto.getStatusCode()) {
                    Predicate statusCodeEquals = criteriaBuilder.equal(root.get("status").get("code"), dto.getStatusCode());
                    list.add(statusCodeEquals);
                }
                if (list.size() > 0) {
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
                return criteriaBuilder.conjunction();
            }
        };
        return specification;
    }
}
