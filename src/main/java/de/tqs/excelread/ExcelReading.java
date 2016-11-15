/*
 * Dependencies: Apache POI Library from http://poi.apache.org/
 */
package de.tqs.excelread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReading {

     /**
     * @param args the command line arguments
     * @param filename 
     * @return 
     * @return 
     */
	
	public static void ExcelStream(String file){
		 		
		InputStream inp = null;
        try {
           
        	String dir = "files//";
        	String file2 = file;
        	String filename = dir + file2;
			inp = new FileInputStream(filename);
            Workbook wb = WorkbookFactory.create(inp);

            for(int i=0;i<wb.getNumberOfSheets();i++) {
             //   System.out.println(wb.getSheetAt(i).getSheetName());
                echoAsCSV(wb.getSheetAt(i));
            }
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inp.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /* Orginal
    public static void echoAsCSV(Sheet sheet) {
        Row row = null;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            //Zeile der Arbeitsmappe
        	row = sheet.getRow(i);
        	//Jede Zeile einzeln wird durchgegangen
            for (int j = 0; j < row.getLastCellNum(); j++) {
            //for (int j = 0; j < 1; j++) {
                System.out.print("\"" + row.getCell(j) + "\";");
            }
            System.out.println("Test");
        }
    }
    */
    public static void echoAsCSV(Sheet sheet) {
        
    	Row row = null;
   	 	TreeMap<Integer,String> tm = new TreeMap<Integer, String>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            
                	
        	//Zeile der Arbeitsmappe
        	row = sheet.getRow(i);
           
        	Cell cell = row.getCell(0);
        	Cell cell2 = row.getCell(1);
        	
        	String Key = cell.getStringCellValue();
        	
        	
        	String strHour = Key.substring(0, 2);
        	String strMin = Key.substring(3, 5);
        	
        	int intHour = Integer.parseInt(strHour);
        	int intMin = Integer.parseInt(strMin);
        	
        	int intTime = (intHour * 60 + intMin)*60000; 
         	
          	tm.put(intTime, cell2.getStringCellValue());
            
        }
        // Liste der Eintraege
        Set set = tm.entrySet();
        
        // Erzeugen eines Iterator
        Iterator i = set.iterator();
        
        // Anzeigen aller Elemente 
        while(i.hasNext()) {
           Map.Entry me = (Map.Entry)i.next();
           System.out.print(me.getKey() + ": ");
           System.out.println(me.getValue());
        }
        System.out.println();
    }
    
	
}
