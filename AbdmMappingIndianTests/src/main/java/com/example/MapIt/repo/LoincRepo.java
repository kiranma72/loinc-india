package com.example.MapIt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MapIt.tests.LoincTest;

public interface LoincRepo extends JpaRepository<LoincTest,String> {

}

