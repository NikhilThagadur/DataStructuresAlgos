
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Sumit{

	private static int[] numbers;
	private static  int[] Aux;
	int number;
	String lastVisited;
	int mergeCounter=0;
	boolean blnAux;
	static boolean blnABAlter = false;
	static boolean blnDynaArrayMerge = false;
	static long startTime;
	static long endTime;
	
	public void DisplayArray(int[] arrDummy) throws IOException{
		//System.out.println("Wait...creating output file");
		//StringBuilder strResult=new StringBuilder();
		
		//for(int i=0;i<arrDummy.length;i++){
		//	strResult = strResult.append( " " + arrDummy[i]);			
		//}		
		//PrintWriter writer = new PrintWriter("D:\\storeReport.txt", "UTF-8");
		//String strFile ="D:\\Sumit_Output.txt";
		//FileWriter fstream = new FileWriter(strFile);
		//BufferedWriter out = new BufferedWriter(fstream);
		//out.write(strResult.toString());
		///out.close();
		//arrDummy = null;	
		//System.out.println(strResult.toString());
		//System.out.println("Output is stored at " + strFile);
	}
	
	public void sort(int [] values) throws IOException{
		this.numbers = values;
		number = values.length;
		this.Aux = new int[number];
		//DisplayArray(numbers);	
		//Aux = numbers;
//		for(int a = 0;a<numbers.length;a++){
//			Aux[a]=numbers[a];
//		}
		startTime = System.currentTimeMillis();
		if (blnABAlter){
			mergeSortAB(numbers,Aux,0, number-1);
		}
			
		else{
			mergeSort(0, number-1);
			//System.out.println("asdasdad");
			}
		System.out.println("ARRAY SORTED");		
		endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");		
		DisplayArray(numbers);
		numbers=null;
		Aux=null;
	}
	
	private void mergeSortAB(int [] main, int[] Aux, int low, int high) throws IOException{
		if(low<high){
			int middle = low+(high-low)/2;
			mergeSortAB(Aux,main,low, middle);
			mergeSortAB(Aux, main,middle+1, high);
			mergingABAlter(main,Aux,low, middle, high);
		}
	}
	private void mergeSort(int low, int high) throws IOException{
		if(low<high){
			int middle = low+(high-low)/2;
			mergeSort(low, middle);
			mergeSort(middle+1, high);
			if (blnDynaArrayMerge==false) {
				merging(low, middle, high);
				//System.out.println("AAAAA");
			}
			else
				mergingDynaArray(low, middle, high);
		}
	}
	public void merging(int low, int middle, int high) throws IOException{
		
		for(int i =low;i<=high;i++){
			Aux[i] = numbers[i]; 
		}
		for(int i =low;i<=middle;i++){
			Aux[i] = numbers[i]; 
		}
		int i = low;
		int j = middle+1;
		int k = low;		
		while(i<=middle && j<=high ){			
			if (Aux[i]<=Aux[j]){
				numbers[k]= Aux[i];
				i++;
			}
			else{
				numbers[k]= Aux[j];
				j++;
			}
			k++;
		}
		while(i<=middle){
			numbers[k]=Aux[i];
			k++;
			i++;
		}
		/*while(j<=high){
			numbers[k]=Aux[j];
			k++;
			j++;
		}*/
		//DisplayArray(numbers);
				
	}
	public void mergingABAlter(int[] A, int[] B,int low, int middle, int high) throws IOException{
		
						
		int i = low;
		int j = middle+1;
		int k = low;		
		while(i<=middle && j<=high ){	
			//System.out.println("Compared : " + B[i] + " and " + B[j] );
			if (B[i]<=B[j]){
				A[k]= B[i];
				i++;
			}
			else{
				A[k]= B[j];
				j++;
			}					
			k++;
		}
		while(i<=middle){
			A[k]=B[i];
			k++;
			i++;			
		}
		while(j<=high){
			A[k]=B[j];
			k++;
			j++;			
		}
		//DisplayArray(numbers);
		//DisplayArray(Aux);
				
	}
public void mergingDynaArray(int low, int middle, int high) throws IOException{
		
	//System.out.println(low + "  " + middle + "  " + high);
		int [] A = new int[middle-low+1];
		int [] B = new int[high-middle];
		int ALen=0;
		int BLen =0;
		for(int i =low;i<=middle;i++){
			A[ALen] = numbers[i]; 
			ALen++;
		}
		//System.out.println(A.length + "  " + B.length);
		for(int i =middle+1;i<=high;i++){
			B[BLen] = numbers[i];
			BLen++;
		}
		
		int i = 0;
		int j = 0;
		int k = low;		
		while(i<=ALen-1 && j<=BLen-1 ){			
			if (A[i]<=B[j]){
				numbers[k]= A[i];
				i++;
			}
			else{
				numbers[k]= B[j];
				j++;
			}
			k++;
		}
		while(i<=ALen-1){
			numbers[k]=A[i];
			k++;
			i++;
		}
//	    while(j<=high){
//			numbers[k]=Aux[j];
//			k++;
//			j++;
//		}
//		
				
	}
	public int[] getArray(int intSize){
		int temp;
		int [] arrA = new int[intSize];
		
		for(int i = 0; i<arrA.length;i++){
			arrA[i]=1 + (int)(Math.random()*intSize);
		}
		return arrA;
	}
	public static int getInput() throws IOException{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		String s = br.readLine();		
		return Integer.parseInt(s);		
	}
	
	public static void displayChoices(){
		System.out.println("Enter Your Choice");
		System.out.println("1. Merger Sort Using Dynamic memory Allocation");
		System.out.println("2. Merger Sort Using Auxillary Array with Copying Data");
		System.out.println("3. Merger Sort Using Alternate Merging between Primary Array and Auxillary Array");
		System.out.println("4. Exit");
	}
	
	public static void main(String args[]) throws IOException{
		Sumit m = new Sumit ();
		int[] arrC;
		displayChoices();
		int choice =getInput();
		
		
		
		
		while(choice!=4){
			switch(choice){
			case 1: 
				blnDynaArrayMerge=true;
				blnABAlter = false;
				System.out.println("Enter the Array Size :");		
				arrC = m.getArray(getInput());
				m.sort(arrC);				
				displayChoices();
				choice =getInput();
				
				
				break;
			case 2:
				blnDynaArrayMerge=false;
				blnABAlter = false;
				System.out.println("Enter the Array Size :");		
				arrC = m.getArray(getInput());
				m.sort(arrC);				
				displayChoices();
				choice =getInput();
				
				
				break;
			case 3:
				blnDynaArrayMerge=false;
				blnABAlter = true;
				System.out.println("Enter the Array Size :");		
				arrC = m.getArray(getInput());
				m.sort(arrC);				
				displayChoices();
				choice =getInput();

				
				break;
			
			default:
				System.out.println("Invalid Choice");
				displayChoices();
				choice =getInput();
				break;
			}
		}		
	}
	
}
