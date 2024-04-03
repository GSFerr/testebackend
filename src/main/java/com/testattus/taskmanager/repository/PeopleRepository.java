package com.testattus.taskmanager.repository;

import com.testattus.taskmanager.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
    boolean existsByCpf(String cpf);

    Optional<People> findByCpf(String cpf);

}
