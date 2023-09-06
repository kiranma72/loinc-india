package com.example.MapIt.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.apache.commons.codec.language.Soundex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MapIt.LuceneService.Indexer;
import com.example.MapIt.LuceneService.SearchServiceLucene;
import com.example.MapIt.repo.Email;
import com.example.MapIt.repo.IndianTestRepo;
import com.example.MapIt.repo.LoincRepo;
import com.example.MapIt.repo.SearchRepo;
import com.example.MapIt.tests.BulkTest;
import com.example.MapIt.tests.Editinfo;
import com.example.MapIt.tests.Forsorting;
import com.example.MapIt.tests.FullTest;
import com.example.MapIt.tests.IDstore;
import com.example.MapIt.tests.LoincTest;
import com.example.MapIt.tests.SearchClass;

@CrossOrigin
@RestController
@RequestMapping("/FullTest")
public class FullTestOutputController {
	     @Autowired
	     private SearchRepo sp;
	     
	     @Autowired
	     private Email em;
	     
	     @Autowired
	     private LoincRepo loincrepo;
	     
	     @GetMapping("/findbyid/{id}")
		    public List<FullTest> findbyid(@PathVariable String id){ 
	    	   List<SearchClass>searchtests=new ArrayList<>();
		    	System.out.println(id);
		    	Optional<SearchClass>op=sp.findById(id);
		    	if(op.isPresent()) {
		    		searchtests.add(op.get());
		    	}
		    	else {
		    		List<SearchClass>lt=sp.findByPhoneticindianname(getphonetic(id));
		             for(SearchClass test:lt) {
		            	 if((test.getIndianname()).equals(id)) {
		            		 searchtests.add(0, test);
		            	 }
		            	 else {
		            		 searchtests.add(test);
		            	 }
		             }
		    	}
		    	
		    	return findbysearchClass(searchtests);
		     }
	  public String getphonetic(String input) {
		  Soundex soundex = new Soundex();
			return soundex.encode(input);
	  }
	  
	  @GetMapping("/lucene/")
	    public List<FullTest> findbyLucene(@RequestParam("name") String testtofind){  System.out.println(testtofind);
	     
	    testtofind=testtofind.trim();
		  String id =decodeValue(testtofind);
		  if(id==null) {System.out.println("an empty string is called");
			  return new ArrayList<>();
		  }
		  SearchServiceLucene ssl=new SearchServiceLucene();
	   List<SearchClass> searchtests=new ArrayList<>();
	   StringTokenizer ids =new StringTokenizer(id," ");
	   while(ids.hasMoreTokens()) {
		   String idcheck=ids.nextToken().trim();
		   System.out.println(idcheck);
		   try {
				searchtests.addAll(ssl.search(idcheck));
				searchtests.addAll(ssl.search(getphonetic(idcheck)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		   System.out.println(searchtests);
	   }
	    	return (getfirstten(searchtests,id));
	     }  

	  
	  public List<FullTest> findbysearchClass(List<SearchClass>lt){
		  List<FullTest>tests=new ArrayList<>();
		  for(SearchClass test:lt) {
			  String indianname=test.getIndianname();
			  Optional<LoincTest>op =loincrepo.findById(test.getLoinccode());
			  if(op.isPresent()) {
				  LoincTest loinc=op.get();
				  FullTest fulltest=new FullTest();
				  fulltest.setIndianname(indianname);
				  fulltest.setClass_(loinc.getCLASS_());
				  fulltest.setCLASSTYPE_(loinc.getCLASSTYPE_());
				  fulltest.setComponent(loinc.getCOMPONENT_());
				  fulltest.setLoincCode(loinc.getLOINC_NUM());
				  fulltest.setLONG_COMMON_NAME(loinc.getLONG_COMMON_NAME());
				  fulltest.setMethod(loinc.getMETHOD_TYP());
				  fulltest.setProperty(loinc.getPROPERTY_());
				  fulltest.setScale(loinc.getSCALE_TYP());
				  fulltest.setSHORTNAME_(loinc.getSHORTNAME_());
				  fulltest.setSTATUS_(loinc.getSTATUS_());
				  fulltest.setSystem(loinc.getSYSTEM_());
				  fulltest.setTIME_ASPCT(loinc.getTIME_ASPCT());
				  fulltest.setVersionFirstReleased_(loinc.getVersionFirstReleased_());
				  fulltest.setVersionLastChanged_(loinc.getVersionLastChanged_());
				  tests.add(fulltest);
			  }  
		  }
		  return tests;
	  }
	  
	  
	  public List<FullTest> getfirstten(List<SearchClass>stl,String id){
		  Forsorting ar[]=new Forsorting [stl.size()];
		  StringTokenizer ids =new StringTokenizer(id," ");
		  List<SearchClass>ans=new ArrayList<>();
		  List<String>allid=new ArrayList<>();
		   while(ids.hasMoreTokens()) {
			   String idcheck=ids.nextToken().trim();
			   allid.add(idcheck);
		   }
		   double totalwords=allid.size();
		   int pt=0;
		   for(SearchClass scl:stl) {
			   List<String>namestring=getALLstrings(scl.getIndianname());
			   List<String>methodstring=getALLstrings(scl.getMethodused());		   
			   List<String>specimenstring=getALLstrings(scl.getPhoneticspecimentype());
	           double sum=0;
			   for(String s:allid) {
				   double maxvalue=0;
				   for(String st:namestring) {
					   maxvalue=Double.max(maxvalue,Getsimilarity.findSimilarity(s, st));
				   }
				   for(String st:methodstring) {
					   maxvalue=Double.max(maxvalue,Getsimilarity.findSimilarity(s, st));
				   }
				   for(String st:namestring) {
					   maxvalue=Double.max(maxvalue,Getsimilarity.findSimilarity(s, st));
				   }
				   sum+=maxvalue;
			   }
			   Forsorting sortit=new Forsorting(pt, sum);
			   ar[pt]=sortit;
			   pt++;
		   }
	Arrays.sort(ar,(a,b)->{ return Double.compare(b.sum, a.sum);});
	HashSet<String>filterit = new HashSet<>();
	List<FullTest>tests=new ArrayList<>();
  for(int t=0;(t<Math.min(15,ar.length))&&(filterit.size()<6);t++) {
	  if(!filterit.contains(stl.get(ar[t].getId()).getLoinccode())) {
		  ans.add(stl.get(ar[t].getId()));
		  FullTest temp=findusingsearchClass((stl.get(ar[t].getId())),(ar[t].getSum()*100)/totalwords);
		  tests.add(temp);
		  filterit.add(stl.get(ar[t].getId()).getLoinccode());
		  System.out.println(ar[t].getId()+" sum=="+ar[t].getSum());
	  }
  }
  return tests;
	  }
	  
	  public List<String> getALLstrings(String s) {
		  List<String>allid=new ArrayList<>();
          if(s==null) {
        	  return allid;
          }
		  StringTokenizer ids =new StringTokenizer(s," ");
		
		   while(ids.hasMoreTokens()) {
			   String idcheck=ids.nextToken().trim();
			   allid.add(idcheck);
		   }
		   return allid;
	  }
	  
	  public FullTest findusingsearchClass(SearchClass test,double percent){
		  FullTest tests=new FullTest();
			  String indianname=test.getIndianname();
			  double confidence
	            = Math.round(percent * 100) / 100.0;
			  Optional<LoincTest>op =loincrepo.findById(test.getLoinccode());
			  if(op.isPresent()) {
				  LoincTest loinc=op.get();
				  FullTest fulltest=new FullTest();
				  fulltest.setPercentage(confidence);
				  fulltest.setIndianname(indianname);
				  fulltest.setClass_(loinc.getCLASS_());
				  fulltest.setCLASSTYPE_(loinc.getCLASSTYPE_());
				  fulltest.setComponent(loinc.getCOMPONENT_());
				  fulltest.setLoincCode(loinc.getLOINC_NUM());
				  fulltest.setLONG_COMMON_NAME(loinc.getLONG_COMMON_NAME());
				  fulltest.setMethod(loinc.getMETHOD_TYP());
				  fulltest.setProperty(loinc.getPROPERTY_());
				  fulltest.setScale(loinc.getSCALE_TYP());
				  fulltest.setSHORTNAME_(loinc.getSHORTNAME_());
				  fulltest.setSTATUS_(loinc.getSTATUS_());
				  fulltest.setSystem(loinc.getSYSTEM_());
				  fulltest.setTIME_ASPCT(loinc.getTIME_ASPCT());
				  fulltest.setVersionFirstReleased_(loinc.getVersionFirstReleased_());
				  fulltest.setVersionLastChanged_(loinc.getVersionLastChanged_());
				  return fulltest; 
		  }
		  return tests;
	  }
	  
	  @GetMapping("/bulktest/")
	    public List<BulkTest> findbyBulkpath(@RequestParam("name") String testtofind){  System.out.println(testtofind);
	    testtofind=testtofind.trim();
		  if(testtofind==null) {System.out.println("an empty string is called");
			  return new ArrayList<>();
		  }
		  String id=decodeValue(testtofind);
		  SearchServiceLucene ssl=new SearchServiceLucene();
	   List<SearchClass> searchtests=new ArrayList<>();
	   StringTokenizer ids =new StringTokenizer(id," ");
	   while(ids.hasMoreTokens()) {
		   String idcheck=ids.nextToken().trim();
		   System.out.println(idcheck);
		   try {
				searchtests.addAll(ssl.search(idcheck));
				searchtests.addAll(ssl.search(getphonetic(idcheck)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		   System.out.println(searchtests);
	   }     
	        List<BulkTest>ans=new ArrayList<>();
	    	 List<FullTest>fts=(getfirstten(searchtests,id));
	    	 for(FullTest ft:fts) {
	    		 BulkTest bt=new BulkTest();
	    		 bt.setCheckingName(id);
	    		 bt.setClass_(ft.getClass_());
	    		 bt.setCLASSTYPE_(ft.getCLASSTYPE_());
	    		 bt.setComponent(ft.getComponent());
	    		 bt.setIndianname(ft.getIndianname());
	    		 bt.setLoincCode(ft.getLoincCode());
	    		 bt.setLONG_COMMON_NAME(ft.getLONG_COMMON_NAME());
	    		 bt.setMethod(ft.getMethod());
	    		 bt.setPercentage(ft.getPercentage());
	    		 bt.setProperty(ft.getProperty());
	    		 bt.setScale(ft.getScale());
	    		 bt.setSHORTNAME_(ft.getSHORTNAME_());
	    		 bt.setSTATUS_(ft.getSTATUS_());
	    		 bt.setSystem(ft.getSystem());
	    		 bt.setTIME_ASPCT(ft.getTIME_ASPCT());
	    		 ans.add(bt);
	    		 }
	    	 if(ans.size()>2) {
	    		 List<BulkTest>ans1=new ArrayList<>();
	    		 ans1.add(ans.get(0));
	    		 ans1.add(ans.get(1));
	    		 return ans1;
	    	 }
	    	 return ans;
	     }
	  
	  public static String decodeValue(String value) {
	        try {
	            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
	        } catch (UnsupportedEncodingException ex) {
	            throw new RuntimeException(ex.getCause());
	        }
	    }
	  
	  @GetMapping("/EditTest/{id}")
	    public List<Editinfo> findbyEditTest( @PathVariable String id){  
		  if(id==null) {System.out.println("an empty string is called");
			  return new ArrayList<>();
		  }	List<Editinfo>edittests=new ArrayList<>();
	    	List<SearchClass>testtoanalyze=sp.findByLoinccode(id);
	    	for(SearchClass test:testtoanalyze) {
	    		Editinfo et=new Editinfo(test.getLoinccode(),test.getIndianname());
	    		edittests.add(et);
	    	}
	    	System.out.println("Edit list is processed");
	    return edittests;
	     }
	   @GetMapping("/savetest")
	    public String saveit(@RequestParam("code")String loinccode,@RequestParam("name")String indianname){
	    	System.out.println("the new test with loincode"+loinccode+" and name"+indianname);
	    	indianname=decodeValue(indianname);
	    	if(sp.findById(indianname).isEmpty()) {
           Optional<LoincTest> newlt=loincrepo.findById(loinccode);
           if(newlt.isPresent()) {
           	LoincTest newLT=newlt.get();
           	SearchClass scl=new SearchClass();
           	scl.setIndianname(indianname);
           	scl.setLoinccode(loinccode);
           	scl.setMethodused(newLT.getMETHOD_TYP());
           	scl.setSpecimentype(newLT.getSYSTEM_());
           	scl.setPhoneticindianname(getphonetic(indianname));
           	scl.setPhoneticmethodused(getphonetic(newLT.getMETHOD_TYP()));
           	scl.setPhoneticspecimentype(newLT.getSYSTEM_());
           	sp.save(scl);
           	Indexer idr=new Indexer();
           	try {
					idr.indexTest(sp.findAll());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
           
           } 
           }
	    	else {
	    		System.out.println("The name already exist");
	    	}
	    	return "Done";
	    	}
	  
	   @GetMapping("/DeleteTest/")
	    public void DeleteTest( @RequestParam("name") String id){  
		  if(id==null) {System.out.println("an empty string is called"); }
		  id=decodeValue(id);
		 System.out.println(id);
		  sp.deleteById(id);
		  Indexer idr=new Indexer();
     	try {
				idr.indexTest(sp.findAll());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	   
	   
	   public void saveemail() {
		    IDstore id1=new IDstore("bt20eee008@nituk.ac.in");
		    em.save(id1);
	   }
	   
	   @GetMapping("/check/{id}")
	    public String Checkid( @PathVariable String id){ 
		   System.out.println(id);
		     //saveemail();
		     if(em.findById(id).isPresent()) {System.out.println("rightid matched");
		    	 return "Yes";}
		     return "not";
		 
	     }
	   
}
	  
