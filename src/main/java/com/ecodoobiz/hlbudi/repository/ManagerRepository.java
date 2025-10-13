package com.ecodoobiz.hlbudi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.ecodoobiz.hlbudi.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByUsername(String username);
}
