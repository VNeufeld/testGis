package com.dev.gis.app.taskmanager.loggingView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

public class SplitFile {
	private static Logger logger = Logger.getLogger(SplitFile.class);

	public static int splitFileOld(File file) {
		
		int count = 0;
		int size = 0;
		int counter = 1;
		int maxSize = 100000000;
		List<String> rows = new ArrayList<String>();
		
		InputStream fis = null;
		 try {
		   fis = new FileInputStream(file);
		   InputStreamReader inR = new InputStreamReader(fis);
		   BufferedReader buf = new BufferedReader( inR );
		   String line;
		   while ( ( line = buf.readLine() ) != null ) {
				rows.add(line);
				count++;
				size = size + line.length();
				if ( size > maxSize ) {
					writeFile(file,counter,rows);
					rows.clear();
					size = 0;
					counter++;
				}
			   
		   }
			writeFile(file,counter,rows);
			return count;
		   
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			 try {
				fis.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 }
		 return -1;
	}



	private static void writeFile(File original, int counter,
			List<String> rows) throws IOException {
		String ext = FilenameUtils.getExtension(original.getAbsolutePath());
		String path = FilenameUtils.getFullPath(original.getAbsolutePath());
		String name = FilenameUtils.getBaseName(original.getAbsolutePath())+ "_" + counter + ".plog";
		
		String newFile = FilenameUtils.concat(path,name);
		logger.info("write file "+newFile + " rows = "+ rows.size());

		File of = new File(newFile);
		
		FileUtils.writeLines(of, rows);
		
		logger.info("exit write file "+newFile + " ");
		
		
	}
	
	private int splitFile(File file) {
		
		int count = 0;
		int size = 0;
		int counter = 1;
		int maxSize = 100000000;
		LineIterator li;
		List<String> rows = new ArrayList<String>();
		try {
			li = FileUtils.lineIterator(file);
			while ( li.hasNext()) {
				String s  = li.nextLine();
				rows.add(s);
				count++;
				size = size + s.length();
				if ( size > maxSize ) {
					writeFile(file,counter,rows);
					rows.clear();
					size = 0;
					counter++;
				}
				
			}
			
			writeFile(file,counter,rows);
			return count;
			
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return -1;
		
		
	}

}
