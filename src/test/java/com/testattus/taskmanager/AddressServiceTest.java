package com.testattus.taskmanager;

import com.testattus.taskmanager.repository.AddressRepository;
import com.testattus.taskmanager.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    public void setUp() {
        // Configuração inicial antes de cada teste, se necessário
    }

    @Test
    public void testSalvarEndereco() {
        // Criar um objeto Endereco para o teste
        Address address = new Address();
        address.setPublicPlace("Rua A");
        address.setCep("12345678");
        address.setNumber("123");
        address.setCity("Cidade A");
        address.setState("Estado A");
        address.setPrincipal(true);

        // Configurar comportamento do mock do EnderecoRepository
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // Chamar o método salvarEndereco do serviço
        Address savedAddress = addressService.saveAddress(address);

        // Verificar se o enderecoSalvo não é nulo
        assertNotNull(savedAddress);
        // Verificar se o método save do EnderecoRepository foi chamado
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    public void editAddressTest() {
        // Criar um objeto Endereco para o teste
        Long id = 1L;
        Address address = new Address();
        address.setId(id);
        address.setPublicPlace("Rua B");
        address.setCep("87654321");
        address.setNumber("456");
        address.setCity("Cidade B");
        address.setState("Estado B");
        address.setPrincipal(false);

        // Configurar comportamento do mock do EnderecoRepository
        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // Chamar o método editarEndereco do serviço
        Address enderecoEditado = addressService.editAddress(id, address);

        // Verificar se o enderecoEditado não é nulo
        assertNotNull(enderecoEditado);
        // Verificar se o método findById e save do EnderecoRepository foram chamados
        verify(addressRepository, times(1)).findById(id);
        verify(addressRepository, times(1)).save(any(Address.class));
    }
}
