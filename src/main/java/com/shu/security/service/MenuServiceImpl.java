package com.shu.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("menuService")
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {
}
