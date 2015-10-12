import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileWriter {
	
	// CSV file delimiter
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	// CSV file header
	private static final String FILE_HEADER = "lineNumber, cycleTime";
	
	public static void writeCsvFile(String fileName) {

		// Create new Stats objects
		
		int numLines = TCPClient.getNumLines();
		ArrayList<Long> cycleTime = TCPClient.getCycleTimeList();
		List<Stats> statList = new ArrayList<Stats>();
		int index = 0;
		int i = 1;
		
		while(i <= numLines) {
			Stats stat = new Stats(i, cycleTime.get(index));
			statList.add(stat);
			i++;
			index++;
		}
		
		FileWriter fw = null;
		
		try{
			fw = new FileWriter(fileName);
			
			// Write the CSV file header
			fw.append(FILE_HEADER.toString());
			fw.append(NEW_LINE_SEPARATOR);
			for (Stats stat : statList) {
				fw.append(String.valueOf(stat.getId()));
				fw.append(COMMA_DELIMITER);
				fw.append(String.valueOf(stat.getCycleTime()));
				fw.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("CSV file was successfully created.");
		} 
		catch (Exception e) {
			System.out.println("Error in CSVFileWriter");
			e.printStackTrace();
		} finally {
			try {
				fw.flush();
				fw.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter");
				e.printStackTrace();
			}
		}
	}
}