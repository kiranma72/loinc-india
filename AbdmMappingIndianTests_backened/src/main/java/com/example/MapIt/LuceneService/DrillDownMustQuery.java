package com.example.MapIt.LuceneService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryVisitor;
import org.apache.lucene.search.TermQuery;



public class DrillDownMustQuery extends Query{

    /** Creates a drill-down term. */
    public static Term term(String field, String dim, String... path) {
        return new Term(field, FacetsConfig.pathToString(dim, path));
    }

    private final FacetsConfig config;
    private final Query baseQuery;
    private final List<BooleanQuery.Builder> dimQueries = new ArrayList<>();
    private final Map<String, Integer> drillDownDims = new LinkedHashMap<>();
    private final List<Query> builtDimQueries = new ArrayList<>();
    private final Set<Integer> dirtyDimQueryIndex = new HashSet<>();

    /** Used by clone() and DrillSideways */
    DrillDownMustQuery(
            FacetsConfig config,
            Query baseQuery,
            List<BooleanQuery.Builder> dimQueries,
            Map<String, Integer> drillDownDims) {
        this.baseQuery = baseQuery;
        this.dimQueries.addAll(dimQueries);
        this.drillDownDims.putAll(drillDownDims);
        this.config = config;
        for (int i = 0; i < this.dimQueries.size(); i++) {
            this.builtDimQueries.add(null);
            this.dirtyDimQueryIndex.add(i);
        }
    }

    /**
     * Creates a new {@code DrillDownMustQuery} without a base query, to perform a pure browsing query
     * (equivalent to using {@link MatchAllDocsQuery} as base).
     */
    public DrillDownMustQuery(FacetsConfig config) {
        this(config, null);
    }

    /**
     * Creates a new {@code DrillDownMustQuery} over the given base query. Can be {@code null}, in which
     * case the result {@link Query} from {@link #rewrite(IndexReader)} will be a pure browsing query,
     * filtering on the added categories only.
     */
    public DrillDownMustQuery(FacetsConfig config, Query baseQuery) {
        this.baseQuery = baseQuery;
        this.config = config;
    }

    /**
     * Adds one dimension of drill downs; if you pass the same dimension more than once it is OR'd
     * with the previous constraints on that dimension, and all dimensions are AND'd against each
     * other and the base query.
     */
    public void add(String dim, String... path) {
        String indexedField = config.getDimConfig(dim).indexFieldName;
        add(dim, new TermQuery(term(indexedField, dim, path)));
    }

    /**
     * Expert: add a custom drill-down subQuery. Use this when you have a separate way to drill-down
     * on the dimension than the indexed facet ordinals.
     */
    public void add(String dim, Query subQuery) {
        assert dimQueries.size() == builtDimQueries.size();
        assert drillDownDims.size() == dimQueries.size();
        if (!drillDownDims.containsKey(dim)) {
            drillDownDims.put(dim, drillDownDims.size());
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            dimQueries.add(builder);
            builtDimQueries.add(null);
        }
        final int index = drillDownDims.get(dim);
        dimQueries.get(index).add(subQuery, BooleanClause.Occur.MUST);
        dirtyDimQueryIndex.add(index);
    }

    @Override
    public DrillDownMustQuery clone() {
        return new DrillDownMustQuery(config, baseQuery, dimQueries, drillDownDims);
    }

    @Override
    public int hashCode() {
        return classHash() + Objects.hash(baseQuery, dimQueries);
    }

    @Override
    public boolean equals(Object other) {
        return sameClassAs(other) && equalsTo(getClass().cast(other));
    }

    private boolean equalsTo(DrillDownMustQuery other) {
        return Objects.equals(baseQuery, other.baseQuery) && dimQueries.equals(other.dimQueries);
    }

    @Override
    public Query rewrite(IndexReader r) throws IOException {
        BooleanQuery rewritten = getBooleanQuery();
        if (rewritten.clauses().isEmpty()) {
            return new MatchAllDocsQuery();
        }
        return rewritten;
    }

    @Override
    public String toString(String field) {
        return getBooleanQuery().toString(field);
    }

    @Override
    public void visit(QueryVisitor visitor) {
        visitor.visitLeaf(this);
    }

    private BooleanQuery getBooleanQuery() {
        BooleanQuery.Builder bq = new BooleanQuery.Builder();
        if (baseQuery != null) {
            bq.add(baseQuery, BooleanClause.Occur.MUST);
        }
        for (Query query : getDrillDownQueries()) {
            bq.add(query, BooleanClause.Occur.FILTER);
        }

        return bq.build();
    }

    /**
     * Returns the internal baseQuery of the DrillDownMustQuery
     *
     * @return The baseQuery used on initialization of DrillDownMustQuery
     */
    public Query getBaseQuery() {
        return baseQuery;
    }

    /**
     * Returns the dimension queries added either via {@link #add(String, Query)} or {@link
     * #add(String, String...)}
     *
     * @return The array of dimQueries
     */
    public Query[] getDrillDownQueries() {
        for (Integer dirtyDimIndex : dirtyDimQueryIndex) {
            builtDimQueries.set(dirtyDimIndex, this.dimQueries.get(dirtyDimIndex).build());
        }
        dirtyDimQueryIndex.clear();

        return builtDimQueries.toArray(new Query[0]);
    }

    Map<String, Integer> getDims() {
        return drillDownDims;
    }
}
