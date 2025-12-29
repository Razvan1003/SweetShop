package com.sweetshop.service;

import com.sweetshop.dto.AddressRequest;
import com.sweetshop.dto.AddressResponse;
import com.sweetshop.entity.Address;
import com.sweetshop.entity.User;
import com.sweetshop.exception.ResourceNotFoundException;
import com.sweetshop.repository.AddressRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressResponse addAddress(User user, AddressRequest request) {
        Address address = new Address(request.getFullName(), request.getAddress(), user);
        Address saved = addressRepository.save(address);
        return new AddressResponse(saved.getId(), saved.getFullName(), saved.getAddress());
    }

    public List<AddressResponse> getAddresses(User user) {
        return addressRepository.findByUserId(user.getId()).stream()
            .map(address -> new AddressResponse(address.getId(), address.getFullName(), address.getAddress()))
            .collect(Collectors.toList());
    }

    public Address getAddressForUser(User user, Long addressId) {
        return addressRepository.findByIdAndUserId(addressId, user.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }
}
