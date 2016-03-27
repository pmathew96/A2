/**
 * Created by Paul on 3/26/2016.
 */
import java.io.*;
import java.util.StringTokenizer;
import java.lang.Integer;

public class Simulator {
	/*Method that accepts a file as input and prints 
	the statistics of that file, i.e., the fanout of every
	gate present in the file
	*/
    public static void printingFileStatistics (File f) throws java.io.IOException{
        int gatenumber, num, i=0, totgates =0;
		FileReader netlistfile = null;
		LineNumberReader line = null;
        netlistfile = new FileReader(f);
        line = new LineNumberReader(netlistfile);
        String str, token;
        int[] gatecounter = new int[100];
		//loop to read lines from file
        while((str = line.readLine()) != null){
            StringTokenizer st = new StringTokenizer(str);
            gatenumber = Integer.parseInt(st.nextToken());
			//loop to read tokens in tokenizer
			while(st.hasMoreTokens()){
				token = st.nextToken();
				char[] c = token.toCharArray();
				if(Character.isDigit(c[0])){
					num = Integer.parseInt(token);
					gatecounter[num]++;
				}
				i++;
			}
			totgates++;
        }
		
		for(i = 0; i < totgates; i++){
			System.out.println(i+" "+gatecounter[i]);	
		}
		
		line.close();
		netlistfile.close();
    }
	//Main method
    public static void main(String[] args)throws java.io.IOException{
        File inputfile = new File(args[0]);
        printingFileStatistics(inputfile);
    }
}
