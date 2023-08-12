package com.example.MapIt.LuceneService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MapIt.tests.SearchClass;


public class SearchServiceLucene {
	LuceneTestDataSearcher searcher;
	int limit = 100;

	int threshold = 100;
	public List<SearchClass> search( @PathVariable  String id) throws IOException {
		System.out.println("starting the test686");
		String searchQuery = id;
		searcher = new LuceneTestDataSearcher(Indexer.PATH, limit, threshold);
		Map<String,Object> tests = (Map<String, Object>) searcher.searchDocumentsWithFacets(new TestQuery(searchQuery, Arrays.asList("indianname","methodused","specimentype")));
		List<Tests> testsList = (List<Tests>) tests.get(LuceneTestDataSearcher.RESULT);
		if(testsList!=null){
			testsList.stream().forEach(System.out::println);
		}
		List<SearchClass>scl=new ArrayList<>();
		for(Tests test:testsList) {
			SearchClass searchclass =new SearchClass();
			searchclass.setLoinccode(test.getLoinccode());
			String indianname="";
			String name=test.getIndianname();
			StringTokenizer stz=new StringTokenizer(name,"%%%%%");
			while(stz.hasMoreTokens()) {
				indianname=stz.nextToken().trim();
				break;
			}
			searchclass.setIndianname(indianname);
			scl.add(searchclass);
		}
		System.out.println("completed the search");
		return scl;
		//System.out.println(tests.get(LuceneTestDataSearcher.FACETS));
	}
}