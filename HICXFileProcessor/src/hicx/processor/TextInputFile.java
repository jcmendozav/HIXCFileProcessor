package hicx.processor;

import java.util.Arrays;
import java.util.List;

/*
 * This class implements the text file
 * */

public class TextInputFile extends InputFile {

	public TextInputFile(String extention, String filePath, List<StatisticAlgorithm> stats) {
		setExtention(extention);
		setFilePath(filePath);
		setStats(stats);
	}
	
	public TextInputFile( String filePath) {
		setExtention("txt");
		setFilePath(filePath);
		setStats(Arrays.asList(new TextFileStatAlgo()));
	}
}
