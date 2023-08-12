package com.example.MapIt.controller;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.codec.language.Soundex;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MapIt.LuceneService.Indexer;
import com.example.MapIt.LuceneService.SearchServiceLucene;
import com.example.MapIt.repo.IndianTestRepo;
import com.example.MapIt.repo.LoincRepo;
import com.example.MapIt.repo.SearchRepo;
import com.example.MapIt.tests.IndianTest;
import com.example.MapIt.tests.LoincTest;
import com.example.MapIt.tests.SearchClass;

@RestController
@RequestMapping("/IndianTest")
public class IndianTestController {

	
	@Autowired
    private IndianTestRepo indianrepo;
	@Autowired
    private LoincRepo loincrepo;
	 @Autowired
     private SearchRepo sp;
//   To fetch data from the csv file and store it to database
	 @Autowired
	    private JobLauncher jobLauncher;
	    @Autowired
	    private Job job;
	    @PostMapping("/importIndianTests")
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
	    public Optional<IndianTest> findbyid(@PathVariable String id){
	    	System.out.println(id);
	    	return indianrepo.findById(id);
	    	}
	    public String getphonetic(String input) {
			  Soundex soundex = new Soundex();
				return soundex.encode(input);
		  }
	  //functions to delete all the tests stored 
	    @GetMapping("/deleteAlltheDAtaPresentthere")
	    public String deleteAll(){ 
	     indianrepo.deleteAll();
	    	return "Deletion is Done";
	    	}
	  //functions to save new test 
	    @PostMapping("/saveit")
	    public IndianTest saveit(@RequestBody IndianTest it){ 
	    	System.out.println(it.toString());
            Optional<LoincTest> newlt=loincrepo.findById(it.getLOINC_Code());
            if(newlt.isPresent()) {
            	LoincTest newLT=newlt.get();
            	SearchClass scl=new SearchClass();
            	scl.setIndianname(it.getAliases_());
            	scl.setLoinccode(it.getLOINC_Code());
            	scl.setMethodused(newLT.getMETHOD_TYP());
            	scl.setSpecimentype(newLT.getSYSTEM_());
            	scl.setPhoneticindianname(getphonetic(it.getAliases_()));
            	scl.setPhoneticmethodused(getphonetic(newLT.getMETHOD_TYP()));
            	scl.setPhoneticspecimentype(newLT.getSYSTEM_());
            	sp.save(scl);
            	Indexer idr=new Indexer();
            	try {
					idr.indexTest();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
            }
	    	return indianrepo.save(it);
	    	}
}
