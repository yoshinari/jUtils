package net.etowns.vine.jUtils;
import java.io.File;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * SWT Util
 * @author Yoshinari Toda (https://github.com/yoshinari/jUtils)
 * @version 1.0
 */
public class SWTUtil{
	/**
	 * @param shell : Shell
	 * @param text  : File Path
	 * @param ext   : File extension
	 * @param title : text of [Open] File Dialog
	 */
	public void openFileDialog(Shell shell, Text text, String[] ext, String title){
		FileDialog openDialog = new FileDialog(shell,SWT.OPEN);
		openDialog.setFilterExtensions(ext);
		openDialog.setText(title);	// You can change the text of [Open] at File Dialog.
		if (text.getText().length() > 0
				&& text.getText().contains(File.separator)) {
			openDialog.setFilterPath(text.getText());
		}
		String filePath = openDialog.open();
		if (filePath != null) {
			text.setText(filePath);
			text.setFocus();
		}
	}
	/**
	 * @param shell : Shell
	 * @param text  : File Path
	 * @param path  :
	 * @param ext
	 * @param title
	 */
	public void openFileDialog(Shell shell, Text text, String path, String[] ext, String title){
		FileDialog openDialog = new FileDialog(shell,SWT.OPEN);
		openDialog.setFilterPath(path);
		openDialog.setFilterExtensions(ext);
		openDialog.setText(title);	// You can change the text of [Open] at File Dialog.
		if (text.getText().length() > 0
				&& text.getText().contains(File.separator)) {
			openDialog.setFilterPath(text.getText());
		}
		String filePath = openDialog.open();
		if (filePath != null) {
			text.setText(filePath);
			text.setFocus();
		}
	}
	/**
	 * @param listInfo
	 * @param str
	 */
	public void listAdd(List listInfo,String str){
		listInfo.add(str);
		listInfo.setSelection(listInfo.getItemCount()-1);
		listInfo.setSelection(-1);
	}
	/**
	 * @param pDisplay
	 * @param pList
	 * @param string
	 */
	public void syncAdd(Display pDisplay, final List pList, final String string) {
		pDisplay.syncExec(new Runnable() {
			public void run() {
				pList.add(string);
				pList.setSelection(pList.getItemCount()-1);
				pList.setSelection(-1);
			}
		});
	}
	/**
	 * @param pDisplay
	 * @param pList
	 * @param string
	 */
	public void asyncAdd(Display pDisplay, final List pList, final String string) {
		pDisplay.asyncExec(new Runnable() {
			public void run() {
				pList.add(string);
				pList.setSelection(pList.getItemCount()-1);
				pList.setSelection(-1);
			}
		});
	}
	/**
	 * @param pDisplay
	 * @param pList
	 * @param listPos
	 * @param string
	 */
	public void asyncSet(Display pDisplay, final List pList, final int listPos,
			final String string) {
		pDisplay.asyncExec(new Runnable() {
			public void run() {
				pList.setItem(pList.getItemCount()+listPos, string);
			}
		});
	}
	/**
	 * @param pDisplay
	 * @param pList
	 * @param listPos
	 * @param string
	 */
	public void syncSet(Display pDisplay, final List pList, final int listPos,
			final String string) {
		pDisplay.syncExec(new Runnable() {
			public void run() {
				pList.setItem(pList.getItemCount()+listPos, string);
			}
		});
	}
}