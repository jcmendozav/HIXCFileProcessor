package hicx.processor;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashSet<String> supportedExt= new HashSet<String>(Arrays.asList("txt"));
//		List<InputFile> inputFiles = ;
		String inputDir = "/Users/jampier/Downloads/HICX";
		String outputDir = "/Users/jampier/Downloads/HICX/processed";
		
		if(args.length>=3) {
			inputDir = args[0];
			outputDir = args[1];
			supportedExt.addAll(Arrays.asList(args[2].split(Pattern.quote(","))));

		}
		
		if(!new File(inputDir).exists()) {
			System.out.println(String.format("inputDir: %s doesnt exist.",inputDir));
			return;
		}
		
		if(!new File(outputDir).exists()) {
			System.out.println(String.format("outputDir: %s doesnt exist.",outputDir));
			return;
		}
		App app = new App( supportedExt, inputDir, outputDir);
		app.processFiles();
	}

}
