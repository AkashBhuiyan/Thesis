package treeSearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.text.StyledEditorKit.ItalicAction;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow.CellIterator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private String path;
	private ArrayList<InputClass> inputClassObj = new ArrayList<InputClass>();

	public ExcelReader() {

	}

	public ExcelReader(String pathFromMainClass) {
		path = pathFromMainClass;
		SetXSSFWorkBook();
		System.out.println("from constructor: " + inputClassObj.size());
	}



	public void SetXSSFWorkBook() {

		try {
			File excel = new File(path);
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();

			ArrayList<String> type = new ArrayList<String>();
			Scanner scanner = new Scanner(System.in);
			ArrayList<String> area = new ArrayList<String>();
			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell cell = cellIterator.next();

			while (cellIterator.hasNext()) {

				cell = cellIterator.next();
				// System.out.println(cell.getStringCellValue());
				if (cellIterator.hasNext()) {
					type.add(cell.getStringCellValue());
					// System.out.println(cell.getStringCellValue());
				}

			}
			// System.out.println(type.size());

			// for (int i = 0; i < type.size(); i++) {
			// System.out.println(i + " - > " + type.get(i));
			// }

			while (itr.hasNext()) {
				row = itr.next();
				// Vector<Double> allRowObj = new Vector<Double>();

				if (!row.equals(null)) {
					InputClass inputObject = new InputClass();
					// Iterating over each column of Excel file
					cellIterator = row.cellIterator();
					cell = cellIterator.next();
					// area.add(cell.getStringCellValue());
					inputObject.setArea(cell.getStringCellValue());
					Map<String, Integer> demoMap = new HashMap<String, Integer>();

					// System.out.println(cell.getStringCellValue());
					// while (cellIterator.hasNext()) {
					//
					// cell = cellIterator.next();
					// allRowObj.add(cell.getNumericCellValue());
					//
					//
					// }
					for (int i = 0; i < type.size(); i++) {
						cell = cellIterator.next();
						int temp = (int) cell.getNumericCellValue();
						demoMap.put(type.get(i), temp);
					}
					// for (Map.Entry<String, Integer> itrr :
					// demoMap.entrySet()) {
					// System.out.print(itrr.getKey() + " ");
					// System.out.print(itrr.getValue() + " ");
					//
					// }
					// System.out.print("\n\n");
					inputObject.setFeatures(demoMap);
					cell = cellIterator.next();
					int CustingCellValeToTemp = (int) cell
							.getNumericCellValue();
					inputObject.setClusterNumber(CustingCellValeToTemp);
					// objects.add(allRowObj);
					// demoVector.add(allRowObj);

					// System.out.println(allRowObj);
					// allRowObj.clear();
					this.inputClassObj.add(inputObject);
				}

			}
			System.out.println(inputClassObj.size());

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();

		}

	}

	public ArrayList<InputClass> getInputClass() {
		System.out.println("ExcelFileReader :" + inputClassObj.size());
		return inputClassObj;
	}
}