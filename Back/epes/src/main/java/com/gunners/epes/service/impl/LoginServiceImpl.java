package com.gunners.epes.service.impl;

import com.gunners.epes.entity.Login;
import com.gunners.epes.mapper.LoginMapper;
import com.gunners.epes.service.ILoginService;
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
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

}
