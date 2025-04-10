package com.ce.template.model.enums;


public enum UserRoleEnum {

    ADMIN("admin"),
    SOLICITANTE("solicitante"),
    FISCAL("fiscal");

    private String role;

   UserRoleEnum(String role){
       this.role = role;
   }

   public String getRole(){
       return this.role;
   }
}
