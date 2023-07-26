package com.example.MapIt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MapIt.tests.SearchClass;

public interface SearchRepo extends JpaRepository<SearchClass,String> {
  public List<SearchClass> findByMethodused(String method);
  public List<SearchClass> findBySpecimentype(String specimentype);
  public List<SearchClass> findByPhoneticindianname(String phoneticindianname);
  public List<SearchClass> findByPhoneticspecimentype(String phoneticspecimentype);
  public List<SearchClass> findByPhoneticmethodused(String phoneticmethodused);
  public List<SearchClass> findByMethodusedAndSpecimentype(String methodused,String specimen);
  public List<SearchClass> findByPhoneticmethodusedAndPhoneticspecimentype(String phoneticmethodused,String specimen);
}
