package com.test.Testapp.service.impl;

import com.test.Testapp.constant.Eroles;
import com.test.Testapp.entity.Role;
import com.test.Testapp.repository.RoleRepository;
import com.test.Testapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role getOrSave(Eroles roles) {
        Optional<Role> optionalRole = roleRepository.findByRole(roles);
        if (optionalRole.isPresent()) return optionalRole.get();
        Role newRole =  Role.builder()
                .role(roles)
                .build();
        return roleRepository.saveAndFlush(newRole);
    }
}
