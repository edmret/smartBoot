package com.ipsmartboot.demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class Slideshow {

 	public static void saveImage(List<String> imageUrl, String destinationFile) throws IOException {
		
 		
		for(int i = 1; i <= imageUrl.size(); i++) {
			URL url = new URL(imageUrl.get(i-1));
			
	        
			String file =  destinationFile +"image" + i+".jpg";
			InputStream is = url.openStream();
			FileOutputStream os = new FileOutputStream(file);
	
			byte[] b = new byte[2048];
			int length;
	
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
	
			is.close();
			os.close();
		}
	}
 }