package net.etowns.vine.jUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.Enumeration;

/**
 * jarUtil
 * @author Yoshinari Toda (https://github.com/yoshinari/jUtils)
 * @version 1.0
 */
public class jarUtil {
	Properties sysProp = System.getProperties();
	FileUtil myFile = new FileUtil();
	String jarFile = "";
	String workDir = "";
	/**
	 * extractDocs from jar file
	 * @return document root path
	 */
	public String extractDocs(){
		// ref: http://stackoverflow.com/questions/1529611/how-to-write-a-java-program-which-can-extract-a-jar-file-and-store-its-data-in-s
		// sun.java.commandを抽出
		Set<Entry<Object, Object>> set = sysProp.entrySet();
		Iterator<Entry<Object, Object>> itr = set.iterator();
		while (itr.hasNext()) {
			Entry<Object, Object> me = itr.next();
			if (me.getKey().toString().equals("sun.java.command")){
				jarFile = me.getValue().toString();
				if (! jarFile.endsWith(".jar")){
					return "/docs";
				}
				break;
			}
		}
		// get tmpDir
		workDir = myFile.setWorkDir(0);
		try {
			JarFile jar = new java.util.jar.JarFile(jarFile);
			Enumeration<JarEntry> e = jar.entries();
			while (e.hasMoreElements()) {
				JarEntry file = (JarEntry) e.nextElement();
				if (file.toString().startsWith("docs")||file.toString().startsWith("templates")){
					File f = new java.io.File(workDir + File.separator + file.getName());
					if (file.isDirectory()) {
						f.mkdir();
						continue;
					}
					java.io.InputStream is = jar.getInputStream(file); // get the input stream
					java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
					while (is.available() > 0) {  // write contents of 'is' to 'fos'
						fos.write(is.read());
					}
					fos.close();
					is.close();
				}
			}
			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workDir + File.separator + "docs";
	}
}
