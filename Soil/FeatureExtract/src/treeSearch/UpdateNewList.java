package treeSearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UpdateNewList {
	ExcelReader excelReaderObj = new ExcelReader("E://Akash/java/Soil/FeatureExtract/Demo.xlsx");
	CheckZeroClass checkZeroClassObj = new CheckZeroClass();

	ArrayList<InputClass> ReaderValueObj = excelReaderObj.getInputClass();
	public UpdateNewList(){
		
	}
	Set<String> key = checkZeroClassObj.getCheckZero();
	
	Comparator< InputClass> comparator = new Comparator<InputClass>() {
		
		@Override
		public int compare(InputClass o1, InputClass o2) {
			return o1.getClusterNumber()-o2.getClusterNumber();
		}
	};



	public void UpdateList() {
		int counter =0;
		ReaderValueObj.sort(comparator);
		for (int i = 0; i < ReaderValueObj.size(); i++) {
			if (ReaderValueObj.get(i).getClusterNumber() == 1) {
				counter++;
				Map<String, Integer> tempMap = ReaderValueObj.get(i)
						.getFeatures();
				for (String temp : key) {
					tempMap.put(temp, 0);

				}
				ReaderValueObj.get(i).setFeatures(tempMap);
			}
		}
		System.out.println("cluster 1 size: "+counter);
		
		for(String tmp : key){
			System.out.print(tmp+" ");
		}
		System.out.println();
		
		for (int j = 0; j < ReaderValueObj.size(); j++) {
			for (Map.Entry<String, Integer> itrr : ReaderValueObj.get(j)
					.getFeatures().entrySet()) {
				//System.out.print(itrr.getKey() + " ");
				System.out.print(itrr.getValue() + " ");
				

			}
			System.out.print(" "+ReaderValueObj.get(j).getClusterNumber());
			System.out.println();
		}

	}
}
