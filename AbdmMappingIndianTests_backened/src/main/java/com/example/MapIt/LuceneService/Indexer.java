package com.example.MapIt.LuceneService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MapIt.repo.SearchRepo;
import com.example.MapIt.tests.SearchClass;


@RestController
@RequestMapping("/indextests")
public class Indexer {
	@Autowired
	private SearchRepo sr;
	
	public final static String PATH = "F:\\desktop\\collect\\ABDM_MapIt\\LuceneDataSet";
	@GetMapping("/save")
	public void indexTest() throws IOException {
		LuceneTestDataIndexer indexer = new LuceneTestDataIndexer(PATH);
		List<SearchClass> Searchmovies =sr.findAll() ;
		List<Tests>movies=new ArrayList<>();List<String>prop=new ArrayList<>();
		prop.add("indianname");prop.add("Loinccode");prop.add("methodused");prop.add("specimentype");
		for(SearchClass scl:Searchmovies) {
			Tests test=new Tests(scl.getIndianname(),scl.getLoinccode(),scl.getMethodused(),scl.getPhoneticindianname(),scl.getPhoneticspecimentype(),scl.getPhoneticmethodused(),scl.getSpecimentype(),prop);
			movies.add(test);
		}
	   indexer.index(movies);
	}
}
