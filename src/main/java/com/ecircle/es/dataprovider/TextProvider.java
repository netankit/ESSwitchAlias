/**
 * 
 */
package com.ecircle.es.dataprovider;

import java.util.Collection;

/**
 * Returns arbitrary words in english language.
 * 
 * @author Ankit Bahuguna
 *
 */
public interface TextProvider {

	/**
	 * Returns a {@link Collection} of distinct words.
	 * 
	 * @param size
	 *            number of returned words
	 * 
	 * @return
	 */
	Collection<String> nextWords(int size);

	/**
	 * Returns a collection of distinct phrases.
	 * 
	 * @param size
	 *            number of returned phrases
	 * 
	 * @return
	 */
	Collection<String> nextPhrases(int size);

}
