package com.example.MapIt.LuceneService;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.LabelAndValue;
import org.apache.lucene.facet.sortedset.DefaultSortedSetDocValuesReaderState;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesFacetCounts;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesReaderState;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.StoredFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.MultiCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class LuceneTestDataSearcher {
	public static final String RESULT = "result";
	public static final String FACETS = "facets";
	private Directory dir;
	private IndexSearcher searcher;
	private int limit;

	private int threshold;

	public LuceneTestDataSearcher(String path, int limit, int threshold) throws IOException {
		dir = FSDirectory.open(Paths.get(path));
		DirectoryReader indexReader = DirectoryReader.open(dir);
		searcher = new IndexSearcher(indexReader);
		this.limit = limit;
		this.threshold = threshold;
	}


	private List<String> tokenizeQuery(Analyzer analyzer, String query) {
		List<String> result = new ArrayList<>();
		try {
			TokenStream stream  = analyzer.tokenStream(null, new StringReader(query));
			stream.reset();
			while (stream.incrementToken()) {
				result.add(stream.getAttribute(CharTermAttribute.class).toString());
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public Object search(TestQuery movieQuery) throws IOException {
		Query query = getQuery(movieQuery);

		TopDocs result = searcher.search(query,limit);

		if (result.totalHits.value == 0) {
			return new ArrayList<>();
		}

		return getMovies(result);
	}

	private List<Tests> getMovies(TopDocs result) throws IOException {
		List<Tests> collection = new ArrayList<>();
		StoredFields fields = searcher.storedFields();
		for (ScoreDoc doc : result.scoreDocs) {
			Document d = fields.document(doc.doc);
			collection.add(getMovie(d));
		}
		return collection;
	}


	public Object searchDocumentsWithFacets(TestQuery movieQuery) throws IOException {
		BooleanQuery.Builder buildQuery = buildQuery(movieQuery);

		if(movieQuery.getGenres()!=null && !movieQuery.getGenres().isEmpty()){
			DrillDownMustQuery drillDownQuery = new DrillDownMustQuery(LuceneTestDataIndexer.facetsConfig);
			for(String g:movieQuery.getGenres()){
				drillDownQuery.add(LuceneTestDataIndexer.GENRES,g);
			}
			buildQuery.add(drillDownQuery,Occur.FILTER);
		}


		Query query =  buildQuery.build();

		FacetsCollector facetsCollector = new FacetsCollector(true);
		TopScoreDocCollector topScoreDocCollector= TopScoreDocCollector.create(limit,threshold);
		searcher.search(query,MultiCollector.wrap(topScoreDocCollector,facetsCollector));

		TopDocs result = topScoreDocCollector.topDocs();

		if (result.totalHits.value == 0) {
			return getSearchResult();
		}

		List<Tests> collection = getMovies(result);

		SortedSetDocValuesReaderState state =
				new DefaultSortedSetDocValuesReaderState(searcher.getIndexReader(),
						LuceneTestDataIndexer.facetsConfig);

		Facets facets = new SortedSetDocValuesFacetCounts(state, facetsCollector);

		FacetResult facetResult = facets.getAllChildren(LuceneTestDataIndexer.GENRES);


		Map<String,Object> map = getSearchResult();
		map.put(RESULT,collection);
		map.put(FACETS,facetResult);
		return map;

	}

	/**
	 * Returns Empty Search Result
	 * @return
	 */
	private Map<String,Object> getSearchResult(){
		Map<String,Object> map = new HashMap<>();
		map.put(RESULT,new ArrayList<>());
		map.put(FACETS,new FacetResult("",new String[]{},0,new LabelAndValue[]{},0));
		return map;
	}

	private Query getQuery(TestQuery movieQuery) {
		Query query =new MatchAllDocsQuery();;
		if(movieQuery!=null){
			return buildQuery(movieQuery).build();
		}
		return query;
	}

	private  BooleanQuery.Builder buildQuery(TestQuery movieQuery) {
		List<String> queries = tokenizeQuery(new StandardAnalyzer(), movieQuery.getFind());
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

		if(queries.isEmpty()){
			booleanQuery.add(new MatchAllDocsQuery(),Occur.SHOULD);
		}

		for(String s:queries){
			booleanQuery
					.add(new TermQuery(new Term(LuceneTestDataIndexer.NAME, s)), Occur.MUST);
		}
		return booleanQuery;
	}

	private Tests getMovie(Document document) {
		Tests test = new Tests();
		test.setLoinccode((document.get(LuceneTestDataIndexer.LOINC_CODE)));
		test.setIndianname(document.get(LuceneTestDataIndexer.NAME));
		test.setMethodused(document.get(LuceneTestDataIndexer.METHODUSED));
		test.setGenres(Arrays.asList(document.getValues(LuceneTestDataIndexer.GENRES)));
		test.setSpecimentype(document.get(LuceneTestDataIndexer.SPECIMENTYPE));
		return test;
	}

}