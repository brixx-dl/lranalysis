/*
 * Dependencies: Apache POI Library from http://poi.apache.org/
 */
package de.tqs.excelread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReading {

	/**
	 * @param fileresults
	 * @param args
	 *            the command line arguments
	 * @param filename
	 * @return
	 * @return
	 */

	public static StringBuilder ExcelStream(String filevuser, String fileresults) {

		InputStream inpvuser = null;
		InputStream inpresults = null;
		StringBuilder ResponseString = new StringBuilder();
		ResponseString.append("Lasttest Auswertung");
		ResponseString.append("\n");

		try {

			String dir = "files//";
			String filenamevuser = dir + filevuser;
			String filenameresults = dir + fileresults;

			inpvuser = new FileInputStream(filenamevuser);
			Workbook wbVuser = WorkbookFactory.create(inpvuser);

			inpresults = new FileInputStream(filenameresults);
			Workbook wbResults = WorkbookFactory.create(inpresults);

			int[] test = vuserRead(wbVuser.getSheetAt(0));

			int panfang = 0;
			int pende = 0;
			int size = test.length - 1;
			int j = 0;
			int plateau = 0;

			do {
				plateau = j + 1;
				panfang = test[j];
				pende = test[j + 1];

				ResponseString.append(resultRead(wbResults.getSheetAt(0), plateau, panfang, pende));

				j++;
			} while (j < size);

		} catch (InvalidFormatException ex) {
			Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				inpvuser.close();
				inpresults.close();
			} catch (IOException ex) {
				Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return ResponseString;

	}

	public static int[] vuserRead(Sheet sheet) {

		Row row = null;
		TreeMap<Integer, String> vuserTM = new TreeMap<Integer, String>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			// Zeile der Arbeitsmappe
			row = sheet.getRow(i);

			Cell cell = row.getCell(0);
			Cell cell2 = row.getCell(1);

			String Key = cell.getStringCellValue();

			int intHour = Integer.parseInt(Key.substring(0, 2));
			int intMin = Integer.parseInt(Key.substring(3, 5));

			int intTime = (intHour * 60 + intMin) * 60;

			vuserTM.put(intTime, cell2.getStringCellValue());

		}
		int count = 0;
		int iter = 0;

		int[] plateauArray;
		plateauArray = new int[vuserTM.size()];

		Set<Entry<Integer, String>> set = vuserTM.entrySet();
		Iterator<Entry<Integer, String>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Integer, String> me = i.next();
			count = me.getKey();
			plateauArray[iter] = count;
			iter++;
		}

		return plateauArray;

	}

	public static String resultRead(Sheet sheet, Integer plateau, Integer panfang, Integer pende) {
		Row row = null;
		TreeMap<Integer, String> resultTM = new TreeMap<Integer, String>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			// Zeile der Arbeitsmappe
			row = sheet.getRow(i);
			// Jede Zeile einzeln wird durchgegangen

			Cell cell = row.getCell(0);
			String strKey = cell.getStringCellValue();
			String regKey = "\\.+";
			String[] resKey = strKey.split(regKey);
			strKey = resKey[0];
			strKey = strKey.replace(",", "");

			int Key = Integer.parseInt(strKey);

			List<String> listValue = new ArrayList<String>();
			Cell cell2 = row.getCell(1);

			// listValue.add(cell2.getStringCellValue());
			String strValue = cell2.getStringCellValue();
			String regValue = "\\.+";

			// System.out.println(listValue);

			String[] resValue = strValue.split(regValue);
			// System.out.println(Arrays.deepToString(resValue));

			int arrayLength = resValue.length;
			// System.out.println(arrayLength);

			if (arrayLength == 1) {

				listValue.add(resValue[0] + "000");

				// resValue[1] = "000";
			}

			if (arrayLength == 2) {
				String strValueSek = resValue[0];
				String strValueMil = resValue[1];
				Integer lengValueMil = strValueMil.length();

				switch (lengValueMil) {
				case 1:
					strValueMil = strValueMil + "00";
					break;
				case 2:
					strValueMil = strValueMil + "0";
					break;

				}
				String strValue2 = strValueSek + strValueMil;
				listValue.add(strValue2);
				// System.out.println(listValue);

			}

			String test = listValue.get(0);
			resultTM.put(Key, test);

		}

		SortedMap<Integer, String> sortedMap = resultTM.subMap(panfang, pende);

		Collection<String> values = sortedMap.values();
		ArrayList<String> val = new ArrayList<String>(values);

		DescriptiveStatistics stats = new DescriptiveStatistics();

		for (int h = 0; h < val.size(); h++) {

			String test = val.get(h);
			double value = Double.parseDouble(test);
			stats.addValue(value);

		}

		String ResultResponseString = "\n";

		ResultResponseString = ResultResponseString + "Plateau: " + plateau + '\n';
		ResultResponseString = ResultResponseString + "Antwortzeit Min: " + stats.getMin() + "\n";
		ResultResponseString = ResultResponseString + "Antwortzeit Max: " + stats.getMax() + "\n";
		ResultResponseString = ResultResponseString + "StdAbw: " + stats.getStandardDeviation() + "\n";
		ResultResponseString = ResultResponseString + "Anzahl Messungen N: " + stats.getN() + "\n";

		ResultResponseString = ResultResponseString + "Percentile 75%: " + stats.getPercentile(75) + "\n";
		ResultResponseString = ResultResponseString + "Percentile 90%: " + stats.getPercentile(90) + "\n";

		// Calculate 95% confidence interval
		double ci = calcMeanCI(stats, 0.95);
		ResultResponseString = ResultResponseString + String.format("Mean: %f", stats.getMean()) + "\n";
		ResultResponseString = ResultResponseString + String.format("Confidence Interval 95%%: %f", ci) + "\n";
		double lower = stats.getMean() - ci;
		double upper = stats.getMean() + ci;
		ResultResponseString = ResultResponseString
				+ String.format("Confidence Interval 95%% min/max: %f, %f %n", lower, upper) + "\n";

		return ResultResponseString;

	}

	private static double calcMeanCI(DescriptiveStatistics stats, double level) {
		try {

			TDistribution tDist = new TDistribution(stats.getN() - 1);

			double critVal = tDist.inverseCumulativeProbability(1.0 - (1 - level) / 2);

			return critVal * stats.getStandardDeviation() / Math.sqrt(stats.getN());
		} catch (MathIllegalArgumentException e) {
			return Double.NaN;
		}
	}

}
