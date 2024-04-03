package com.testattus.taskmanager.service;

import com.testattus.taskmanager.Address;
import com.testattus.taskmanager.People;
import com.testattus.taskmanager.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    @Transactional
    public Address saveAddress(Address address) {
        // Verificar se todos os campos obrigatórios estão preenchidos
        if (address.getPublicPlace().isEmpty() || address.getCep().isEmpty() || address.getNumber().isEmpty()
                || address.getCity().isEmpty() || address.getState().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos do endereço devem ser preenchidos");
        }

        // Se o endereço não for marcado como principal e a pessoa já tiver um endereço principal,
        // desmarcar o endereço principal anterior
        if (!address.isPrincipal()) {
            unmarkMainAddress(address.getPeople());
        }

        // Salvar o endereço
        return addressRepository.save(address);
    }

    @Transactional
    public Address editAddress(Long id, Address editedAddress) {
        // Verificar se o endereço existe
        Address existAddress = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado"));

        // Atualizar os campos do endereço existente com os valores do endereço editado
        existAddress.setPublicPlace(editedAddress.getPublicPlace());
        existAddress.setCep(editedAddress.getCep());
        existAddress.setNumber(editedAddress.getNumber());
        existAddress.setCity(editedAddress.getCity());
        existAddress.setState(editedAddress.getState());
        existAddress.setPrincipal(editedAddress.isPrincipal());

        // Salvar e retornar o endereço atualizado
        return addressRepository.save(existAddress);
    }

    @Transactional(readOnly = true)
    public List<Address> listAllAddress() {
        return addressRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Address> listAddressByPeople(Long peopleId) {
        return addressRepository.findByPeopleId(peopleId);
    }

    private void unmarkMainAddress(People people) {
        for (Address address : people.getAddresses()) {
            if (address.isPrincipal()) {
                address.setPrincipal(false);
                addressRepository.save(address);
                break;
            }
        }
    }
}
