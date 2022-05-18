package ru.tsu.hits.webjavabackendhomework1.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.BASE_USER_PERMISSION)),
    ADMIN(Set.of(Permission.BASE_USER_PERMISSION, Permission.BASE_ADMIN_PERMISSION)),
    NOBODY(new HashSet<>());

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
