package com.flipfit.bean;

public class Role {
    private int roleId;
    private String roleName;
    private int roleDesc;       // 0 -> GymCustomer, 1-> GymOwner, 2-> GymAdmin

    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleDesc() {
        return this.roleDesc;
    }

    public void setRoleDesc(int roleDesc) {
        this.roleDesc = roleDesc;
    }
    
}