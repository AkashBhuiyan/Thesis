package kmean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import featureExtract.SoilReaderWriter;

public class Kmeannn {

	public static void main(String[] args) {

		try {

			double EPS = Math.pow(10.00, -2.00);
			File excel = new File(
					"E://Akash/java/Soil/FeatureExtract/LouhojongDhanV2OutputV2.xlsx");
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);

			Vector<String> area = new Vector<String>();
			Vector<String> type = new Vector<String>();
			Vector<String> clus = new Vector<String>();
			Vector<String> clusterCode = new Vector<String>();

			Vector<Vector<Double>> objects = new Vector<Vector<Double>>();
			Vector<Vector<Double>> cluster = new Vector<Vector<Double>>();
			Vector<Vector<Double>> obj = new Vector<Vector<Double>>();

			Iterator<Row> itr = sheet.iterator();

			Scanner scanner = new Scanner(System.in);

			System.out.println("How many cluster? :");
			int numCluster = scanner.nextInt();

			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell cell = cellIterator.next();
			
			while (cellIterator.hasNext()) {

				cell = cellIterator.next();
				// System.out.println(cell.getStringCellValue());
				type.add(cell.getStringCellValue());

			}

			for (int i = 0; i < type.size(); i++) {
				System.out.println(i + " - > " + type.get(i));
			}

			while (itr.hasNext()) {
				row = itr.next();
				Vector<Double> allRowObj = new Vector<Double>();
				if (!row.equals(null)) {
					// Iterating over each column of Excel file
					cellIterator = row.cellIterator();
					cell = cellIterator.next();
					area.add(cell.getStringCellValue());
					clus.add(cell.getStringCellValue());
					System.out.println(cell.getStringCellValue());
					while (cellIterator.hasNext()) {

						cell = cellIterator.next();
						allRowObj.add(cell.getNumericCellValue());

					}
					objects.add(allRowObj);
					obj.add(allRowObj);
					// allRowObj.clear();

				}

			}

			// System.out.println(objects.size());
			// for (int i = 0; i < objects.size(); i++) {
			//
			// System.out.print(objects.get(i).size());
			// for (int j = 0; j < objects.firstElement().size(); j++) {
			// Double output1 = (Double) ((Double) objects.get(i).get(j));
			//
			// System.out.print(output1+" ");
			// }
			// System.out.println();
			// }

			for (int i = 0; i < numCluster; i++) {
				Random rand = new Random();

				int position = rand.nextInt(obj.size()) % obj.size();
				// System.out.println(position);

				cluster.add(obj.get(position));
				clusterCode.add(clus.get(position));

				// for (int k = 0; k < cluster.get(i).size(); k++){
				// double chk=cluster.get(i).get(k);
				// System.out.print( chk+ " ");
				// }
				// System.out.println();
				// for (int l = 0; l < obj.get(position).size(); l++){
				// double chk=obj.get(position).get(l);
				// System.out.print(chk + " ");
				// }
				obj.remove(position);
				clus.remove(position);
				System.out.println();
				System.out.println();

			}

			// System.out.println("object size : " + obj.size());
			// System.out.println(cluster.size());

			for (int i = 0; i < cluster.size(); i++) {
				for (int j = 0; j < cluster.firstElement().size(); j++) {
					Double output1 = ((Double) cluster.get(i).get(j));

					// System.out.print(output1 + " ");
				}
				// System.out.println();
			}

			int sizeOfObject = (int) objects.size() + 2;
			// System.out.println(sizeOfObject);
			int[] minClasses = new int[sizeOfObject];
			Arrays.fill(minClasses, 0);
			boolean flag = true;
			int loop = 0;

			do {
				++loop;
				for (int i = 0; i < objects.size(); i++) {
					double mn = 0x7f7f7f7f;
					int minClass = 0;
					for (int j = 0; j < cluster.size(); j++) {
						double dis = 0.000;
						int l = 0;
						for (int k = 0; k < cluster.firstElement().size(); k++) {

							dis = dis
									+ distance(objects.get(i).get(k), cluster
											.get(j).get(k));
							l++;
							// System.out.println(dis);
						}
						dis = Math.sqrt(dis);
						// System.out.println(" " + dis);
						if (mn > dis) {
							mn = dis;
							minClass = j + 1;
							// System.out.println("minClass " + minClass);
						}

					}
					minClasses[i] = minClass;
					// System.out.println("minClasses : " + minClasses[i]);
				}
				// System.out.println(minClasses.length);
				int[] classCounterSize = new int[numCluster + 5];
				Arrays.fill(classCounterSize, 0);

				for (int i = 0; i < objects.size(); i++) {
					// if(minClasses[i]>){
					//
					// }
					classCounterSize[minClasses[i]]++;

				}

				// for (int i = 0; i < numCluster; i++) {
				// System.out.println("classCounterSize : " + " "
				// + classCounterSize[i]);
				// }
				// Vector<Vector<Double>> updateCluster = new
				// Vector<Vector<Double>>();

				Double[][] tempArray = new Double[numCluster + 5][cluster
						.firstElement().size()];
				// Arrays.fill(tempArray, 0);
				// Arrays.fill(tempArray, 0, tempArray.length, 0);

				for (int demoRow = 0; demoRow < tempArray.length; demoRow++) {
					Arrays.fill(tempArray[demoRow], 0.0);
				}

				// System.out.println("object : " + objects.size());

				for (int i = 0; i < objects.size(); i++) {
					for (int j = 0; j < cluster.firstElement().size(); j++) {
						double storeAddition = objects.get(i).get(j);
						tempArray[minClasses[i]][j] += storeAddition;
						// System.out.print(tempArray[minClasses[i]][j] + " ");
					}
					// System.out.println("ROW : "+i);
				}

				// System.out.println("YES");

				for (int i = 1; i <= numCluster; i++) {
					for (int j = 0; j < cluster.firstElement().size(); j++) {
						tempArray[i][j] /= classCounterSize[i];
					}
				}

				boolean Continue = true;

				for (int i = 0; i < cluster.size() && Continue; i++) {
					double dis = 0.00;
					for (int j = 0; j < cluster.get(i).size(); j++) {
						dis = dis
								+ distance(cluster.get(i).get(j),
										tempArray[i + 1][j]);
					}

					dis = Math.sqrt(dis);
					if (dis <= EPS) {
						Continue = false;
					}
				}
				if (Continue) {
					flag = false;
				}

				// Vector<Vector<Double>> temp = new
				// Vector<Vector<Double>>(Arrays.asList(tempArray));

				else {
					for (int i = 0; i < cluster.size(); i++) {
						Vector<Double> b = new Vector<Double>();
						for (int j = 0; j < cluster.get(i).size(); j++) {
							double a = tempArray[i][j];

							b.add(a);

						}
						cluster.set(i, b);
					}
				}

			} while (flag);

			for (int i = 0; i < area.size(); i++) {
				System.out.print(area.get(i) + "\t\t");
				System.out.print(minClasses[i] + "\t\t");
				System.out.println("\t\t" + clusterCode.get(minClasses[i] - 1));
			}
			fis.close();

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet4 = workbook.createSheet("Samplesheet");

			Row r = sheet4.createRow(0);
			int cellnum = 0;
			Cell ce1 = r.createCell(cellnum++);
			ce1.setCellValue("Area");

			for (String str : type) {
				ce1 = r.createCell(cellnum++);
				ce1.setCellValue(str);
				// sheet.autoSizeColumn(50);
			}
			ce1 = r.createCell(cellnum++);
			ce1.setCellValue("Cluster Number");
			
			ce1 = r.createCell(cellnum++);
			ce1.setCellValue("Cluster Name");

			for (int i = 0; i < area.size(); i++) {
				r = sheet4.createRow(i + 1);
				cellnum = 0;
				ce1 = r.createCell(cellnum++);
				ce1.setCellValue(area.get(i));

				for (int j = 0; j < objects.firstElement().size(); j++) {
					ce1 = r.createCell(cellnum++);
					ce1.setCellValue(objects.get(i).get(j));
				}
				ce1 = r.createCell(cellnum++);
				ce1.setCellValue(minClasses[i]);
				
				ce1 = r.createCell(cellnum++);
				ce1.setCellValue(clusterCode.get(minClasses[i] - 1));

			}

			// Row row1=itr1.next();
			// int cellnum = objects.firstElement().size()+1;
			// Cell ce1 = row1.createCell(cellnum);
			// ce1.setCellValue("Cluster Number");
			// int i =0;
			// while (itr1.hasNext()) {
			//
			// System.out.println(i++
			// +"**********************************************");
			// row1 = itr1.next();
			// row1.createCell(cellnum);
			// ce1.setCellValue(1);
			//
			// }
			fis.close();

			FileOutputStream output_file = new FileOutputStream(new File(
					"E://Akash/java/Soil/FeatureExtract/LouhojongDhanV2ClusterOutputV2.xlsx"));

			workbook.write(output_file);
			output_file.close();
			book.close();

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}

	static double distance(double x1, double x2) {

		return ((x1 - x2) * (x1 - x2));
	}
}
