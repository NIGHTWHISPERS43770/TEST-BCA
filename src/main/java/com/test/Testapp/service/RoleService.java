package com.test.Testapp.service;

import com.test.Testapp.constant.Eroles;
import com.test.Testapp.entity.Role;

public interface RoleService {
    Role getOrSave(Eroles roles);
}
