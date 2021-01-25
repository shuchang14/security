package com.shu.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
}
