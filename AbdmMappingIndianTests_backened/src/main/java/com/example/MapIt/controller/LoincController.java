package com.example.MapIt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MapIt.repo.LoincRepo;
import com.example.MapIt.tests.IndianTest;
import com.example.MapIt.tests.LoincTest;
@CrossOrigin
@RestController
@RequestMapping("/LoincTest")
public class LoincController {
	@Autowired
    private LoincRepo loincrepo;
   
	// To fetch data from the csv file and store it to database
	
	 @Autowired
	    private JobLauncher jobLauncher;
	    @Autowired
	    private Job job;

	    @PostMapping("/importtheLoincTests")
	    public void importCsvToDBJob() {
	        JobParameters jobParameters = new JobParametersBuilder()
	                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
	        try {
	            jobLauncher.run(job, jobParameters);
	        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
	            e.printStackTrace();
	        }
	    } 
	
	// importing function ends...
	
	//functions to find test by id (Loinccode)
	    @GetMapping("/findbyid/{id}")
	    public Optional<LoincTest> findbyid(@PathVariable String id){ 
	    	
	    	System.out.println(id);
	    	return loincrepo.findById(id);
	     }
	    
	    //functions to save new test
	    @PostMapping("/saveit")
	    public LoincTest saveit(@RequestBody LoincTest it){ 
	    	System.out.println(it.toString());
	    	return loincrepo.save(it);
	    	}
	    @GetMapping("/findall/{id}")
	    public List<LoincTest> findall(@PathVariable String id){ 
	    	
	    	System.out.println("the id is =="+id);
	    	return loincrepo.findAll();
	     }
	    
}
