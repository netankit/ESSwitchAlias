package com.ecircle.es.dataprovider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

/**
 * The class provides random Text by mining the wikipedia XML corpus.
 * 
 * @author Ankit Bahuguna
 * 
 */
public class RandomTextEngine implements TextProvider {

	public List<String> words = new ArrayList<String>();
	public List<String> phrase = new ArrayList<String>();

	/**
	 * Returns a {@link Collection} of distinct words.
	 * 
	 * @param size
	 *            number of returned words
	 * 
	 * @return
	 */
	public Collection<String> nextWords(int size) {

		if (words.size() == 0) {
			initWords();
		}
		List<String> output = new ArrayList<String>();

		String myword;

		// Do some word processing
		for (int i = 0; i < size; i++) {
			myword = extractWord();
			output.add(myword);
		}

		return output;
	}

	private void initWords() {
		// 1. list files in documentFolder
		// 2. iterate over all files
		// 2a) read file as string and tokenize to words array
		words = new ArrayList<String>();

		System.out.println("Initializing words cache ...");

		File documentFolder = new File("src/main/resources/Corpus");
		File[] listFiles = documentFolder.listFiles();
		for (File file : listFiles) {
			try {
				String[] wordsColl;
				String documentContent = FileUtils.readFileToString(file);
				documentContent = cleanDocument(documentContent);
				wordsColl = documentContent.split(" ");
				for (String individualWord : wordsColl) {
					// We only add unique words to the index
					if (words.contains(individualWord)) {
						continue;
					} else {
						words.add(individualWord);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String cleanDocument(String documentContent) {
		documentContent = documentContent.replace("\n", " ");
		documentContent = documentContent.replace(".", " ");
		documentContent = documentContent.replace("!", " ");
		documentContent = documentContent.replace("-", " ");
		documentContent = documentContent.replace("_", " ");
		documentContent = documentContent.replace(",", " ");
		documentContent = documentContent.replace("   ", " ");
		documentContent = documentContent.replace("  ", " ");
		return documentContent;
	}

	private String extractWord() {

		// Value retrieved from array-list
		String item = "";
		// Index to be read from array-list
		int index = 0;
		Random randomGenerator = new Random();

		// Check the size, there is a possibility to have zero elements in your
		// stored file
		// System.out.println(words.size());
		if (words.size() > 0) {
			if (words.size() > 1) {
				// Get a random number
				index = randomGenerator.nextInt(words.size());
			} else {
				// If the array-list has only one item there is no need to get a
				// random number.
				index = 0;
			}
			// Get the indexed item
			item = words.get(index);
			// Remove item
			// words.remove(index);
		}
		item = item.trim();
		// Return the item
		return item;
	}

	/**
	 * Returns a collection of distinct phrases.
	 * 
	 * @param size
	 *            number of returned phrases
	 * 
	 * @return
	 */

	public Collection<String> nextPhrases(int size) {
		// TODO Auto-generated method stub
		List<String> myphrase = new ArrayList<String>();

		if (phrase.isEmpty()) {
			initPhraseBuilder();
		}
		Random randomGenerator = new Random();

		for (int k = 0; k < size; k++) {
			int randomIndex = randomGenerator.nextInt(phrase.size());
			myphrase.add(phrase.get(randomIndex));
		}

		return myphrase;
	}

	/**
	 * The method develops a phrase list of 1000 sentences, with random words.
	 * and returns it.
	 */
	private void initPhraseBuilder() {

		System.out.println("initializing phrases cache ...");

		int rand = 0;
		String myWord;
		int max = 12;
		int min = 6;
		do {
			rand = min + (int) (Math.random() * ((max - min) + 1));
		} while (rand == 0);

		for (long j = 0; j < 1000000; j++) {
			String sentence = "";
			for (int i = 0; i < rand; i++) {
				myWord = extractWord();
				if (sentence != "") {
					sentence += myWord + " ";
				} else
					sentence = myWord;
			}
			phrase.add(sentence);
		}

	}
}
