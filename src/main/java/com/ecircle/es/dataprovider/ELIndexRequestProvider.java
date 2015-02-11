/**
 * 
 */
package com.ecircle.es.dataprovider;

import java.io.IOException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Client;


public interface ELIndexRequestProvider {

    /**
     * Creates a new request builder with random data.
     * 
     * @param client 
     * @return
     * @throws IOException 
     */
    BulkRequestBuilder getNext(Client client) throws IOException;
    
}
