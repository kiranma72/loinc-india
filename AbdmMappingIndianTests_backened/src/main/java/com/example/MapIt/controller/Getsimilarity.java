package com.example.MapIt.controller;

public class Getsimilarity {
	public static int getLevenshteinDistance(String X, String Y)
	{
		int m = X.length();
		int n = Y.length();

		int[][] T = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			T[i][0] = i;
		}
		for (int j = 1; j <= n; j++) {
			T[0][j] = j;
		}

		int cost;
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				cost = X.charAt(i - 1) == Y.charAt(j - 1) ? 0: 1;
				T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
						T[i - 1][j - 1] + cost);
			}
		}

		return T[m][n];
	}

	public static double findSimilarity(String x, String y) {
		if (x == null || y == null) {
			return 0.0;
		}
		
		double maxLength = Double.max(x.length(), y.length());
		if (maxLength > 0) {
		String aa=	x.toUpperCase();
		String bb=	y.toUpperCase();
			// optionally ignore case if needed
			return (maxLength - getLevenshteinDistance(aa,bb)) / maxLength;
		}
		return 1.0;
	}
}
