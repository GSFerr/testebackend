package com.testattus.taskmanager.service;

import com.testattus.taskmanager.Address;
import com.testattus.taskmanager.People;
import com.testattus.taskmanager.repository.AddressRepository;
import com.testattus.taskmanager.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public People savePeople(People people) {

        // Verificar se todos os campos estão preenchidos
        if (people.getName().isEmpty() || people.getDateOfBirth() == null || people.getAddresses().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
        }

        // Verificar se a pessoa já existe
        if (peopleRepository.existsByCpf(people.getCpf())) {
            throw new IllegalStateException("Pessoa já cadastrada");
        }

        // Salvar a Pessoa
        People savePeople = peopleRepository.save(people);

        // Salvar os endereços da pessoa
        for (Address address : people.getAddresses()) {
            if (address.getPublicPlace().isEmpty() || address.getCep().isEmpty() || address.getNumber().isEmpty()
                    || address.getCity().isEmpty() || address.getState().isEmpty()) {
                throw new IllegalArgumentException("Todos os campos de endereço devem ser preenchidos");
            }
            address.setPeople(savePeople); // Definindo a pessoa para o endereço
            addressRepository.save(address);
        }

        return savePeople;
    }

    @Transactional
    public People editPeople(Long id, People editedPeople) {
        // Verificar se a pessoa existe
        People existPeople = peopleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        // Verificar se todos os campos obrigatórios estão preenchidos
        if (editedPeople.getName().isEmpty() || editedPeople.getDateOfBirth().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos");
        }

        // Verificar se o CPF foi alterado
        if (!existPeople.getCpf().equals(editedPeople.getCpf())) {
            throw new IllegalArgumentException("CPF não pode ser alterado");
        }

        // Atualizar os demais campos da pessoa
        existPeople.setName(editedPeople.getName());
        existPeople.setDateOfBirth(editedPeople.getDateOfBirth());

        // Atualizar endereços da pessoa
        List<Address> updatedAddresses = new ArrayList<>();
        for (Address editedAddress : editedPeople.getAddresses()) {
            if (editedAddress.getPublicPlace().isEmpty() || editedAddress.getCep().isEmpty() ||
                    editedAddress.getNumber().isEmpty() || editedAddress.getCity().isEmpty() ||
                    editedAddress.getState().isEmpty()) {
                throw new IllegalArgumentException("Todos os campos de endereço devem ser preenchidos");
            }
            Address existAddress = existPeople.getAddresses().stream()
                    .filter(address -> address.getId().equals(editedAddress.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado"));
            existAddress.setPublicPlace(editedAddress.getPublicPlace());
            existAddress.setCep(editedAddress.getCep());
            existAddress.setNumber(editedAddress.getNumber());
            existAddress.setCity(editedAddress.getCity());
            existAddress.setState(editedAddress.getState());
            existAddress.setPrincipal(editedAddress.isPrincipal()); // Atualizar o endereço principal se necessário
            updatedAddresses.add(existAddress);
        }
        existPeople.setAddresses(updatedAddresses);

        // Salvar a pessoa atualizada
        return peopleRepository.save(existPeople);
    }


    public List<People> listAllPeople() {
        return peopleRepository.findAll();
    }

    public People listByCpf(String cpf) {
        Optional<People> peopleOptional = peopleRepository.findByCpf(cpf);
        if (peopleOptional.isEmpty()) {
            throw new IllegalArgumentException("Pessoa com o CPF " + cpf + " não encontrada");
        }
        return peopleOptional.get();
    }
}
