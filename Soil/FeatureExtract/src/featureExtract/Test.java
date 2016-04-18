package featureExtract;
import java.io.*;

public class Test {

	public static void main(String[] args) throws IOException{
		
		//FileInputStream in = null;
		//FileOutputStream out = null;
		
		FileReader in = null;
	     FileWriter out = null;
		
		try{
			//in = new FileInputStream("Book.xlsx");
			//out = new FileOutputStream("out.xlsx");
			in = new FileReader("in.txt");
	        out = new FileWriter("out.txt");
			
			int col;
			while((col=in.read())!=-1){
				out.write(col);
			}
			
		}finally{
			
			if(in !=null){
				in.close();
			}
			if(out != null){
				out.close();
			}
			System.out.println("Successfully!");
		}
		

	}

}
