package com.example.algorithms;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ImageSorting {

	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String directoryName = "G:\\Filtered Photos\\30. After Marriage\\Singapore\\Universal Studio Singapore";
		List<File> files = new ArrayList<>();
		listf(directoryName, files);
		System.out.println(files.size());
		
		// 2. Create Folders as per Mon-YYYY
		ImageSorting obj = new ImageSorting();
		try {
			obj.searchFiles(files);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Total execution time :: " + (end-start)/1000 + " seconds");
		// 3. Check if the image is present then create copy folder and move file there
		// a. if file is already present, then create {imagename}-copy-1.jpg file
		// 5. If file is not present move the file

	}

	public static  void listf(String directoryName, List<File> files) {
		File directory = new File(directoryName);
		
		// Get all files from a directory.
		File[] fList = directory.listFiles();

		if (fList != null)
			for (File file : fList) {
				if (file.isFile()) {
					files.add(file);
					System.out.println(file.getAbsolutePath());
				} else if (file.isDirectory()) {
					listf(file.getAbsolutePath(), files);
				}
			}
	}

	private void searchFiles(List<File> list) throws IOException {
		
		for (File file : list) {
			try {
				Metadata metadata = ImageMetadataReader.readMetadata(file);
				for (Directory directory : metadata.getDirectories()) {
					for (Tag tag : directory.getTags()) {
						if(tag != null && "File Modified Date".equals(tag.getTagName())) {
							String dateInString = tag.getDescription();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss +08:00 yyyy", Locale.ENGLISH);
							LocalDateTime dateTime = LocalDateTime.parse(dateInString, formatter);
							String fileNameWithDateTime = dateTime.format(formatter);
							formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
							
							formatter = DateTimeFormatter.ofPattern("yyyy-MM");
							String monthYearFolderName = dateTime.format(formatter);
							
							String monthYearFolderRoot = "G:\\Sorted_Photos\\";
							File monthYearFolder = new File(monthYearFolderRoot + monthYearFolderName);
							
							if (! monthYearFolder.exists()) {
								monthYearFolder.mkdir();
								System.out.println(monthYearFolder.getName() + " Folder created");
						    }
							String fileNameExtension = file.getName()
									.substring(file.getName().lastIndexOf('.') + 1);
							
//							path.toFile().renameTo(new File(monthYearFolder+"\\" + fileNameWithDateTime+"."+fileNameExtension));
							
							File destination = new File(monthYearFolderRoot + monthYearFolderName +"\\"+ file.getName());
							
							if(destination.exists()) {
								
								if(file.length() == destination.length()) {
									file.delete();
									System.out.println(file.getAbsolutePath() +" is deleted!");
								} else {
									String copyFolderName = monthYearFolderRoot + monthYearFolderName +"\\copy\\";
									File copyFolder = new File(copyFolderName);
									
									if (!copyFolder.exists()) {
										copyFolder.mkdir();
								    }
									
									
									File[] fList = copyFolder.listFiles();
									
									int max = 1;
									String str = file.getName().replace("."+fileNameExtension, "");
									
									for (int i = 0; i < fList.length; i++) {
										if (fList[i].getName().contains("-") && fList[i].getName().contains(str)) {
											String result = fList[i].getName().substring(fList[i].getName().lastIndexOf("-") + 1, fList[i].getName().indexOf("."));
											
											if(Integer.parseInt(result) >= max) {
												max = Integer.parseInt(result);
												max++;
											}
										}
									}
									
									String copyFolderFileName = monthYearFolderRoot + monthYearFolderName +"\\copy\\" + str +"-"+ max +"."+ fileNameExtension;
									
									destination = new File(copyFolderFileName);
								}
							} else {
								FileUtils.moveFile(file, destination);
							}
						}		
					}
				}
				
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}