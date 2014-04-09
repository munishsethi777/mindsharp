package com.satya.Utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	public static void writeFile(String filePath,byte[] bytesIn) throws Exception{
		  InputStream in = null;
		  OutputStream out = null;
		  try{
			 
			  in = new ByteArrayInputStream(bytesIn);
			  out = new BufferedOutputStream(new FileOutputStream(filePath));
			  byte[] buffer = new byte[512];
			  while(in.read(buffer) != -1){
				  out.write(buffer);
			  }
			  out.flush();
		  } finally{
			  if(in != null) in.close();
			  if(out != null) out.close();
		  }
			  
	  }
}
