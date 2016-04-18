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
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import featureExtract.SoilReaderWriter;

public class Kmean {

	public static void main(String[] args) {

		try {

			double EPS = Math.pow(10.00, -3.00);
			File excel = new File(
					"E://Akash/java/Soil/FeatureExtract/value.xlsx");
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);

			Vector<String> area = new Vector<String>();
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

			while (itr.hasNext()) {
				row = itr.next();
				Vector<Double> allRowObj = new Vector<Double>();
				if (!row.equals(null)) {
					// Iterating over each column of Excel file
					Iterator<Cell> cellIterator = row.cellIterator();
					Cell cell = cellIterator.next();
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
			fis.close();
			book.close();
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

			for (int i = 1; i <= numCluster; i++) {
				Random rand = new Random();

				int position = rand.nextInt(obj.size()) % obj.size();
				System.out.println(position);
				cluster.add(obj.get(position));
				clusterCode.add(clus.get(position));
//				for (int k = 0; k < cluster.get(i).size(); k++)
//					System.out.println(cluster.get(i).get(k) + " ");
//				for (int k = 0; k < obj.get(position).size(); k++)
//					System.out.println(obj.get(position).get(k) + " ");
				obj.remove(position);
				clus.remove(position);

			}

			System.out.println("object size : "+obj.size());
			// System.out.println(cluster.size());

			for (int i = 0; i < cluster.size(); i++) {
				for (int j = 0; j < cluster.firstElement().size(); j++) {
					Double output1 = ((Double) cluster.get(i).get(j));

					System.out.print(output1 + " ");
				}
				System.out.println();
			}

			int sizeOfObject = (int) objects.size() + 2;
			System.out.println(sizeOfObject);
			int[] minClasses = new int[sizeOfObject];
			Arrays.fill(minClasses, 0);
			boolean flag = true;
			int loop = 0;

			do {
				++loop;
				for (int i = 0; i < objects.size(); i++) {
					double mn = 0x7f7f7f7f;
					int minClass = 0;
					for (int j = 0; i < cluster.size(); j++) {
						double dis = 0.000;
						int l = 0;
						for (int k = 0; k < cluster.firstElement().size(); k++) {

							dis = dis
									+ distance(objects.get(i).get(k), cluster
											.get(j).get(k));
							l++;
							System.out.println(dis);
						}
						dis = Math.sqrt(dis);
						System.out.println(" "+dis);
						if (mn > dis) {
							mn = dis;
							minClass = j + 1;
							System.out.println("minClass "+minClass);
						}

					}
					minClasses[i] = minClass;
					System.out.println(minClasses[i]);
				}

				int[] classCounterSize = new int[numCluster + 5];
				Arrays.fill(classCounterSize, 0);

				for (int i = 0; i < objects.size(); i++) {
					classCounterSize[minClasses[i]]++;
					System.out.println(classCounterSize[i]);
				}
				//Vector<Vector<Double>> updateCluster = new Vector<Vector<Double>>();

				Double[][] tempArray = new Double[numCluster + 5][cluster
						.firstElement().size()];
				Arrays.fill(tempArray, 0);

				for (int i = 0; i < objects.size(); i++) {
					for (int j = 0; j < cluster.firstElement().size(); j++) {
						tempArray[minClasses[i]][j] += objects.get(i).get(j);
					}
				}

				for (int i = 0; i < objects.size(); i++) {
					for (int j = 0; j < cluster.firstElement().size(); j++) {
						tempArray[i][j] /= classCounterSize[i];
					}
				}

				boolean Continue = true;

				for (int i = 0; i < cluster.size() && Continue; i++) {
					double dis = 0.00;
					for (int j = 0; j < cluster.get(i).size(); j++) {
						dis = dis
								+ distance(objects.get(i).get(j),
										cluster.get(i + 1).get(j));
					}
					dis = Math.sqrt(dis);
					if (dis > EPS) {
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
				System.out.print(area.get(i)+" ");
				System.out.print(minClasses[i]+" ");
				System.out.print(clusterCode.get(minClasses[i]-1));
			}
			
			/*HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet4 = workbook.createSheet("Samplesheet");
			
			Row r = sheet4.createRow(0);
			int cellnum=0;
			Cell ce1 = r.createCell(cellnum++);
			ce1.setCellValue("Area");
	*/
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
