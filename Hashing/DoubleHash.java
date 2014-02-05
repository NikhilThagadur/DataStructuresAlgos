

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DoubleHash {
	private int arraySize = 15485863;
	private long hashArray[]; 
	private static int findFail=0;
	private static int currentsize;

	DoubleHash(int size) {
		arraySize = size;
		hashArray = new long[arraySize];
		for(int i=0; i<arraySize; i++)
		{
			hashArray[i]=-1;
		}
	}

	public void displayTable() {
		System.out.print("Table: ");
		for (int j = 0; j < arraySize; j++) {
			if (hashArray[j] != -1)
				System.out.print(hashArray[j] + " ");
			else
				System.out.print("** ");
		}
		System.out.println("");
	}

	public long hashFunc1(long key) {
		return key % arraySize;
	}

	public long hashFunc2(long key) {
		return 6 - key % 6;
	}

	public void insert(long key) {
		long item = key;
		int hashVal = (int)hashFunc1(key); // hash the key
		int stepSize = (int)hashFunc2(key); // get step size
		// until empty cell or -1
		while (hashArray[hashVal] != -1 && hashArray[hashVal] != -2) {
			hashVal += stepSize; // add the step
			hashVal %= arraySize; // for wraparound
		}
		hashArray[hashVal] = item; // insert item
		currentsize++;
	}

	public long delete(long key) {
		int hashVal = (int)hashFunc1(key); 
		int stepSize = (int)hashFunc2(key); // get step size

		while (hashArray[hashVal] != -1) {
			if (hashArray[hashVal] == key) {
				long temp = hashArray[hashVal]; // save item
				hashArray[hashVal] = -2; // delete item
				currentsize--;
				return temp; // return item
			}
			hashVal += stepSize; // add the step
			hashVal %= arraySize; // for wraparound
		}
		return 0; // can't find item
	}

	public long find(long key) {
		int hashVal = (int)hashFunc1(key); // hash the key
		int stepSize = (int)hashFunc2(key); // get step size

		while (hashArray[hashVal] != -1) {
			if (hashArray[hashVal] == key)
			{
				return hashArray[hashVal]; // yes, return item
			}
			hashVal += stepSize; // add the step
			hashVal %= arraySize; // for wraparound
		}
		findFail++;
		return 0; // can't find item
	}

	public static void main(String[] args) throws IOException {
		int size=10000000;
		DoubleHash theHashTable = new DoubleHash(size);
//		
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Double Hashing Implementation");
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
				s1=s.substring(7, i);
				data=Long.parseLong(s1, 10);
				theHashTable.insert(data);
			}
			else if(s.startsWith("Remove "))
			{
				long data;
				String s1;
				int i=7;
				s1=s.substring(i, s.length());
				data=Long.parseLong(s1, 10);
				theHashTable.delete(data);
			}
			else if(s.startsWith("Find "))
			{
				long data;
				String s1;
				int i=5;
				s1=s.substring(i, s.length());
				data=Long.parseLong(s1, 10);
				theHashTable.find(data);
			}
		}
		//theHashTable.displayTable();
		System.out.println("Double hash: Fail Count = " +  findFail);
//		System.out.println(currentsize);
		long endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");


	}
}