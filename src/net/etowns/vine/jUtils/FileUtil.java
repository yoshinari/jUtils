package net.etowns.vine.jUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.prefs.Preferences;

/**
 * File IO Utilities.
 * @author Yoshinari Toda (https://github.com/yoshinari/jUtils)
 * @version 1.0
 */
public class FileUtil {
	ArrayList<String> fileName = new ArrayList<String>();
	String baseDir;
	
	/**
	 * Add file list under specified directory.
	 * @param dirName
	 */
	private void addFileList(String dirName){
		try {
			File dir = new File(dirName);
			String[] files = dir.list();
			for (String file: files){
				File currentFile = new File(dir.getAbsoluteFile() + File.separator + file);
				if (currentFile.isDirectory()){
					addFileList(currentFile.getAbsolutePath());
				} else {
					fileName.add(currentFile.getAbsolutePath().substring(baseDir.length()));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * listFiles
	 * @param docRoot
	 * @return Return file name array.
	 */
	public String[] listFiles(String docRoot){
		baseDir = docRoot;
		addFileList(docRoot);
		return (String[])fileName.toArray(new String[fileName.size()]);
	}
	/**
	 * Check lines of URL from urlList
	 * @param urlList
	 * @return Return number of URL, or -1 when fail.
	 */
	public Integer urlsInFile(String urlList){
		Integer urls = 0;
		try {
			File file = new File(urlList);
			if (! file.isFile()){
				return -1;
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				if (line.startsWith("http://") || line.startsWith("https://")){
					urls++;
				}
			}
			br.close();
			fr.close();
		} catch (IOException e){
			return -1;
		}
		return urls;
	}
	/**
	 * 
	 * @param urlList
	 * @return Return URL list which start with /, http://, or https:// only.
	 */
	public Integer urlPartsInFile(String urlList){
		Integer urls = 0;
		try {
			File file = new File(urlList);
			if (! file.isFile()){
				return -1;
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				if (line.startsWith("/") || line.startsWith("http://") || line.startsWith("https://")){
					urls++;
				}
			}
			br.close();
			fr.close();
		} catch (IOException e){
			return -1;
		}
		return urls;
	}
	/**
	 * 
	 * @param urlList
	 * @return Return URL list which start with http:// or https:// only.
	 */
	public String[] listUrlsInFile(String urlList){
		ArrayList<String> urls = new ArrayList<String>();
		try {
			File file = new File(urlList);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				if (line.startsWith("http://") || line.startsWith("https://")){
					urls.add(line);
				}
			}
			br.close();
			fr.close();
		} catch (IOException e){
			//
		}
		return (String[])urls.toArray(new String[urls.size()]);
	}
	public String[] listUrlPartsInFile(String urlList){
		ArrayList<String> urls = new ArrayList<String>();
		try {
			File file = new File(urlList);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				if (line.startsWith("/") || line.startsWith("http://") || line.startsWith("https://")){
					urls.add(line);
				}
			}
			br.close();
			fr.close();
		} catch (IOException e){
			//
		}
		return (String[])urls.toArray(new String[urls.size()]);
	}
	/**
	 * 
	 * @param url
	 * @return Returns URL Extension
	 */
	public String urlExtension(String url){
		String ext;
		if (url.contains("://")){
			url = url.substring(url.indexOf("://")+3);
			if (! url.contains("/")){	// specified only domain	url = "";
			}
		}
		if (url.contains("?")){
			url = url.substring(0, url.indexOf("?"));
		}
		if (url.contains("#")){
			url = url.substring(0, url.indexOf("#"));
		}
		ext = url.substring(url.lastIndexOf(".")+1).toLowerCase();
		if (ext.contains("/")){
			if (ext.toLowerCase().startsWith("php/")){	// Bad URL specification which I found.
			} else {
				ext = "";
			}
		}
		// http://www.zzz.ggg
		return ext;
	}
	/**
	 * 
	 * @param file
	 * @return Returns isFile.
	 */
	public boolean isFile(String file){
		File checkFile = new File(file);
		return checkFile.isFile();
	}
	/**
	 * 
	 * @param dir
	 * @return Returns isWritable
	 */
	public boolean isWritableDir(String dir){
		File checkDir = new File(dir);
		if (! checkDir.isDirectory()){
			return false;
		}
		if (! checkDir.canWrite()){
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param dir
	 * @return Returns File System Free Space.
	 */
	public Long getFreeSpace(String dir){
		File checkDir = new File(dir);
		return checkDir.getFreeSpace();
	}
	/**
	 * 
	 * @param url
	 * @return Return Boolean (is URL or NOT).
	 */
	public boolean isHtml(String url){
		String ext;
		ext = urlExtension(url);
		if (ext.equals("") || ext.equals("html") || ext.equals("htm")){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param threadNum
	 * @return Return Work Directory Path.
	 */
	public String setWorkDir(Integer threadNum){
		Properties sysProp = System.getProperties();
		String javaAppWorkDir = sysProp.getProperty("java.io.tmpdir");
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		// MAC OS Xの場合：JAVA_MAIN_CLASS_xxxxxx <= こ�?xxxxxxがスレ�?��IDを示す�?で、�?��ファイル名に含める
		switch (sysProp.get("os.name").toString()){
		case "Mac OS X":
//			System.out.println("Mac OS X");
			for (Entry<?, ?> entry: System.getenv().entrySet()) {
				if (entry.getKey().toString().startsWith("JAVA_MAIN_CLASS")){
					String workSession=entry.getKey().toString();
					javaAppWorkDir += entry.getValue().toString()+workSession.substring(workSession.lastIndexOf("_"))+"_"+threadNum;
					break;
				}
			}
			break;
		case "Linux":
			javaAppWorkDir += File.separator + prefs.parent().toString();
			break;
		default:
//			System.out.println("OS:["+sysProp.get("os.name").toString()+"]");
			//TODO: Set unique session id for Windows (No Idea)
			String wintmp= prefs.name();
			wintmp= prefs.parent().toString();
			wintmp = wintmp.substring(wintmp.lastIndexOf(" ")+1).replaceAll("/", "_") + "_" + prefs.name();
//			System.out.println("wintmp:"+wintmp);
			javaAppWorkDir += wintmp +"_"+threadNum;
		}
		File workDir = new File(javaAppWorkDir);
		if (! workDir.isDirectory()){
			workDir.mkdir();
		}
//		System.out.println("workDir:"+javaAppWorkDir);
		return javaAppWorkDir;
	}
	/**
	 * 
	 * @param filename
	 * @return Returns Hash Value.
	 */
	public String checkHashValue(String filename){
		String retStr = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			DataInputStream in = new DataInputStream(new FileInputStream(filename));
			try {
				while (true) {
					byte b = in.readByte();
					md5.update(b);
				}
			} catch (EOFException e){
				//	normal case ?
			}
			in.close();
			if (retStr.equals("")){
				retStr = toHexString(md5.digest());
			}
		} catch (NoSuchAlgorithmException e) {
			retStr = "NoSuchAlgorithmException";
		} catch (FileNotFoundException e) {
			retStr = "FileNotFoundException";
		} catch (IOException e) {
			retStr = "IOException";
		}
		return retStr;
	}
	/**
	 * 
	 * @param filename
	 * @param postProcess
	 * @return Return File Hash Value.
	 */
	public String checkHashValue(String filename, String postProcess){
		String retStr = checkHashValue(filename);
		switch (postProcess){
			case "DELETE":
				File delFile = new File(filename);
				delFile.deleteOnExit();	// is it ok?
				break;
		}
		return retStr;
	}
	/**
	 * http://homepage2.nifty.com/igat/igapyon/diary/2002/ig021213.html
	 * @param buf
	 * @return Return Hex String Value.
	 */
	private static String toHexString(byte[] buf) {
		String s = "";
		for (int i = 0; i < buf.length; i++) {
			int n = buf[i] & 0xff;
			if (n < 16) {
				s += " 0";
			} else {
				s += " ";
			}
			s += Integer.toHexString(n).toUpperCase();
		}
		return s;
	}
	/**
	 * 
	 * @param tgtFile
	 * @param line
	 */
	public void appendLine(String tgtFile, String line) {
		try{
			File file = new File(tgtFile);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
			pw.println(line);
			pw.close();
			pw = null;
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}