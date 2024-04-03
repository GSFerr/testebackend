package com.testattus.taskmanager.repository;

import com.testattus.taskmanager.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPeopleId(Long peopleId);
}
