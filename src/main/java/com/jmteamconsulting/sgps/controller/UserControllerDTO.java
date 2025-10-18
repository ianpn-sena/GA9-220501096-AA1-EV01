package com.jmteamconsulting.sgps.controller;

import jakarta.validation.constraints.NotNull;

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