package com.testattus.taskmanager;

import com.testattus.taskmanager.repository.AddressRepository;
import com.testattus.taskmanager.repository.PeopleRepository;
import com.testattus.taskmanager.service.PeopleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

	@Mock
	private PeopleRepository peopleRepository;

	@Mock
	private AddressRepository addressRepository;

	@InjectMocks
	private PeopleService peopleService;

	@Test
	public void savePeopleTest() {
		People people = new People();
		people.setName("Fulano");
		people.setDateOfBirth("1990-01-01");
		people.setCpf("12345678912");

		List<Address> addresses = new ArrayList<>();
		Address address = new Address();
		address.setPublicPlace("Rua Teste");
		address.setCep("12345678");
		address.setNumber("123");
		address.setCity("Cidade Teste");
		address.setState("Estado Teste");
		addresses.add(address);

		people.setAddresses(addresses);

		when(peopleRepository.existsByCpf(anyString())).thenReturn(false);
		when(peopleRepository.save(any(People.class))).thenReturn(people);

		People salvedPeople = peopleService.savePeople(people);

		assertNotNull(salvedPeople);
		assertEquals("Fulano", salvedPeople.getName());
		assertEquals("1990-01-01", salvedPeople.getDateOfBirth());
		assertEquals("12345678912", salvedPeople.getCpf());
		// Verifique se a lista de endereços da pessoa salva não está vazia
		assertFalse(salvedPeople.getAddresses().isEmpty());
		// Verifique se os detalhes do endereço da pessoa correspondem aos detalhes do endereço fornecido
		Address savedAddress = salvedPeople.getAddresses().get(0);
		assertEquals("Rua Teste", savedAddress.getPublicPlace());
		assertEquals("12345678", savedAddress.getCep());
		assertEquals("123", savedAddress.getNumber());
		assertEquals("Cidade Teste", savedAddress.getCity());
		assertEquals("Estado Teste", savedAddress.getState());
	}

	@Test
	public void listAllPeopleTest() {
		People people1 = new People();
		people1.setName("Fulano");
		people1.setDateOfBirth("1990-01-01");
		people1.setCpf("12365498745");
		People people2 = new People();
		people2.setName("Ciclano");
		people2.setDateOfBirth("1995-01-01");
		people2.setCpf("98765412336");

		when(peopleRepository.findAll()).thenReturn(Arrays.asList(people1, people2));

		List<People> pessoas = peopleService.listAllPeople();

		assertNotNull(pessoas);
		assertEquals(2, pessoas.size());
	}

	@Test
	public void listPeopleByCpf() {
		String cpf = "12345678912";
		People people = new People();
		people.setName("Fulano");
		people.setDateOfBirth("1990-01-01");
		people.setCpf(cpf); // Configurar o CPF do objeto de pessoa

		// Configurar o comportamento do mock para retornar um Optional contendo o objeto de pessoa
		when(peopleRepository.findByCpf(cpf)).thenReturn(Optional.of(people));

		// Chamar o método que está sendo testado
		People foundPeople = peopleService.listByCpf(cpf);

		// Verificar se o objeto retornado é o esperado
		assertNotNull(foundPeople);
		assertEquals("Fulano", foundPeople.getName());
		assertEquals("1990-01-01", foundPeople.getDateOfBirth());
		assertEquals(cpf, foundPeople.getCpf());
	}


}

