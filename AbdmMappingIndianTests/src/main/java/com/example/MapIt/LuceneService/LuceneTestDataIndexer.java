package com.example.MapIt.LuceneService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesFacetField;
//import org.apache.lucene.facet.sortedset.SortedSetDocValuesFacetField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneTestDataIndexer {
	public final static String LOINC_CODE = "loincode";
	public final static String NAME = "indianname";
	public final static String METHODUSED = "methodused";
	public final static String SPECIMENTYPE = "specimentype";
	public static final String GENRES = "genres";
	private final Path path;
	
	public static FacetsConfig facetsConfig = new FacetsConfig();


	static {
		facetsConfig.setMultiValued(LuceneTestDataIndexer.GENRES, true);
		facetsConfig.setRequireDimCount(LuceneTestDataIndexer.GENRES,true);
	}

	public LuceneTestDataIndexer(String directoryPath) {System.out.println("yes the indexer is called ");
		path = Paths.get(directoryPath);
	}

	public void index(List<Tests> movies) throws IOException {
		System.out.println("Now starting the indexing part ");
		Directory dir = FSDirectory.open(path);
		
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		config.setOpenMode(OpenMode.CREATE);
		config.setRAMBufferSizeMB(256);
		IndexWriter writer = new IndexWriter(dir, config);
		addDocument(movies.iterator(), writer);
		writer.commit();
		writer.close();
		System.out.println("Now ending the indexing part ");
		
	}
	
	private void addDocument(Iterator<Tests> movies, IndexWriter writer) throws IOException {
		while (movies.hasNext()) {
			writer.addDocument(createDocument(movies.next()));
		}
	}
	private Document createDocument(Tests test) throws IOException {
		IndexableField id = new StoredField(LOINC_CODE, String.valueOf(test.getLoinccode()));
		IndexableField indianname = new TextField(NAME, test.getIndianname()+" "+"%%%%%"+" "+test.getPhonecticIn()+" "+"%%%%%"+" "+test.getMethodused()+" "+"%%%%%"+" "+test.getPhonecticmt()+" "+"%%%%%"+" "+test.getPhonecticlc()+" "+"%%%%%"+" "+test.getSpecimentype(),Store.YES);
		IndexableField methodused = new StoredField(METHODUSED, test.getMethodused());
		IndexableField specimentype = new StoredField(SPECIMENTYPE, String.valueOf(test.getSpecimentype()));
		Document doc = new Document();
		doc.add(id);
		doc.add(indianname);
		doc.add(methodused);
		doc.add(specimentype);
		for (String genre : test.getGenres()) {
			if(genre!=null && !genre.isEmpty()){
				IndexableField genresFacets = new SortedSetDocValuesFacetField(GENRES, genre);
				IndexableField genres = new TextField(GENRES,genre,Store.YES);
				doc.add(genresFacets);
				doc.add(genres);
			}
		}
		return facetsConfig.build(doc);
	}
}
