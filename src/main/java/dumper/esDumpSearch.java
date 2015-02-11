package dumper;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;

public class esDumpSearch {
	public static void main(String[] args) {
		/*
		 * Setting up ES Client
		 */
		Node node = nodeBuilder().node();
		Client client = node.client();

		/*
		 * GET Response - Getting an entire row based on its id
		 */
		System.out.println("\nGET RESPONSE - Field ID 1");
		GetResponse responseGet = client.prepareGet("alias2", "val2", "1")
				.execute().actionGet();
		System.out.println(responseGet.getSourceAsString());

		/*
		 * Searching the index - Custom word search
		 */

		System.out.println("\n\nSEARCH RESPONSE Query:>> field3:Bank");
		SearchResponse responseSearch = client.prepareSearch("alias2")
				.setTypes("val2")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchQuery("field3", "Bank")).execute()
				.actionGet();
		System.out.println("Total search hits for Query: "
				+ responseSearch.getHits().getTotalHits());
		System.out.println("Search Response:\n" + responseSearch.toString());

	}
}
