

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;

public class ArithematicPrecision {
	
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
	
	public ArithematicPrecision(String in){
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
				decide(chr,1);
				blnIsPrevParan = false;
				break;
			case '*':
				decide(chr,3);
				blnIsPrevParan = false;
				break;
			case '^':
				decide(chr,4);
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
				blnIsPrevParan = false;
				if (blnoperatorAdded==true){
					output = output +" " + chr;
					blnoperatorAdded = false;
				}
				else{
					output = output + chr;
				}								
				break;
			}				
		}
		
		while(!lnksck.isEmpty()){
			output = output + " " + lnksck.pop();
		}
		//	System.out.println("Post Fix Equation: " + output);
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
		while(!lnksck.isEmpty()){
			char topChr = lnksck.pop();		
			if(topChr=='('){
				lnksck.push(topChr);// if ( is poped out, push it back
				if (NewPrec=='-'){
					
					blnNegativeNo = true;
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
				else{
					topPrec =4;
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
		lnksck.push(chr);
		blnoperatorAdded = true;
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
		String Result;
		polyCalculation pc = new polyCalculation();
		String [] a = inFix.split(" ");
		for(int i =0;i<a.length;i++){			
			char chr = a[i].charAt(0);
			switch (chr){
			case '+':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				if(N2!=""&&N1!=""){					
					Result = pc.addition(N1, N2);
					lnkpost.push(Result);
				}
				break;
			case '-':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				if(N2!=""&&N1!=""){
					Result = pc.minus(N1, N2);
					if (Result.equalsIgnoreCase("Negative Numbers")){
						//System.out.println("NNNNegative Number");
						i=a.length;
					}
					lnkpost.push(Result);
				}
				
				break;
			case '*':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				if(N2!=""&&N1!=""){
					Result = pc.multiplication(N1, N2);
					lnkpost.push(Result);
				}				
				break;
			case '^':
				N2 = popWithSyntaxVatidation();
				N1 = popWithSyntaxVatidation();
				if(N2!=""&&N1!=""){
					Result = pc.Exponential(N1, N2);
					lnkpost.push(Result);
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
			System.out.println(lnkpost.pop());
		}
		else{
			System.out.println("Syntax Error");
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
        	ArithematicPrecision sll = new ArithematicPrecision(temp);
        	String prefx = sll.ConvertPrefix();
        	if(prefx.equalsIgnoreCase("")){
        		blnSyntaxError = true;
        	}
			if(blnNegativeNo==false){
				if(blnSyntaxError==false){
					sll.postFixEval(prefx);
					
				}
				else{
					System.out.println("Syntax Error");
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
				strRes = "Negative numbers";
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
		public String multiplication(String N1, String N2){			
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
			return String.valueOf(tmp);
		}
		public String Exponential(String N1, String N2){						
			String res_temp = N1;
			N2 = minus(N2, "1");			
			while(!N2.equalsIgnoreCase("0")){
				res_temp =  multiplication(res_temp, N1);				
			N2 = minus(N2, "1");
			}			
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
	
	
