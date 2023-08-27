package com.example.MapIt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MapIt.tests.IDstore;

public interface Email extends JpaRepository<IDstore,String> {
   
}
