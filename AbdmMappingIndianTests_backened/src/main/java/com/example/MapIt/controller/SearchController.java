package com.example.MapIt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MapIt.repo.IndianTestRepo;
import com.example.MapIt.repo.LoincRepo;
import com.example.MapIt.repo.SearchRepo;
import com.example.MapIt.tests.IndianTest;
import com.example.MapIt.tests.LoincTest;
import com.example.MapIt.tests.SearchClass;

@RestController
@RequestMapping("/SearchTest")
public class SearchController {
     @Autowired
     private SearchRepo sp;
     @Autowired
     private LoincRepo loincrepo;
     @Autowired
     private IndianTestRepo itr;
	
	 @GetMapping("/findbyid/{id}")
	    public Optional<SearchClass> findbyid(@PathVariable String id){ 
	    	System.out.println(id);
	    	return sp.findById(id);
	     }
	    @PostMapping("/saveit")
	    public SearchClass saveit(@RequestBody SearchClass it){ 
	    	System.out.println(it.toString());
	    	return sp.save(it);
	    	}
	    @GetMapping("/saveallincurrentdbandletsstarttosaveit")
	    public List<SearchClass> saveAllpresenttests(){ 
	    	List<SearchClass>tests=new ArrayList<>();
	    	List<IndianTest>ittests=itr.findAll();
	    	for(IndianTest it:ittests) {
	    		String loincCode=it.getLOINC_Code();
	    		 StringTokenizer st = new StringTokenizer(it.getAliases_(),"$$");    
	    	     /* Checks if the String has any more tokens */  
	    		 LoincTest lt=loincrepo.getById(loincCode);
	    	     while (st.hasMoreTokens())   
	    	     {    String indianname=st.nextToken().trim();
	    	          System.out.println(indianname); 
	    	         String Specimentype=lt.getSYSTEM_();
	    	         String testmethod=lt.getMETHOD_TYP();
	    	        SearchClass test=new SearchClass(indianname,loincCode,testmethod,Specimentype,"","","");
	    	        test.setPhoneticindianname(indianname);
	    	        test.setPhoneticmethodused(testmethod);
	    	        test.setPhoneticspecimentype(Specimentype);
	    	       System.out.println(test.toString());
	    	       tests.add(test);
	    	       sp.save(test);
	    	     }
	    	}
	    	
	    	return tests;
	    	}
}
