package com.sweetshop.dto;

public class AddressResponse {
    private Long id;
    private String fullName;
    private String address;

    public AddressResponse(Long id, String fullName, String address) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }
}
