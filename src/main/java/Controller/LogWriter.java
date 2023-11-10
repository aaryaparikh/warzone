package Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * write the output to log file
 */
public class LogWriter implements Observer {

	/**
	 * FileWriter instance
	 */
	private FileWriter d_logFile;

	/**
	 * DateTimeFormatter instance
	 */
	private DateTimeFormatter d_formatter;

	/**
	 * LocalDateTime Instance
	 */
	private LocalDateTime d_now;

	/**
	 * Constructor to attach the object of LogEntryBuffer
	 *
	 * @param p_logEntry object of LogEntryBuffer
	 */
	public LogWriter(LogEntryBuffer p_logEntry) {
		p_logEntry.attach(this);
		d_formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * Display the current time and the state changes
	 *
	 * @param p_observableState current state
	 */
	@Override
	public void update(Observable p_observableState) {

		try {
			d_now = LocalDateTime.now();
			d_logFile = new FileWriter(("src/main/resources/log.txt"), true);
			d_logFile.append(
					d_formatter.format(d_now) + ">> " + ((LogEntryBuffer) p_observableState).getString() + "\n");
			d_logFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}