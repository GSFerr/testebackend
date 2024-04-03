package com.testattus.taskmanager.controller;

import com.testattus.taskmanager.Address;
import com.testattus.taskmanager.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereços")
public class AddressController {

    private AddressService addressService;

    @PostMapping("/salvar")
    public ResponseEntity<Address> saveAddress(@RequestBody Address address) {
        Address savedAddress = addressService.saveAddress(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Address> editAddress(@PathVariable Long id, @RequestBody Address address) {
        Address editedAddress = addressService.editAddress(id, address);
        return new ResponseEntity<>(editedAddress, HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Address>> listAllAddresses() {
        List<Address> addresses = addressService.listAllAddress();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/porPessoa/{pessoaId}")
    public ResponseEntity<List<Address>> listAddressByPeople(@PathVariable Long peopleId) {
        List<Address> addresses = addressService.listAddressByPeople(peopleId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }


}
