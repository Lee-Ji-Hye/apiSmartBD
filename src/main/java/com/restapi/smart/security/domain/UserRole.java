package com.restapi.smart.security.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum UserRole {

    USER("[ROLE_USER]"), ADMIN("[ROLE_ADMIN]"), DEFAULT("[ROLE_DEFAULT]");
    //,SYSMASTER("[ROLE_SYSMASTER]"), FOOD("[ROLE_BD_FOOD]"), OFFICE("[ROLE_BD_OFFICE]")

    private String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCorrectName(String name) {
        return name.equalsIgnoreCase(this.roleName);
    }

    public static UserRole getRoleByName(String roleName) {
        //return Arrays.stream(UserRole.values()).filter(r -> r.isCorrectName(roleName)).findFirst().orElseThrow(() -> {new NoSuchElementException("검색된 권한이 없습니다.")});
        return Arrays.stream(UserRole.values()).filter(r -> r.isCorrectName(roleName)).findFirst().orElse(USER);
    }

}