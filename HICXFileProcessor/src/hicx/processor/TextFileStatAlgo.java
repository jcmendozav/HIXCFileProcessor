package hicx.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/*
 * This class implements the statistics algorithms for text files
 * */

public class TextFileStatAlgo implements StatisticAlgorithm {

	
	
	@Override
	public void perform(String filePath) {
		// TODO Auto-generated method stub
		Path path = Paths.get(filePath);
		if(path.toFile().length()==0) {

			System.out.println(String.format(" "
					+ "Stats: \n "
					+ "filePath: %s \n is empty"
					,
					filePath
					
					)
					);		
			return;
		}
		int wordsCounter=0;
		int whiteSpacesCounter=0;
		String mostUsedWord=null;
		HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();
		try {
			BufferedReader br = Files.newBufferedReader(path);
		
			String line = br.readLine();
			
			while(line!=null) {
				
				// split by spaces
				String[] wordList = line.split("\\s+");
				wordsCounter+=wordList.length;
				
				// calculate white spaces
				whiteSpacesCounter+=wordList.length>0? wordList.length-1:0;
				
				for (int i = 0; i < wordList.length; i++) {
					// count words
					String w = wordList[i].toLowerCase();
					wordFrequency.put(w, wordFrequency.getOrDefault(w, 0)+1);
				}				
 				line= br.readLine();

			}
			br.close();
			
			// get the most used word
			mostUsedWord = wordFrequency.entrySet().stream()
					.max((e1,e2)->{
				return e1.getValue().compareTo(e2.getValue());
			}).get().getKey();

			System.out.println(String.format(" "
					+ "Stats: \n "
					+ "filePath: %s \n "
					+ "words: %s \n "
					+ "whiteSpaces: %s \n "
					+ "mostUsedWord: %s -> %s \n", 
					filePath,
					wordsCounter,
					whiteSpacesCounter,
					mostUsedWord,wordFrequency.getOrDefault(mostUsedWord,0)
					)
					);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
