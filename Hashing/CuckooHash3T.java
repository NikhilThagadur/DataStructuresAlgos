

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CuckooHash3T {
	
	private int tableOneSize;
	private int tableTwoSize;
	private int tableThreeSize;
	private long arrhashTable1[]; 
	private long arrhashTable2[];
	private long arrhashTable3[];
	private static int failCount=0;
	private static int currentsize;
	private int prevUseTable =1;
	
	public CuckooHash3T() {
		tableOneSize = 15485863;
		tableTwoSize =15483491;
		tableThreeSize = 15482153;
		//tableOneSize = 11;
		//tableTwoSize =13;
		
		arrhashTable1 = new long[15485863];
		arrhashTable2 = new long[15483491];
		arrhashTable3 = new long[15482153];
		for(int i=0; i<tableOneSize; i++){
			arrhashTable1[i]=-1;
		}
		for(int i=0; i<tableTwoSize; i++){
			arrhashTable2[i]=-1;
		}
		for(int i=0; i<tableThreeSize; i++){
			arrhashTable3[i]=-1;
		}
		currentsize =0;
	}
	
	public void insertOuter(long value){
		int key  = hashFunctionOne(value);
		prevUseTable = 1;
		//System.out.println(key);
		String temp = insertInner(key, arrhashTable1, value);
		while(!temp.equalsIgnoreCase("filled")){
			if(prevUseTable==1){
				temp = insertInner(hashFunctionTwo(Long.parseLong(temp)), arrhashTable2, Long.parseLong(temp));
				prevUseTable=2;
			}
			else if(prevUseTable==2){
				temp = insertInner(hashFunctionThree(Long.parseLong(temp)), arrhashTable3, Long.parseLong(temp));
				prevUseTable=3;
			}
			else if(prevUseTable==3){
				temp = insertInner(hashFunctionOne(Long.parseLong(temp)), arrhashTable1, Long.parseLong(temp));
				prevUseTable=1;
			}
		}
	}
	public String insertInner(int key, long [] table,long value){
		String result = "" ;
		if(table[key]==-1){
			table[key]=value;
			result = "filled";
//			if(table==arrhashTable1){
//				System.out.println(value + " is added in Table 1 at location " + key);
//			}
//			else{
//				System.out.println(value + " is added in Table 2 at location " + key);
//			}
		}
		else{
			result = String.valueOf(table[key]);
			table[key]=value;
//			if(table==arrhashTable1){
//				System.out.println(value + " is added in Table 1 at location " + key + " and " + result + " is poped out");
//			}
//			else{
//				System.out.println(value + " is added in Table 2 at location " + key + " and " + result + " is poped out");
//			}
			
		}		
		return result;
	}
	public void Find(long value){
		int key1 = hashFunctionOne(value);
		int key2 = hashFunctionTwo(value);
		int key3 = hashFunctionThree(value);
		if(arrhashTable1[key1] ==value){
//			System.out.println(value + " is found in Table 1 at location " + key1);
		}
		else if(arrhashTable2[key2] ==value){
//			System.out.println(value + " is found in Table 2 at location " + key2);
		}
		else if(arrhashTable2[key3] ==value){
//			System.out.println(value + " is found in Table 2 at location " + key2);
		}
		else{
			failCount++;
//			System.out.println(value + " is not found");
		}
		
		
	}
	
	public void Remove(long value){
		int key1 = hashFunctionOne(value);
		int key2 = hashFunctionTwo(value);	
		int key3 = hashFunctionThree(value);
		if(arrhashTable1[key1] ==value){
			arrhashTable1[key1] =-1;
//			System.out.println(value + " is deleted from Table 1 at location " + key1);
		}
		else if(arrhashTable2[key2] ==value){
			arrhashTable2[key2] = -1;
//			System.out.println(value + " is deleted from Table 2 at location " + key2);
		}
		else if(arrhashTable3[key3] ==value){
			arrhashTable3[key3] = -1;
//			System.out.println(value + " is deleted from Table 2 at location " + key2);
		}
		else{
//			System.out.println(value + " is not found");
		}
	}

	public int hashFunctionOne(long value){
		//System.out.println(Integer.parseInt(value%tableOneSize));
		return (int) (value%tableOneSize);
	}
	public int hashFunctionTwo(long value){
		return (int)(value%tableTwoSize);
	}
	public int hashFunctionThree(long value){
		return (int)(value%tableThreeSize);
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException{
		CuckooHash3T ch = new CuckooHash3T();
//		
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Cuckoo Hashing 3 Tables Implementation");
        System.out.println("Enter file path..");        
		String filepath = in.readLine();
//		String filepath = "C:\\Sumitin1.txt";
		BufferedReader input = new BufferedReader (new FileReader(filepath));
		String s;
		long startTime = System.currentTimeMillis();
		while((s=input.readLine())!=null)
		{
			if(s.startsWith("Insert"))
			{
				long data;
				String s1;
				int i=7;
				while(s.charAt(i)!=' ')
				{
					i++;
				}
				s1=s.substring(7, i++);
				data=Long.parseLong(s1, 10);
				ch.insertOuter(data);
			}
			else if(s.startsWith("Remove "))
			{
				long data;
				String s1;
				int i=7;
				s1=s.substring(i, s.length());
				data=Long.parseLong(s1, 10);
				ch.Remove(data);
			}
			else if(s.startsWith("Find "))
			{
				long data;
				String s1;
				int i=5;
				s1=s.substring(i, s.length());
				data=Long.parseLong(s1, 10);
				ch.Find(data);
			}
		}
		//theHashTable.displayTable();
		System.out.println("Cuckoo hash: Fail Count = " +  failCount);
//		System.out.println(currentsize);
		long endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");

	}
}
