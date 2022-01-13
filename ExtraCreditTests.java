/*
Kevin Corcoran
kjc100@pitt.edu
ID: 3855660
CS 0445 Fall 2021
Assignment 2: Test driver for Extra Credit methods .equals() and .getWord()
-	Tests each case of equals: Comparing two MyStringBuilders of different lengths,
	comparing two unequal MyStringBuilders of the same length, comparing two equal
	MyStringBuilders, and comparing two empty MyStringBuilders.
-	Tests each case of getWord by trying valid and invalid arguments on
	MyStringBuilders with one, many, and no words.
- The test show that these methods work as intended.
 */

public class ExtraCreditTests {

	public static void main(String[] args) {

		System.out.println("Testing .equals method: \n");

		MyStringBuilder b0 = new MyStringBuilder("K");
		MyStringBuilder b1 = new MyStringBuilder("This is a diff string.");
		MyStringBuilder b2 = new MyStringBuilder("This is a twin string.");
		MyStringBuilder b3 = new MyStringBuilder("This is a twin string.");
		MyStringBuilder b4 = new MyStringBuilder();
		MyStringBuilder b5 = new MyStringBuilder();

		if (b0.equals(b1))
		{
			System.out.println("\"" + b0 + "\" is equal to \"" + b1 + "\"");
		}
		else
		{
			System.out.println("\"" + b0 + "\" is not equal to \"" + b1 + "\"");
		}

		if (b1.equals(b2))
		{
			System.out.println("\"" + b1 + "\" is equal to \"" + b2 + "\"");
		}
		else
		{
			System.out.println("\"" + b1 + "\" is not equal to \"" + b2 + "\"");
		}

		if (b2.equals(b3))
		{
			System.out.println("\"" + b2 + "\" is equal to \"" + b3 + "\"");
		}
		else
		{
			System.out.println("\"" + b2 + "\" is not equal to \"" + b3 + "\"");
		}

		if (b3.equals(b4))
		{
			System.out.println("\"" + b3 + "\" is equal to \"" + b4 + "\"");
		}
		else
		{
			System.out.println("\"" + b3 + "\" is not equal to \"" + b4 + "\"");
		}

		if (b4.equals(b5))
		{
			System.out.println("\"" + b4 + "\" is equal to \"" + b5 + "\"");
		}
		else
		{
			System.out.println("\"" + b4 + "\" is not equal to \"" + b5 + "\"");
		}

		System.out.println();


		System.out.println("Testing .getWord method: \n");

		MyStringBuilder oneWord = new MyStringBuilder("Hello");
		MyStringBuilder manyWords = new MyStringBuilder("This is a sentence with many words.");
		MyStringBuilder noWords = new MyStringBuilder();

		System.out.println("Testing on sentence with one word: \"" + oneWord.toString() + "\"");
		System.out.println("0th word: " + oneWord.getWord(0));
		System.out.println("1st word?: " + oneWord.getWord(1));
		System.out.println("Can't have a negative word: " + oneWord.getWord(-20));
		System.out.println("Way past number of words: " + oneWord.getWord(20));

		System.out.println("Testing on sentence with many words: \"" + manyWords.toString() + "\"");
		System.out.println("0th word: " + manyWords.getWord(0));
		System.out.println("1st word: " + manyWords.getWord(1));
		System.out.println("2nd word: " + manyWords.getWord(2));
		System.out.println("3rd word: " + manyWords.getWord(3));
		System.out.println("4th word: " + manyWords.getWord(4));
		System.out.println("5th word: " + manyWords.getWord(5));
		System.out.println("6th word: " + manyWords.getWord(6));
		System.out.println("Past number of words: " + manyWords.getWord(7));
		System.out.println("Past number of words: " + manyWords.getWord(8));
		System.out.println("Can't have a negative word: " + manyWords.getWord(-20));
		System.out.println("Way past number of words: " + manyWords.getWord(20));

		System.out.println("Testing on sentence with no words: \"" + noWords.toString() + "\"");
		System.out.println("No words here: " + noWords.getWord(0));
		System.out.println("Still words here: " + noWords.getWord(1));
		System.out.println("Definitely no words here: " + noWords.getWord(-20));
		System.out.println("Nope, still no words: " + noWords.getWord(20));

	}

}
