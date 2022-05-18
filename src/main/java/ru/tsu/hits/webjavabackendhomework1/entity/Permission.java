package ru.tsu.hits.webjavabackendhomework1.entity;

public enum Permission {
    BASE_USER_PERMISSION("baseUserPrm"),
    BASE_ADMIN_PERMISSION("baseAdminPrm");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
