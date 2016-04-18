package featureExtract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Sample Java program to read and write Excel //file in Java using Apache POI
 *
 */
public class SoilReaderWriter {

	public static void main(String[] args) {

		try {
			File excel = new File(
					"E://Akash/java/Soil/FeatureExtract/LouhojongDhanV2Input.xlsx");
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);

			Vector<String> area = new Vector<String>();
			Set<String> type = new HashSet<String>();
			Map<String, Set<String>> map = new HashMap<String, Set<String>>();

			Iterator<Row> itr = sheet.iterator();
			int count = 0;
			// Iterating over Excel file in Java
			Row row = itr.next();

			while (itr.hasNext()) {
				row = itr.next();
				// System.out.print(++count);
				if(!row.equals(null)){
					// Iterating over each column of Excel file
					Iterator<Cell> cellIterator = row.cellIterator();
					Cell cell = cellIterator.next();
					//if(cell.getStringCellValue()!=null)
						System.out.println(area.size());
						area.add(cell.getStringCellValue());
					// System.out.println(cell.getStringCellValue());
					Set<String> tmp = new HashSet<String>();
					while (cellIterator.hasNext()) {

						cell = cellIterator.next();
						type.add(cell.getStringCellValue());
						tmp.add(cell.getStringCellValue());

					}
					map.put(area.lastElement(), tmp);
				}
				
				
			}
			
			// System.out.println(map.size());

			// for (Map.Entry<String, Set<String>> i : map.entrySet()) {
			// System.out.println(i.getKey());
			//
			// Set<String> temp = i.getValue();
			// // temp.forEach(System.out::println);
			//
			// for (String str : temp) {
			// System.out.print(str + " ");
			// }
			// System.out.println("\n");
			// }
			
			fis.close();
			
//			for (Map.Entry<String, Set<String>> i : map.entrySet()) {
//				System.out.println(i.getKey());
//
//				Set<String> temp = i.getValue();
//				// temp.forEach(System.out::println);
//
//				for (String str : temp) {
//					System.out.print(str + " ");
//					
//				}
//				System.out.println("\n");
//			}
			
			for(int i =0;i<area.size();i++)
				System.out.println(i +" "+area.get(i));
			
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet4 = workbook.createSheet("Samplesheet");
			
			Row r = sheet4.createRow(0);
			int cellnum=0;
			Cell ce1 = r.createCell(cellnum++);
			ce1.setCellValue("Area");
			for(String str : type){
				ce1 = r.createCell(cellnum++);
				ce1.setCellValue(str);
				//sheet.autoSizeColumn(50);
			}
			
			for(int i=0;i<area.size();i++){
				r = sheet4.createRow(i+1);
				cellnum = 0;
				ce1 = r.createCell(cellnum++);
				ce1.setCellValue(area.get(i));
				Set<String> compare = map.get(area.get(i));
				//compare.forEach(System.out::println);
				
				for(String str : type){
					if(compare.contains(str)){
						ce1 =r.createCell(cellnum++);
						ce1.setCellValue(1);
					}
					else{
						ce1 =r.createCell(cellnum++);
						ce1.setCellValue(0);
					}
				}
				System.out.println();
			}
			
			
			FileOutputStream out = 
		            new FileOutputStream(new File("E://Akash/java/Soil/FeatureExtract/LouhojongDhanV2Output.xlsx"));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
			
		    
		    
		    book.close();
			fis.close();
			

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
}
