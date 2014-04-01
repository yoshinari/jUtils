package net.etowns.vine.jUtils;
import java.util.Date;
import java.text.*;

/**
 * Date Calculate class
 * @author Yoshinari Toda (https://github.com/yoshinari/jUtils)
 * @version 1.0
 * 
 */
public class DateCalc {
	/**
	 * @param format
	 * @return Return current date/time as SimpleDateFormat.
	 */
	public String date(String format){
		Date date = new Date();
		DateFormat dfm = new SimpleDateFormat(format);
		return dfm.format(date);
	}
	/**
	 * @return Return current date/time as yyyy-MM-dd HH:mm:ss format.
	 */
	public String date(){
		Date date = new Date();
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dfm.format(date);
	}
	/**
	 * 
	 * @param dateValue
	 * @return Return date/time of dateValue as yyyy-MM-dd HH:mm:ss format.
	 */
	public String date(long dateValue){
		Date date = new Date(dateValue);
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dfm.format(date);
	}
}