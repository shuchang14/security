package com.shu.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authorityService")
@Transactional(readOnly = true)
public class AuthorityServiceImpl implements AuthorityService {
}
