/**
 * Created by Paul on 3/26/2016.
 */
import java.io.*;
import java.util.*;
import java.lang.*;


public class Simulator {
	/*
	Method that accepts a line from the input file and the circuit file
	and simulates that one line in the circuits and prints the outputs of each 
	gate. This method calls the creatingGateArray method.
	*/
	public static void printingSimulatedCircuit(String inputstring, File circuitfile) throws java.io.IOException{
		Gate[] gates = new Gate[100];
		FileReader circuitreader = new FileReader(circuitfile);
		LineNumberReader circuitline = new LineNumberReader(circuitreader);
		String circuitstring;
		for(int i = 0;(circuitstring = circuitline.readLine()) != null; i++){
			creatingGateArray(circuitstring, inputstring, gates);
			System.out.print(gates[i].output+" ");
		}
		circuitline.close();
		circuitreader.close();
		}
	/*
	Method that accepts one line from the input file and
	returns each number as an integer. Called by the
	creatingGateArray method.
	*/
	public static int readingInputFile(String s, String inp){
		int inputnumber = Integer.parseInt(s);
		StringTokenizer st = new StringTokenizer(inp);
		String rvalue = "0";
		for(int i = 0; i < inputnumber+1; i++){
			rvalue = st.nextToken();
		}
		return Integer.parseInt(rvalue);
	}
	/*
	Method that accepts one line from the circuit file and from the 
	input file and an array of Gate objects initializes the array
	elements. This method calls the readingInputFile method.
	*/
	public static void creatingGateArray(String gatestr, String inp, Gate[] gatearray) {
		String gatename, in1 = "0", in2 = "0";
		int input1, input2;
		StringTokenizer circuittoken = new StringTokenizer(gatestr);
		int gatenumber = Integer.parseInt(circuittoken.nextToken());
		gatename = circuittoken.nextToken();
		if (gatename == "NOT") {	//since NOT gates only have 1 input
			in1 = circuittoken.nextToken();
			char[] c = in1.toCharArray();
			if (Character.isDigit(c[0])) {
				input1 = gatearray[Integer.parseInt(in1)].output;
			} else {
				input1 = readingInputFile(in1.substring(1), inp);
			}
			gatearray[gatenumber] = new Gate(input1, gatename);
		} else {
			if (circuittoken.hasMoreTokens()) {
				in1 = circuittoken.nextToken();
			}
			if (circuittoken.hasMoreTokens()) {
				in2 = circuittoken.nextToken();
			}
			char[] c1 = in1.toCharArray();
			char[] c2 = in2.toCharArray();
			if (Character.isDigit(c1[0])) {
				input1 = gatearray[Integer.parseInt(in1)].output;
			} else {
				input1 = readingInputFile(in1.substring(1), inp);
			}
			if (Character.isDigit(c2[0])) {
				input2 = gatearray[Integer.parseInt(in2)].output;
			} else {
				input2 = readingInputFile(in2.substring(1), inp);
			}
			gatearray[gatenumber] = new Gate(input1, input2, gatename);
		}
	}
	/*
	Method that accepts a file as input and prints 
	the statistics of that file, i.e., the fanout of every
	gate present in the file and the number of gates
	*/
    public static void printingFileStatistics (File circuitfile) throws java.io.IOException{
        int linenum, gatenum, i=0, totgates =0;
		FileReader netlistfile = null;
		LineNumberReader line = null;
        netlistfile = new FileReader(circuitfile);
        line = new LineNumberReader(netlistfile);
        String str, token;
        int[] gatecounter = new int[100];
		//loop to read lines from file
        while((str = line.readLine()) != null){
            StringTokenizer st = new StringTokenizer(str);
            linenum = Integer.parseInt(st.nextToken());
			//loop to read tokens in tokenizer
			while(st.hasMoreTokens()){
				token = st.nextToken();
				char[] c = token.toCharArray();
				if(Character.isDigit(c[0])){
					gatenum = Integer.parseInt(token);
					gatecounter[gatenum]++;
				}
				i++;
			}
			totgates++;
        }
		System.out.println(totgates+"\n");
		
		for(i = 0; i < totgates; i++){
			System.out.println(i+" "+gatecounter[i]);	
		}
		System.out.print("\n");
		line.close();
		netlistfile.close();
    }
  
	//Main method
    public static void main(String[] args) throws java.io.IOException{
		File circuitfile = new File(args[0]);
		File inputfile = new File(args[1]);
		FileReader inputreader = new FileReader(inputfile);
		LineNumberReader inputline = new LineNumberReader(inputreader);
		String inputstring;
		printingFileStatistics(circuitfile);
		while((inputstring = inputline.readLine()) != null){
			printingSimulatedCircuit(inputstring, circuitfile);
			System.out.print("\n");
		}
		inputline.close();
		inputreader.close();
	}
}
 /*
 Class that stores the name, inputs and output 
 of each gate.
 */
class Gate{
        int output, input1, input2;
        String name;
        void calculatingOutput(){
            switch (name){
                case "AND":
                    if((input1 & input2)==1){
                        output = 1;
                    }
                    break;
                case "NOT":
                    if(input1 == 0){
                        output = 1;
                    }
                    break;
                case "OR":
                    if((input1 | input2)==1){
                        output = 1;
                    }
                    break;
                case "NOR":
                    if((input1 & input2)==0){
                        output = 1;
                    }
                    break;
                case "NAND":
                    if((input1 | input2)==0){
                        output = 1;
                    }
                    break;
                case "XOR":
                    if(((input1 == 0) && (input2 == 1)) || ((input1 == 1) && (input2 == 0))){
                        output = 1;
                    }
                    break;
                case "XNOR":
                    if(((input1 & input2) == 1) || ((input1 & input2 ) == 0)){
                        output = 1;
                    }
                    break;
                default:
                    output = 0;
            }
        }
        Gate(int in1, int in2, String n){
            input1 = in1;
            input2 = in2;
            name = n;
            calculatingOutput();
        }

        Gate(int in, String n){
            input1 = in;
            name = n;
            calculatingOutput();
        }
    }
	
