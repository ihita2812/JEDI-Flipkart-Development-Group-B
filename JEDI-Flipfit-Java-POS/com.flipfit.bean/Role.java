package com.flipfit.bean;


/*
 *@author: Zaid, Ritesh
 *@ClassName: Role
 *@Exceptions: No
 *@version: 1.0
 *@See :GymUser, GymAdmin, GymOwner, GymCustomer
 */

public class Role {
    private int roleId;
    private String roleName;
    private String roleDesc;       // 0 -> GymCustomer, 1-> GymOwner, 2-> GymAdmin

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

    public String getRoleDesc() {
        return this.roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
    
}