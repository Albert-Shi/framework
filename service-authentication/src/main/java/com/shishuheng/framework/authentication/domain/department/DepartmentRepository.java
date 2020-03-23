package com.shishuheng.framework.authentication.domain.department;

import com.shishuheng.framework.common.module.domain.base.BaseRepository;
import com.shishuheng.framework.common.module.domain.department.Department;

/**
 * @author shishuheng
 * @date 2020/3/23 4:02 下午
 */
public interface DepartmentRepository extends BaseRepository<Department> {
    /**
     * 根据部门代码查询
     *
     * @param code
     * @return
     */
    Department findDepartmentByCode(String code);
}
