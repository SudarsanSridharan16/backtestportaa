package ru.backtesting.utils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

public class Logger {
    public static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("0.00");;
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMAT_SIMPLE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    
	private static Logger instance;

	private PrintStream out;

    private Logger() {
    	try {
			out = new PrintStream(System.out, true, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
    }
    
	public static synchronized Logger log() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	public String doubleLog(double number) {
		return DOUBLE_FORMAT.format(number);
	}
	
	public void info(String info) {
		out.println(info);
	}
	
	public static synchronized void setTableFormatter(Table table) {
		for(Column column : table.columns() ) {
			if ( column instanceof DateTimeColumn )
				((DateTimeColumn)column).setPrintFormatter(Logger.DATE_FORMAT_SIMPLE);
			else if ( column instanceof DoubleColumn )
				((DoubleColumn)column).setPrintFormatter(Logger.DOUBLE_FORMAT, "_missing value_");
		}
	}
}
