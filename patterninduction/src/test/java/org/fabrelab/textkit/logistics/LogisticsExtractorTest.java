package org.fabrelab.textkit.logistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.textbreaker.util.TextLoader;
import org.fabrelab.textkit.model.ExtractResult;
import org.fabrelab.textkit.rules.DEPPON;

public class LogisticsExtractorTest {
	public static void main(String[] args) throws Exception {

		InputStream is = LogisticsExtractorTest.class.getResourceAsStream("deppon.txt");
		List<String> originalLines = TextLoader.loadText(is, "uft-8");
		is.close();
		int errorCount = 0;
		List<String> errorlines = new ArrayList<String>();
		
		try {
			File csvFile = new File("extract_" + System.currentTimeMillis() + ".csv");
			PrintWriter csvout = new PrintWriter(new BufferedWriter(new FileWriter(csvFile)), true);
			for (String line : originalLines) {
				if(StringUtils.isNotBlank(line)){
					ExtractResult result = DEPPON.extract(line);
					if(result.isSuccess()){
						System.out.println(toCsvString(line, result));
						csvout.println(toCsvString(line, result));
					}else{
						System.err.println(toCsvString(line, result));
						csvout.println(toCsvString(line, result));
						errorlines.add(line);
						errorCount++;
					}
				}
			}
			csvout.flush();
			csvout.close();
			System.out.println("Saving as CSV file ... DONE! fileName: " + csvFile.getName());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		System.err.println("errorCount: " +errorCount);
		for (String errorline : errorlines) {
			System.err.println(errorline);
		}
	}

	private static String toCsvString(String line, ExtractResult result) {
		line = line.replaceAll(",", "，");
		return line + "," +result.getAddress()+","+result.getStatus();
	}

}
