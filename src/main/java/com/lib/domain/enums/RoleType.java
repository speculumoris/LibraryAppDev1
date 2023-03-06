package com.lib.domain.enums;

public enum RoleType {

    MEMBER_TYPE("Member"),
    EMPLOYEE_TYPE("Employee"),
    ADMIN_TYPE("Administrator");

    private String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
