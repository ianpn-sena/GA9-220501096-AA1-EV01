package com.jmteamconsulting.sgps.controller;

import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) usado por cuerpos de requests de UserController.
 * Define la estructura y validaciones del RequesstBody de algunos requests de UserController.
 */
public class UserControllerDTO {
    @NotNull
    public String names;

    @NotNull
    public String surname;

    @NotNull
    public Long document_type_id;

    @NotNull
    public String document_number;

    @NotNull
    public String email;

    @NotNull
    public String password;

    @NotNull
    public Long country_id;

    @NotNull
    public Long department_id;

    @NotNull
    public Long city_id;

    @NotNull
    public String phone_number;

    @NotNull
    public String address_1;

    public String address_2;
    public Integer zip_code;
}