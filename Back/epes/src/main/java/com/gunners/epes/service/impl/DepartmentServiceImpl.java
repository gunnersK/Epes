package com.gunners.epes.service.impl;

import com.gunners.epes.entity.Department;
import com.gunners.epes.mapper.DepartmentMapper;
import com.gunners.epes.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
