

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;

public class ArithematicPrecision2 {
	
	private LinkStack lnksck;
	private StackPostFix lnkpost;
	private String input;
	private String output="";
	private boolean blnoperatorAdded;
	public static boolean blnNegativeNo=false;
	public static boolean blnIsPrevParan=false;
	public static boolean blnSyntaxError = false;
	public static long startTime;
	public static long endTime;
	
	public ArithematicPrecision2(String in){
		lnksck = new LinkStack();
		lnkpost = new StackPostFix();
		input = in;		
		blnoperatorAdded = false;
	}
	
	public String ConvertPrefix(){
		//input = "2+(4+5)*3";
		for(int i=0;i<input.length();i++){
			char chr = input.charAt(i);			
			switch(chr){
			case '+':
				decide(chr,1);
				break;
			case '-':
				if(blnIsPrevParan){
					blnNegativeNo = true;
				}
				if(!blnNegativeNo){
					decide(chr,1);
				}
				
				blnIsPrevParan = false;
				break;
			case '*':
				decide(chr,3);
				blnIsPrevParan = false;
				break;
			case '/':
			case '%':
				decide(chr,4);
				blnIsPrevParan = false;
				break;
			case '^':
				decide(chr,5);
				blnIsPrevParan = false;
				break;
			case 'r':
				decide(chr,6);
				blnIsPrevParan = false;
				break;
			case '(':
				lnksck.push(chr);
				blnIsPrevParan = true;
				break;
			case ')':
				closeParn(chr);
				break;
			default://                        must be number				
				
				if(blnNegativeNo){
					if (blnoperatorAdded==true){
						output = output +" N" + chr;
						blnoperatorAdded = false;
					}
					else{
						output = output +"N"+ chr;
					}
					blnNegativeNo=false;
				}
				else{
					if (blnoperatorAdded==true){
						output = output +" " + chr;
						blnoperatorAdded = false;
					}
					else{
						output = output + chr;
					}
				}				
				blnIsPrevParan = false;
				break;
			}				
		}
		
		while(!lnksck.isEmpty()){
			output = output + " " + lnksck.pop();
		}
			//System.out.println("Post Fix Equation: " + output);
		return output;
	}
	public void closeParn(char c){
		while(!lnksck.isEmpty()){
			char ct = lnksck.pop();
			if(ct=='('){
				break;
			}
			else{
				output = output + " " + ct;				
			}
		}
	}
	
	public void decide(char chr, int NewPrec){
		boolean blnInsert = true;
		while(!lnksck.isEmpty()){
			char topChr = lnksck.pop();		
			if(topChr=='('){
				lnksck.push(topChr);// if ( is poped out, push it back
				if (NewPrec=='-'){					
					blnNegativeNo = true;
					blnInsert= false;
				}
				break;
			}
			else{
				int topPrec;
				if(topChr=='-'){
					topPrec =1;
				}
				else if(topChr=='+'){
					topPrec =1;
				}
				else if(topChr=='*'){
					topPrec =3;
				}
				else if(topChr=='/' || topChr=='%'){
					topPrec =5;
				}
				else{
					topPrec =6;
				}
				if(topPrec<NewPrec){					
					lnksck.push(topChr);					
					break;
				}
				else{
					output = output + " " + topChr;		
				}				
			}
		}
		if(blnInsert){
			lnksck.push(chr);
			blnoperatorAdded = true;	
		}
		
	}
	
	public String popWithSyntaxVatidation(){
		String N = "";
		if (!lnkpost.isEmpty())
			N = lnkpost.pop();
		else{
			blnSyntaxError = true;
			}		
		return N;
	}
	public void postFixEval(String inFix){
		String N1;
		String N2;
		String Result="";
		polyCalculation pc = new polyCalculation();		
		inFix= inFix.trim();
		String [] a = inFix.split(" ");
		for(int i =0;i<a.length;i++){
			//System.out.println("val " + a[i]);
			char chr = a[i].charAt(0);
			switch (chr){
			case '+':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				//System.out.println(N1 + "" + N2);
				if(N2!=""&&N1!=""){
					if(N2.indexOf("N")>-1 && (N1.indexOf("N")<0)){
						Result = pc.minus(N1, N2.substring(1,N2.length()));
						if(Result.equalsIgnoreCase("Negative Number")){
							Result ="N"+ pc.minus(N2.substring(1,N2.length()),N1);
						}
					}
					else if (N1.indexOf("N")>-1 && (N2.indexOf("N")<0)){
						Result = pc.minus(N2, N1.substring(1,N1.length()));
						if(Result.equalsIgnoreCase("Negative Number")){
							Result ="N"+ pc.minus(N1.substring(1,N1.length()),N2);
						}
					}
					else if (N1.indexOf("N")>-1 && (N2.indexOf("N")>-1)){
						Result = "N"+ pc.addition(N2.substring(1,N2.length()), N1.substring(1,N1.length()));						
					}
					else if (N1.indexOf("N")<0 && (N2.indexOf("N")<0)){
						Result =pc.addition(N1,N2 );						
					}
					//System.out.println(Result);
					lnkpost.push(Result);
				}
				break;
			case '-':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				if(N2!=""&&N1!=""){
					if(N1.indexOf("N")<0 && N2.indexOf("N")>-1){ // N2 is Negative
						Result=pc.addition(N1, N2.substring(1,N2.length()));
					}
					else if(N1.indexOf("N")>-1 && N2.indexOf("N")<0){ // N1 is Negative
						Result="N" + pc.addition(N1.substring(1,N1.length()), N2);
					}
					else if (N1.indexOf("N")>-1 && (N2.indexOf("N")>-1)){  // N1 and N2 are Negative
						Result = pc.minus(N2.substring(1,N2.length()), N1.substring(1,N1.length()));
						if (Result.equalsIgnoreCase("Negative Number")){							
							Result ="N"+ pc.minus(N1.substring(1,N1.length()),N2.substring(1,N2.length()));
						}
					}
					else if (N1.indexOf("N")<0 && (N2.indexOf("N")<0)){ //N1 and N2 are positive
						Result =pc.minus(N1,N2 );	
						if (Result.equalsIgnoreCase("Negative Number")){							
							Result ="N"+ pc.minus(N2,N1);
						}
					}
					lnkpost.push(Result);
					//System.out.println("pushed " + Result);
				}
				
				break;
			case '*':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				//System.out.println(N2 + "   " + N1);
				if(N2!=""&&N1!=""){
					if(N1.indexOf("N")<0 && N2.indexOf("N")>-1){ // N2 is Negative
						Result="N" + pc.multiplication(N1, N2.substring(1,N2.length()));
					}
					else if(N1.indexOf("N")>-1 && N2.indexOf("N")<0){ // N1 is Negative
						Result="N" + pc.multiplication(N1.substring(1,N1.length()), N2);
					}
					else if (N1.indexOf("N")>-1 && (N2.indexOf("N")>-1)){  // N1 and N2 are Negative
						Result = pc.multiplication(N2.substring(1,N2.length()), N1.substring(1,N1.length()));						
					}
					else if (N1.indexOf("N")<0 && (N2.indexOf("N")<0)){ //N1 and N2 are positive
						Result = pc.multiplication(N1, N2);
					}					
					lnkpost.push(Result);
					//System.out.println("pushed " + Result);
				}				
				break;
			case '^':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				if(N2!=""&&N1!=""){
					Result = pc.Exponential(N1, N2);
					if(Result.equalsIgnoreCase("Error")){
						blnSyntaxError = true;
					}
					else{
						lnkpost.push(Result);
					}
					//System.out.println("pushed " + Result);
				}
				break;
			case '/':
			case '%':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				if(N2!=""&&N1!=""){
					if(N1.indexOf("N")<0 && N2.indexOf("N")>-1){ // N2 is Negative
						Result="N" + pc.division(N1, N2.substring(1,N2.length()),chr);
					}
					else if(N1.indexOf("N")>-1 && N2.indexOf("N")<0){ // N1 is Negative
						Result="N" + pc.division(N1.substring(1,N1.length()), N2,chr);
					}
					else if (N1.indexOf("N")>-1 && (N2.indexOf("N")>-1)){  // N1 and N2 are Negative
						Result = pc.division(N1.substring(1,N1.length()), N2.substring(1,N2.length()),chr);						
					}
					else if (N1.indexOf("N")<0 && (N2.indexOf("N")<0)){ //N1 and N2 are positive
						Result = pc.division(N1, N2,chr);
					}					
					
					lnkpost.push(Result);
				}
				break;
			case 'r':
				//System.out.println("i m here");
				N1 =popWithSyntaxVatidation();
				if(N1!=""){
					if(N1.indexOf("N")>-1){
						blnSyntaxError = true;
					}
					else{
						//System.out.println("poping " + N1);
						Result = pc.root(N1);
						lnkpost.push(Result);
					}
					
					//System.out.println("pushing " + Result);
				}				
				break;
			default:
				lnkpost.push(a[i]);
			}
			if(blnSyntaxError){
				break;
			}
		}
		if(!blnSyntaxError){
			String strFinalResult = lnkpost.pop();
			if(strFinalResult.indexOf("N")>-1){
				strFinalResult = strFinalResult.substring(1,strFinalResult.length());
				if(strFinalResult.equalsIgnoreCase("0")){
					System.out.println(strFinalResult);
				}
				else{
				System.out.println("-" +strFinalResult );
				}
			}
			else{
				System.out.println(strFinalResult);
			}
			
		}
		else{
			System.out.println("Syntax Error2");
		}		
	}
	
	public static String getInput() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		String s = br.readLine();		
		return s;		
	}
	public static void displayChoices(){
		
		System.out.println("Enter '0' for Exit");
		System.out.println("Enter The Expression");
	}

	public static boolean syntaxErrorParan(String exp){
		
		int OpenPCount=0;
		int ClosePCount=0;
		boolean result=true;
		for(int i =0;i<exp.length();i++){
			if (ClosePCount>OpenPCount){
				result = result & false;
			}
			else{
				if(exp.charAt(i)=='('){
					OpenPCount++;
				}
				else if(exp.charAt(i)==')'){
					ClosePCount++;
				}
			}			
		}
		if (ClosePCount!=OpenPCount){
			result = result & false;
		}
		return result;
	}
	public static void main(String arg[]) throws IOException{
		
		displayChoices();
		//String s = getInput();
		StringBuffer myData = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String str;		
		ArrayList<String> arrl = new ArrayList<String>();        
        while(true){
        	str = br.readLine();
			if(str.equalsIgnoreCase("0")){
				System.out.println("BYE");
				break;
			}
			else{				
				arrl.add(str);
			}
		}
        Iterator<String> itr = arrl.iterator();
        startTime = System.currentTimeMillis();
        while(itr.hasNext()){
        	String temp = itr.next();
        	if(!syntaxErrorParan(temp)){
        		blnSyntaxError = true;
        	}
        	ArithematicPrecision2 sll = new ArithematicPrecision2(temp);
        	String prefx = sll.ConvertPrefix();
        	if(prefx.equalsIgnoreCase("")){
        		blnSyntaxError = true;
        	}
			if(blnNegativeNo==false){
				if(blnSyntaxError==false){
					sll.postFixEval(prefx);					
				}
				else{
					System.out.println("Syntax Error1");
				}
			}
			else{
				System.out.println("Negative Number Not Supported");
			}
			blnNegativeNo = false;
			blnSyntaxError=false;
        }
        endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}	
}

class Link{
	public char data;
	public Link next;
	
	public Link(char dd){
		{data = dd;}
	}
	public void displayLink(){
		System.out.print(data + " ");
	}
}

class LinkingList{
	private Link first;
	public LinkingList(){
		first = null;
	}
	public boolean isEmpty(){
		return (first==null);
	}
	public void insertFirst(char dd){
		Link nL = new Link(dd);
		nL.next = first;
		first = nL;
	}
	public char deleteFirst(){
		Link temp = first;
		first = first.next;
		return temp.data;
	}
	public void displayList(){
		Link current = first;
		while(current!=null){
			current.displayLink();
			current = current.next;
		}
	}
}

class LinkStack{
	private LinkingList lst;
	public LinkStack(){
		lst = new LinkingList();
	}
	public void push(char j){
		lst.insertFirst(j);
	}
	public char pop(){
		return lst.deleteFirst();
	}
	public boolean isEmpty(){
		return (lst.isEmpty());
	}
	public void displayStack(){
		System.out.print("Stack (top-->bottom): ");
		lst.displayList();
	}
}

	class PolyNode{
		public int coef;
		public int pow;
		public PolyNode next;
		public int base =10;
		public PolyNode(int coef, int pow){
			this.coef = coef;
			this.pow = pow;
			next = null;
		}
		public void displayPolyNode(){
			System.out.println(coef + " " + pow);
		}
	}
	class LinkPolynomial{
		public PolyNode first;
		public LinkPolynomial(){
			first=null;
		}
		public boolean isEmpty(){
			return (first==null);
		}
		public void insertFirst(int coef, int pow ){
			PolyNode nL = new PolyNode(coef, pow);
			nL.next = first;
			first = nL;
		}
		public int deleteFirst(){
			PolyNode temp = first;
			first = first.next;
			return temp.coef;
		}
		public void displayList(){
			PolyNode current = first;
			while(current!=null){
				current.displayPolyNode();
				current = current.next;
				
			}
		}
		
		public String convertPolyToNumberadd(){
			PolyNode current = first;
			String strRes="";
			int carryfwd =0;
			int pow;
			int coe;
			while(current!=null){				
				coe = current.coef;
				coe = coe+carryfwd;
				carryfwd =0;
				if(coe>=10){
					carryfwd = 1;
					coe=coe-10;
				}
				strRes = String.valueOf(coe) + strRes;				
				current = current.next;
			}
			if(carryfwd>0){
				strRes = String.valueOf(carryfwd) + strRes;
			}			
			return strRes;
		}
		public String convertPolyToNumberminus(){
			PolyNode current = first;
			String strRes="";
			int carryfwd =0;
			int pow;
			int coe;
			while(current!=null){				
				coe = current.coef;
				coe = coe-carryfwd;
				carryfwd =0;
				if(coe<0){
					carryfwd = 1;
					coe=coe+10;
				}
				strRes = String.valueOf(coe) + strRes;
				current = current.next;
			}
			if(carryfwd>0){
				strRes = "Negative Number";
				//break;
			}
			int n = strRes.length();
			for(int k=0;k<n-1;k++){
				if(strRes.startsWith("0")){
					strRes=strRes.substring(1, strRes.length());					
				}
			}
//			
			return strRes;
		}
		public String convertPolyToNumbermulty(){
			PolyNode current = first;
			String strRes="";
			int carryfwd =0;
			int power;
			int coe;
			Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>();
			while(current!=null){				
				coe = current.coef;
				power = current.pow;
				Integer t1 =(Integer) ht.get(power);
				if(t1 ==null){
					ht.put(power, coe);
				}
				else{
					ht.put(power, t1+coe);
				}
				current = current.next;
			}
			Integer [] keys = (Integer[]) ht.keySet().toArray(new Integer[0]);
			Arrays.sort(keys);
			for (Integer key:keys){
				coe = ht.get(key);
				coe = coe+carryfwd;
				carryfwd =0;
				if(coe>=10){
					carryfwd = (int)coe/10;
					coe=coe%10;
				}
				strRes = String.valueOf(coe) + strRes;	
			}
			if(carryfwd>0){
				strRes = String.valueOf(carryfwd) + strRes;
			}			
			return strRes;
		}
	}
	

	class polyCalculation{
		LinkPolynomial firstP = new LinkPolynomial();
		LinkPolynomial secondP = new LinkPolynomial();
		LinkPolynomial result = new LinkPolynomial();
		LinkPolynomial resultMulti = new LinkPolynomial();
		String rootResult ="";
		String rootDividedBy;
		String rootRemainder;
		public String root(String N1){	
			String tempres="";
			boolean blnEvenDigits=false;
			if (N1.length()%2==0)
				blnEvenDigits=true;
			else
				blnEvenDigits=false;
			if(blnEvenDigits){
				for(int i =0;i<N1.length();i=i+2){
					//System.out.println(N1.charAt(i)+ "     " + N1.charAt(i+1));
					String strTempNo = String.valueOf(N1.charAt(i))+ String.valueOf(N1.charAt(i+1));	
					rootCalculate(strTempNo);
				}
			}
			else{
				//System.out.println("odd");
				if(N1.length()==1){
					String strTempNo = String.valueOf(N1);	
					rootCalculate(strTempNo);
				}
				else{
					rootCalculate(String.valueOf(N1.charAt(0)));
					for(int i =1;i<N1.length();i=i+2){
						//System.out.println(N1.charAt(i)+ "     " + N1.charAt(i+1));
						String strTempNo = String.valueOf(N1.charAt(i))+ String.valueOf(N1.charAt(i+1));	
						rootCalculate(strTempNo);
					}
				}
			}
					
			tempres = rootResult;
			rootResult ="";
			rootDividedBy="";
			rootRemainder="";
			return tempres;			
		}
		public void rootCalculate(String N){
			//System.out.println(N);
			if(rootResult==""){
				//System.out.println("Sumit");
				for(int k=9;k>=1;k--){
					String strT1 = String.valueOf(k*k);
					//System.out.println(strT1);
					String strDiff = minus(N,strT1);
					//System.out.println(strDiff);
					if(!strDiff.equalsIgnoreCase("Negative Number")){
						rootResult=String.valueOf(k);
						rootDividedBy = String.valueOf(k+k);
						rootRemainder = strDiff;
						break;
					}
				}
				//System.out.println(rootRemainder);
				//System.out.println(rootDividedBy);
			}
			else{
				if(rootRemainder.trim()!="0"){
					N=rootRemainder+N;
				}
				//System.out.println("New N " + N);
				for(int k=9;k>=1;k--){
					String strT1 =rootDividedBy + String.valueOf(k);
					///System.out.println(strT1);
					String strDiff = minus(N,multiplication(strT1, String.valueOf(k)));
					//System.out.println(strDiff);
					if(!strDiff.equalsIgnoreCase("Negative Number")){
						rootResult= rootResult + String.valueOf(k);
						rootDividedBy = addition(strT1, String.valueOf(k));
						rootRemainder = strDiff;
						break;
					}
					else{
						if(k==1){
							rootResult = rootResult + "0";
						}
					}				
				}				
			}
		}
		
		public String division(String N1, String N2,char chr){			
//			for(int i = (N1.length()-1);i>=0;i--){
//				String coe = String.valueOf(N1.charAt(i));	
//				firstP.insertFirst(Integer.parseInt(coe), ((N1.length()-1)-i));
//			}
//			for(int i = (N2.length()-1);i>=0;i--){
//				String coe = String.valueOf(N2.charAt(i));
//				secondP.insertFirst(Integer.parseInt(coe), ((N2.length()-1)-i));
//			}			
			//PolyNode firstNo = firstP.first;
			//System.out.println(" Starting Dividion N1  " + N1+ " N2 " + N2);
			int quoient=0;
			String temp="0";
			String ret="0";
			String temp1="0";
			String temp2;
			String pow1="0";
			String pow_temp="0";
			while(N1.length()-N2.length()>2){
				String powDiff = minus(String.valueOf(N1.length()),String.valueOf(N2.length()+1));
				//System.out.println("POWDIFF  " + powDiff + " N1 " + N1);
				//System.out.println("temp1  initial " + temp1);
				temp1 = Exponential(String.valueOf(10), powDiff);
				//System.out.println("temp1  " + temp1);
				pow1 = addition(pow1, temp1);
				temp2 = multiplication(N2, temp1);
				
				//System.out.println(N1 + "   temp1  after muliplication   " + temp2);
				N1 = minus(N1, temp2);
				String N1_temp = N1;
				int count =0;
				while(N1_temp!="Negative Number"){
					N1 = N1_temp;
					count++;
					
					N1_temp = minus(N1_temp,temp2);
				}
				//System.out.println("power " + pow1);
				count = count-1;
				if (count<0) {
					count=0;
				}
				pow1 = addition(pow1, multiplication(temp1, String.valueOf(count)));
				//System.out.println("power " + pow1);
				//System.out.println("Updated N1 " + N1 + " Updated N2 " +  N2 );
			}
			N1 =minus(N1, N2);			
			while(N1!="Negative Number"){
				//System.out.println(N1 + "  Reminder: " + temp);
				result.insertFirst(1, 1);
				temp=N1;
				N1 = minus(N1, N2);				
			}
			//System.out.println("Ans: " + result.convertPolyToNumbermulty());
			if(chr=='/'){
				
				String Quo = result.convertPolyToNumbermulty();
				while(result.isEmpty()!=true){
					result.deleteFirst();
				}
				if(Quo.equalsIgnoreCase("")){
					Quo ="0";
				}
				//System.out.println("temp 1 " + pow1 + "Quo  " + Quo);
				ret = addition(Quo, pow1);
				//ret = Quo;
			}
			else if(chr=='%'){
				ret = temp;
			}
			
			//System.out.println("Quo " + Quo  + "  Reminder: " + temp);
//			while(firstP.isEmpty()!=true){
//				firstP.deleteFirst();
//			}
//			while(secondP.isEmpty()!=true){
//				secondP.deleteFirst();
//			}
			
			//System.out.println("Returning Devision   : " + ret);
			return ret;
			
		}
		public String multiplication(String N1, String N2){	
			//System.out.println(N1 + "  multiply: " + N2);
			while(firstP.isEmpty()!=true){
				firstP.deleteFirst();
			}
			while(secondP.isEmpty()!=true){
				secondP.deleteFirst();
			}
			while(result.isEmpty()!=true){
				result.deleteFirst();
			}
			for(int i = (N1.length()-1);i>=0;i--){
				String coe = String.valueOf(N1.charAt(i));	
				firstP.insertFirst(Integer.parseInt(coe), ((N1.length()-1)-i));
			}
			for(int i = (N2.length()-1);i>=0;i--){
				String coe = String.valueOf(N2.charAt(i));
				secondP.insertFirst(Integer.parseInt(coe), ((N2.length()-1)-i));
			}	
			
			
			PolyNode firstNo = firstP.first;			
			
			while(firstNo!=null){
				PolyNode secondNo = secondP.first;
				while(secondNo!=null){
					result.insertFirst(firstNo.coef*secondNo.coef, firstNo.pow+secondNo.pow);
					secondNo = secondNo.next;
				}
				firstNo = firstNo.next;
			}
			String tmp = result.convertPolyToNumbermulty();
			while(firstP.isEmpty()!=true){
				firstP.deleteFirst();
			}
			while(secondP.isEmpty()!=true){
				secondP.deleteFirst();
			}
			while(result.isEmpty()!=true){
				result.deleteFirst();
			}			
			//System.out.println("multiplcation return " + String.valueOf(tmp));
			return String.valueOf(tmp);
		}
		public String Exponential(String N1, String N2){		
			boolean blnAdd_NegSign=true;
			boolean blnN1Neg= false;
			//System.out.println(" N1 ; " + N1 + " N2 " + N2);
			if(N1.indexOf("N")>-1){
				N1 = N1.substring(1,N1.length());
				blnN1Neg = true;
			}
			String res_temp = N1;
			if(N2.indexOf("N")>-1){
				return "Error";
			}
			//System.out.println(N1 + " " + N2 );
			N2 = minus(N2, "1");
			//System.out.println(" N1 ; " + N1 + " N2 " + N2);
			//System.out.println(N1 + " " + N2 );
			while(!N2.equalsIgnoreCase("0")){
			//	System.out.println("AAAAA" + res_temp + " N1  " +  N1);
				res_temp =  multiplication(res_temp, N1);	
				//System.out.println("BBBB" + res_temp + " N1  " +  N1);
			N2 = minus(N2, "1");
			//System.out.println(res_temp + " " + N2 );
			blnAdd_NegSign = !blnAdd_NegSign;
			}			
			if(blnN1Neg){
				if(blnAdd_NegSign){
					res_temp = "N" + res_temp;
				}
			}
			//System.out.println(res_temp );
			return res_temp;
		}
		public String addition(String N1, String N2 ){			
			for(int i = (N1.length()-1);i>=0;i--){
				String coe = String.valueOf(N1.charAt(i));
				firstP.insertFirst(Integer.parseInt(coe), ((N1.length()-1)-i));
			}
			for(int i = (N2.length()-1);i>=0;i--){
				String coe = String.valueOf(N2.charAt(i));				
				secondP.insertFirst(Integer.parseInt(coe), ((N2.length()-1)-i));
			}			
			PolyNode firstNo = firstP.first;
			PolyNode secondNo = secondP.first;
			
			while(firstNo!=null || secondNo!=null){
				if(firstNo.pow>secondNo.pow){
					result.insertFirst(firstNo.coef, firstNo.pow);
					firstNo = firstNo.next;
				}
				else if(firstNo.pow<secondNo.pow){
					result.insertFirst(secondNo.coef, secondNo.pow);
					secondNo = secondNo.next;
				}
				else if(firstNo.pow==secondNo.pow){
					result.insertFirst(firstNo.coef +secondNo.coef  , firstNo.pow);
					firstNo = firstNo.next;
					secondNo = secondNo.next;
				}
			}
			while(firstNo!=null){
				result.insertFirst(firstNo.coef, firstNo.pow);
				firstNo = firstNo.next;
			}
			while(secondNo!=null){
				result.insertFirst(secondNo.coef, secondNo.pow);
				secondNo = secondNo.next;
			}
			
			String tmp = result.convertPolyToNumberadd();			
			while(firstP.isEmpty()!=true){
				firstP.deleteFirst();
			}
			while(secondP.isEmpty()!=true){
				secondP.deleteFirst();
			}
			while(result.isEmpty()!=true){
				result.deleteFirst();
			}
			return String.valueOf(tmp);
		}
		public String minus(String N1, String N2 ){			
			LinkPolynomial firstP_minus = new LinkPolynomial();
			LinkPolynomial secondP_minus = new LinkPolynomial();
			LinkPolynomial result_minus = new LinkPolynomial();
			
			for(int i = (N1.length()-1);i>=0;i--){
				String coe = String.valueOf(N1.charAt(i));				
				firstP_minus.insertFirst(Integer.parseInt(coe), ((N1.length()-1)-i));
			}
			for(int i = (N2.length()-1);i>=0;i--){
				String coe = String.valueOf(N2.charAt(i));				
				secondP_minus.insertFirst(Integer.parseInt(coe), ((N2.length()-1)-i));
			}			
			PolyNode firstNo_m = firstP_minus.first;
			PolyNode secondNo_m = secondP_minus.first;
			
			while(firstNo_m!=null || secondNo_m!=null){
				if(firstNo_m.pow>secondNo_m.pow){
					result_minus.insertFirst(firstNo_m.coef, firstNo_m.pow);
					firstNo_m = firstNo_m.next;
				}
				else if(firstNo_m.pow<secondNo_m.pow){
//					result_minus.insertFirst(secondNo_m.coef, secondNo_m.pow);
//					secondNo_m = secondNo_m.next;
					return "Negative Number";
				}
				else if(firstNo_m.pow==secondNo_m.pow){
					result_minus.insertFirst(firstNo_m.coef -secondNo_m.coef  , firstNo_m.pow);
					firstNo_m = firstNo_m.next;
					secondNo_m = secondNo_m.next;
				}
			}			
			while(firstNo_m!=null){
				result_minus.insertFirst(firstNo_m.coef, firstNo_m.pow);
				firstNo_m = firstNo_m.next;
			}
			while(secondNo_m!=null){
				result_minus.insertFirst(secondNo_m.coef, secondNo_m.pow);
				secondNo_m = secondNo_m.next;
			}			
			String tmp = result_minus.convertPolyToNumberminus();
			
			while(firstP_minus.isEmpty()!=true){
				firstP_minus.deleteFirst();
			}
			while(secondP_minus.isEmpty()!=true){
				secondP_minus.deleteFirst();
			}
			while(result_minus.isEmpty()!=true){
				result_minus.deleteFirst();
			}			
			return String.valueOf(tmp);
		}		
		public static void main(String args[]){
			
		}
	}	
	class LinkPostFix{
		public String number;
		public LinkPostFix next;
		
		public LinkPostFix(String number){
			this.number = number;
			next = null;
		}
	}
	
	class LinkListPostFix{
		public LinkPostFix first;
		
		public LinkListPostFix(){
			first = null;
		}
		public boolean isEmpty(){
			return (first==null);
		}
		public void insertFirst(String num){
			LinkPostFix nL = new LinkPostFix(num);
			nL.next = first;
			first = nL;
		}
		public String deleteFirst(){
			String temp = first.number;
			first = first.next;
			return temp;
		}
	}
	
	class StackPostFix{
		private LinkListPostFix lst;
		public StackPostFix(){
			lst = new LinkListPostFix();
		}
		public void push(String j){
			lst.insertFirst(j);
		}
		public String pop(){
			return lst.deleteFirst();
		}
		public boolean isEmpty(){
			return (lst.isEmpty());
		}		
	}
	
	
