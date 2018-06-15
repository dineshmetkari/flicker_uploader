package es.gruposistemasdistribuidos.practicasd2.src;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.flickr4java.flickr.uploader.Uploader;


class testThread2 implements Runnable {
		
		private Thread t;
		private String threadName;
		private File src;
		private File dest;
		boolean suspended = false;
		
		private File file;
		private UploadMetaData metaData;
		private Uploader uploader;
		private UploadPhoto190318 upload;
		private String photoId;
		
		 testThread2(String name,UploadPhoto190318 upload,Uploader uploader, File file, UploadMetaData metaData) throws Exception {
			 threadName = name;
			 this.upload=upload;
			 this.uploader=uploader;
			 this.file = file;
			 this.metaData=metaData;
			 System.out.println("Creating " +  threadName );
		 }
				
			 
		testThread2(String name,File source,File destination){
			threadName = name;
			src = source;
			dest = destination;
			System.out.println("Creating " +  threadName );
		}

		@Override
		public void run() {
			System.out.println("Running " +  threadName );
			 try {
				this.photoId = uploader.upload(file, metaData);
				System.out.println("Upload completed:" + this.photoId);
			} catch (FlickrException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			
		}
		
		//@Override
		public void run1() {
			System.out.println("Running " +  threadName );
			try{
	        	copyFolder(src,dest);
	           }catch(IOException e){
	        	e.printStackTrace();
	                System.exit(0);
	           }
		}
		
		public static void copyFolder(File src, File dest)
		    	throws IOException{
		 
		    	if(src.isDirectory()){
		    		if(!dest.exists()){
		    		   dest.mkdir();
		    		   System.out.println("Directory copied from " 
		                              + src + "  to " + dest);
		    		}
		 
		    		String files[] = src.list();
		 
		    		for (String file : files) {
		    		   File srcFile = new File(src, file);
		    		   File destFile = new File(dest, file);
		    		   copyFolder(srcFile,destFile);
		    		}
		 
		    	}else{
		    			InputStream in = new FileInputStream(src);
		    	        OutputStream out = new FileOutputStream(dest); 
		    	        byte[] buffer = new byte[1024];
		    	        int length;
		    	        while ((length = in.read(buffer)) > 0){
		    	    	   out.write(buffer, 0, length);
		    	        }
		 
		    	        in.close();
		    	        out.close();
		    	        System.out.println("File copied from " + src + " to " + dest);
		    	}
		    }

		public void start(){
			System.out.println("Starting " +  threadName );
			if(t==null){
				 t = new Thread (this, threadName);
				 t.start();
			}
		}
		
	}

	public class Multithread4 {
	
		   public static void main(String args[]) throws InterruptedException {
			   
			   File srcFolder = new File("D:\\Dinesh\\Images");
			   
			   File[] files = srcFolder.listFiles();
			   System.out.println("files listed:" + files.length);
			   
			   testThread2[] t=new testThread2[files.length];
			   
			   boolean[] copy = new boolean[files.length];
			   
			   ExecutorService pool = Executors.newFixedThreadPool(files.length);
			   
			   int i = 0;
			   for(File f:files){
				   String t1 = "t"+i;
				   System.out.println(t1);
				   File dest1 = new File("D:\\Dinesh\\tmp"+"\\"+f.getName());
				   Runnable task = new testThread2(t1,(f.getAbsoluteFile()),new File("D:\\Dinesh\\tmp"+"\\"+f.getName()));
				   pool.execute(task);
				   i++;
			   }
			   
			   pool.shutdown();
			   try {
				   pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} 
			   catch (InterruptedException e) {
				   e.printStackTrace();
				}
			  
			   System.out.println("copying completed");
			   
		   }  
private static String threadName = "t";
private static int threadCounter;
private static int maxThreads=100;
private static ExecutorService pool = Executors.newFixedThreadPool(maxThreads);
//private static testThread2[] t=new testThread2[100];
   public static void uploadUsingThread(UploadPhoto190318 upload, Uploader uploader, File file, UploadMetaData metaData) throws Exception {
			   			   
			   threadCounter +=1;
			   threadName = threadName + threadCounter;
			   Runnable task = new testThread2(threadName,upload, uploader, file, metaData);
			   pool.execute(task);
			   			   
			   //pool.shutdown();
			   try {
				   pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} 
			   catch (InterruptedException e) {
				   e.printStackTrace();
				}
			  
			   System.out.println("Upload completed");
			   
		   }  
   
   
   public static String uploadfile(UploadPhoto190318 upload,String filename, String inpTitle) throws Exception {
	   
	   return upload.uploadfile(filename, inpTitle);
   }
  
   

	}