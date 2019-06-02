package randomWallpaper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

public class Main {
	
	private final static String RES = "2560x1440";
	private final static String BASEURL = "https://source.unsplash.com/random/";
	
	// windows stuff
	public static interface User32 extends Library {		
		User32 INSTANCE = (User32) Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
		boolean SystemParametersInfo(int one, int two, String s, int three);
	}
	
	public static void main(String[] args) {		
		// download random image from source.unsplash.com and set it as desktop background before deleting it
		try (InputStream in = new URL(BASEURL+RES).openStream()) {
			File img = new File("D:\\Pictures\\DesktopBackgrounds\\image.jpg");
			String path = img.toString();
			Files.copy(in, Paths.get(path));
			User32.INSTANCE.SystemParametersInfo(0x0014, 0, path, 1);
			TimeUnit.SECONDS.sleep(1);
			img.delete();
		} 
		catch (IOException e) {e.printStackTrace();}
		catch (InterruptedException e) {e.printStackTrace();}
	}	
}
