package dumper;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.ecircle.es.dataprovider.RandomTextEngine;

public class randomText {
	private static RandomTextEngine rndt = new RandomTextEngine();

	public static void main(String[] args) {

		Collection<String> randomTextCollection = rndt.nextWords(10000);

		for (Iterator<?> iterator = randomTextCollection.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			// System.out.println(string);

		}
		for (int i = 0; i < 10; i++) {

			int size = randomTextCollection.size();
			int item = new Random().nextInt(size);
			int counter = 0;
			String randomWord = "";

			for (String obj : randomTextCollection) {
				if (counter == item)
					randomWord = obj;
				counter = counter + 1;
			}
			System.out.println(randomWord);
		}
	}
}
