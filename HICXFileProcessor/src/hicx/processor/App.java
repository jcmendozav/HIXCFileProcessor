package hicx.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

/*
 * This class process all the created files from an input directory 
 * and moves them to a output  directory
 * */

public class App {

	DateTimeFormatter df = DateTimeFormatter.ofPattern(Constants.dateFormat);
	
	public List<InputFile> getInputFiles() {
		return inputFiles;
	}

	public void setInputFiles(List<InputFile> inputFiles) {
		this.inputFiles = inputFiles;
	}

	public HashSet<String> getSupportedExt() {
		return supportedExt;
	}

	public void setSupportedExt(HashSet<String> supportedExt) {
		this.supportedExt = supportedExt;
	}

	public String getInputDir() {
		return inputDir;
	}

	public void setInputDir(String inputDir) {
		
		this.inputDir = inputDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	List<InputFile> inputFiles;
	HashSet<String> supportedExt = new HashSet<String>();
	String inputDir;
	String outputDir;

	public App( HashSet<String> supportedExt, String inputDir, String outputDir) {
		// TODO Auto-generated constructor stub
		setInputDir(inputDir);
//		setInputFiles(inputFiles);
		setOutputDir(outputDir);
		setSupportedExt(supportedExt);
	}

	public void processFiles() {


		WatchService watchService;
		try {
			
//			search for created files

			watchService = FileSystems.getDefault().newWatchService();
			Path path = Paths.get(getInputDir());

			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

			WatchKey key;
			
			
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					String fileAffected = String.valueOf(event.context());
					System.out.println("+++++++++++++++++++++ \n Event kind:" + event.kind() + ". File affected: " + fileAffected + ".");
					String ext = getExtention(fileAffected);
					
//					check for valid extentions

					if (getSupportedExt().contains(ext)) {
						InputFile inputFile = null;
						switch (ext) {
						case "txt":

							inputFile = new TextInputFile(String.format("%s/%s", getInputDir(), fileAffected));
							break;

						default:
							System.out.println(String.format("Please, define process for this new extention: %s",ext));
//							inputFile = new TextInputFile(String.format("%s/%s", getInputDir(), fileAffected));

							break;
						}

						
//						execute stats
						inputFile.performStats();
						
//						move files
						LocalDateTime now = LocalDateTime.now();
						
						String newPath = String.format("%s/%s_%s_.%s", 
								getOutputDir(),
								fileAffected,
								this.df.format(now),
								ext
								);
						Files.move(Paths.get(inputFile.getFilePath()),Paths.get( newPath));
						
					} else {
						System.out.println(String.format("!!! Extention %s not supported yet", ext));
					}
				}
				key.reset();
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getExtention(String fileAffected) {
		// TODO Auto-generated method stub
		return fileAffected.substring(fileAffected.lastIndexOf(".") + 1);
	}

}
