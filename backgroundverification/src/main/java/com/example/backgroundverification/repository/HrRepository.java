package com.example.backgroundverification.repository;

import com.example.backgroundverification.model.Hr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HrRepository extends JpaRepository<Hr, Long> {
    Hr findByEmail(String email);
}

