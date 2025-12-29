package com.sweetshop.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    private String address;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
