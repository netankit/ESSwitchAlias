package dumper;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import com.ecircle.es.dataprovider.RandomTextEngine;

public class EsIndexDumper {
	private static RandomData rnd = new RandomDataImpl();
	private static RandomTextEngine rndtxt = new RandomTextEngine();

	public static void main(String[] args) throws IOException {

		// Command line Arguments
		long num_of_fields = Long.parseLong(args[0]);
		long num_of_document_ids = Long.parseLong(args[1]);
		String index_name = args[2];
		String type_name = args[3];

		if (args.length != 4) {
			System.out
					.println("java -jar millionFieldGenerator <num_of_fields> <num_of_document_ids> <index_name> <type_name>");
			System.exit(0);
		}

		Collection<String> randomTextCollection = rndtxt.nextWords(10000);

		int size = randomTextCollection.size();

		System.out.println("num_of_fields: " + num_of_fields);
		System.out.println("num_of_Document ID's: " + num_of_document_ids);

		Node node = nodeBuilder().node();
		Client client = node.client();

		BulkRequestBuilder bulkRequest = client.prepareBulk();

		String randomWord = "";

		/*
		 * Using Map to load the fields and random binary data into the es
		 * database.
		 */

		/*
		 * Generates more documents in the Elastic Search database based on the
		 * Number of document ID's
		 */

		for (int i = 0; i < num_of_document_ids; i++) {
			Map<String, Object> jsonData = new HashMap<String, Object>();

			for (long k = 0; k < num_of_fields; k++) {
				// System.out.println(rndtxt.words.get(0));
				// jsonData.put("field" + (i + 1), rnd.nextInt(0, 1));
				int item = new Random().nextInt(size);
				int counter = 0;
				for (String obj : randomTextCollection) {
					if (counter == item)
						randomWord = obj;
					counter = counter + 1;
				}
				jsonData.put("field" + (k + 1), randomWord);

			}
			bulkRequest.add(client.prepareIndex(index_name, type_name,
					Integer.toString(i + 1)).setSource(jsonData));
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println(bulkResponse.buildFailureMessage());
		}
		node.close();

	}
}
