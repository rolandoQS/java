package com.example.java.repositories;

import com.example.java.entities.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {



}
