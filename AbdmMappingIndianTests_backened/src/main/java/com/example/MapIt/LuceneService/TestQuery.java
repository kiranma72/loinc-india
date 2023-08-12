package com.example.MapIt.LuceneService;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestQuery {

    private String find;
	@Override
	public String toString() {
		return "MovieQuery [find=" + find + ", genres=" + genres + "]";
	}
	public TestQuery() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestQuery(String find, List<String> genres) {
		super();
		this.find = find;
		this.genres = genres;
	}
	public String getFind() {
		return find;
	}
	public void setFind(String find) {
		this.find = find;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	private List<String> genres;

}
