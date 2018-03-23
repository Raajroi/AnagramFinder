
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;

public class AnagramFinde {

	static HashMap<String, String> words = new HashMap<String, String>(300000);

	public static HashMap<String, String> getSortedDictionary(String DictInputFile) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(DictInputFile));
		String input = "";

		while ((input = br.readLine()) != null) {

			// sort the string
			char[] inputChars = input.toCharArray();
			Arrays.sort(inputChars);
			String sortedInput = String.valueOf(inputChars);

			if (words.get(sortedInput) == null) {
				words.put(sortedInput, input);
			}

			else {
				String previousValue = words.get(sortedInput);
				words.put(sortedInput, previousValue + "; " + input);
			}
		}

		br.close();

		return words;
	}

	public static void printAnagrams(String s) {
		int count = 0;
		int length = s.length();
		int combsSize = (int) (Math.pow(2, (double) length) - 1);

		long start2 = System.currentTimeMillis();

		// hash set 
		
		HashMap<String, String> tried = new HashMap<String, String>(300000);

		for (int j = 0; j < combsSize; j++) {

			String mask = Integer.toBinaryString(j + 1);
			String buffer = "";

			int maskLength = mask.length();

			for (int k = maskLength - 1; k >= 0; k--) {

				if (mask.charAt(k) == '1') {
					int getChar = length - (maskLength - k);
					buffer = s.charAt(getChar) + buffer;
				}
			}
			// sort

			char[] bufferChars = buffer.toCharArray();
			Arrays.sort(bufferChars);
			String sortedBuffer = String.valueOf(bufferChars);

			if (tried.get(sortedBuffer) != null) {
			}

			// print the anagram if it is found in the dictionary
			else {
				
				tried.put(sortedBuffer, "");
				String dictVal = "";
				if ((dictVal = words.get(sortedBuffer)) != null ) {

					String[] dictValSplit = dictVal.split("; ");
				
					for (int m = 0; m < dictValSplit.length; m++) {
						if (dictValSplit[m].length() == length) {
							System.out.print(dictValSplit[m] + ",");
							count++;
						}
					}
				}
			}
		}
		long finish2 = System.currentTimeMillis();

		System.out.println("\n");
		if (count > 1) {
			
			System.out.println(count + " Anagrams found for " + s);
			System.out.println("Time taken to generate anagrams: " + (finish2 - start2) + " ms");
		} else {
			System.out.println("No anagrams found for " + s);

		}

	}

	public static void main(String args[]) {
		try {
			String dictInputFile = args[0];

			// import dictionary
			long start = System.currentTimeMillis();

			words = getSortedDictionary(dictInputFile);
			long finish = System.currentTimeMillis();

			System.out.println("Time taken to import dictionary: " + (finish - start) + " ms\n");

			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			System.out.println("Welcome to the Anagram Finder");

			while (true) {

				String word = "";
				System.out.print("Enter the word: ");
				word = input.nextLine();
				if (word.equals("exit")) {
					break;
				} else {
					printAnagrams(word);
				}
			}
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
}
