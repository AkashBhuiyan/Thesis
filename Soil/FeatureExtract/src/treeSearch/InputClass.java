package treeSearch;

import java.util.HashMap;
import java.util.Map;

public class InputClass {
	private String Area;
	private Map<String,Integer> Features = new HashMap<String, Integer>();
	private int ClusterNumber;
	
	public InputClass(){
		
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public Map<String, Integer> getFeatures() {
		return Features;
	}

	public void setFeatures(Map<String, Integer> features) 
	{
		Features = features;
	}

	public int getClusterNumber() {
		return ClusterNumber;
	}

	public void setClusterNumber(int clusterNumber) {
		ClusterNumber = clusterNumber;
	}
	
}
