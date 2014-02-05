




import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;



public class MaxFlow {
	
//	public static int [] [] graph;
//	
	public static long startTime;
	public static long endTime;
	ArrayList Edges = new ArrayList();
	ArrayList Ecapacity = new ArrayList();
	
	public static void main(String args[]) throws IOException{
		
		int V = 10;
		Graph g = new Graph();
		int source=0;
		int sink=0;
//		g.createGraph(g, V);  
		
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Maximum Flow Implementation");
		System.out.println("Enter file path..");
		String filepath = in.readLine();
		//		String filepath = "C:\\in.100K";
		//String filepath = "C:\\inMst2.2	";
		BufferedReader input = new BufferedReader (new FileReader(filepath));
		String s;
		startTime = System.currentTimeMillis();
		int ReadlineCounter=0;
		
		while((s=input.readLine())!=null){

			if(ReadlineCounter==0){
				//s=input.readLine();
				int i=0;
				int a=0,b=0,c=0,d=0,e=0;
				Boolean VFound =false;
				Boolean EFound =false;
				Boolean sourceFound =false;
				Boolean sinkFound =false;
				Boolean costFound =false;
				s= s.trim();
				String s1="";
				for (int k=0;k<s.length();k++){
					if(s.substring(k, k+1).matches("[0-9]")){
						s1 = s1 + s.substring(k, k+1);
					}
					else{
						if(!VFound && s1!=""){
							a=Integer.parseInt(s1, 10);
							s1="";
							VFound = true;
						}
						if(VFound==true && EFound==false && s1!=""){
							b=Integer.parseInt(s1, 10);
							s1="";
							EFound = true;
						}
						if(VFound==true && EFound==true && sourceFound ==false && s1!=""){
							c=Integer.parseInt(s1, 10);
							s1="";
							sourceFound = true;
						}
						if(VFound==true && EFound==true && sourceFound ==true && sinkFound ==false&& s1!=""){
							d=Integer.parseInt(s1, 10);
							s1="";
							sinkFound = true;
						}
						if(VFound==true && EFound==true && sourceFound ==true && sinkFound ==false&& costFound ==false && s1!=""){
							e=Integer.parseInt(s1, 10);
							s1="";
							costFound = true;
						}
					}
				}
				ReadlineCounter++;
				V=(int) a;
				g.createGraph(g,V);
				source = c;
				sink = d;
				//numVertexLeft =(int)a;
				//numVertexRight = (int)b;
			}
			else{
				String s1="";
				String s2="";;
				long a=0, b=0,c=0;
				int i=0;
				Boolean aFound =false;
				Boolean bFound =false;
				Boolean cFound =false;				
				s= s.trim();
				for (int k=0;k<s.length();k++){
					if(s.substring(k, k+1).matches("[0-9]")){
						s1 = s1 + s.substring(k, k+1);
					}
					else{
						if(!aFound && s1!=""){
							a=Integer.parseInt(s1, 10);
							s1="";
							aFound = true;
						}
						if(aFound==true && bFound==false && s1!=""){
							b=Integer.parseInt(s1, 10);
							s1="";
							bFound = true;
						}
						if(aFound==true && bFound==true && cFound ==false && s1!=""){
							c=Integer.parseInt(s1, 10);
							s1="";
							cFound = true;
						}
					}
				}
				g.addEdge(g, (int)a, (int)b, (int)c);
			}
		}
		new MaxFlow().calculateMaxFlow(g, source, sink,V);
		endTime = System.currentTimeMillis();
		System.out.println("");
		System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void calculateMaxFlow(Graph g, int source, int dest, int V){
		
		//int residualGraph[][] = new int [V][V];
		
		Graph residualGraph = g;
		
		//System.out.println("XXXX  " + residualGraph[0][2]);
	
		int [] bfsVisitedVertex = new int [V];
		int max_flow=0;
		int tempCapacity=0;
		
		while(BFS(residualGraph,source, dest,bfsVisitedVertex, V)){
			
			int pathFlow = 1000000000;
			
			for(int v=dest;v!=source; v=bfsVisitedVertex[v]){
				int u = bfsVisitedVertex[v];
				adjNode adjN = residualGraph.arrRdjL[u].Head;
				while(adjN.dest!=v){
					adjN = adjN.next;
 				}
				tempCapacity = adjN.capacity;
				pathFlow = Math.min(pathFlow,tempCapacity);
			}
			
			for(int v=dest;v!=source; v=bfsVisitedVertex[v]){
				int u = bfsVisitedVertex[v];
				adjNode adjN = residualGraph.arrRdjL[u].Head;
				adjNode adjNOriginal = g.arrRdjL[u].Head;
				while(adjN.dest!=v){
					adjN = adjN.next;
					adjNOriginal =adjNOriginal.next;
 				}
				String Edge = String.valueOf(u) + " " + String.valueOf(adjN.dest);
				String ECap = String.valueOf(pathFlow) + "/" + String.valueOf(adjN.capacity);
				prepareResult(Edges, Ecapacity, Edge, ECap);
				//System.out.println( u + "  "  + adjN.dest + " " + pathFlow + "/" +adjNOriginal.capacity);
				adjN.capacity = adjN.capacity - pathFlow;
				//if(adjN.capacity>0){
					
				//}
				
//				residualGraph[v][u] = residualGraph[v][u] + pathFlow;
				residualGraph.addEdge(residualGraph, v, u, pathFlow);
			}
			
//			System.out.println("PathFlow" + pathFlow);
			
			max_flow = max_flow + pathFlow;
		}
		
		System.out.println("Maximum Flow From "+ source + " to " + dest);
		System.out.println("Maximum Flow = "+ max_flow);
		System.out.println("Edges with non-zero flow:");
		
		Iterator ie = Edges.iterator();
		Iterator ic = Ecapacity.iterator();
		while(ie.hasNext()){
			System.out.println(ie.next() + "  " + ic.next());
		}	
		//return max_flow;
	}
	
	public void prepareResult(ArrayList Edges, ArrayList ECapacity, String Edge, String Capacity){
		if(!Edges.isEmpty() && Edges.contains(Edge)){
			Iterator ie = Edges.iterator();
			Iterator ic = ECapacity.iterator();
			int index=0;
			//System.out.println();
			while(Edge.compareToIgnoreCase((String) ie.next())!=0){
				ic.next();
				index++;
			}
			String temp = (String) ic.next();
		//	ib.remove();
			//ia.remove();
			//System.out.println(temp);
			String arrtemp[] = temp.split("/");
			int t = Integer.parseInt(arrtemp[0]);
			String arrtempCap[] = Capacity.split("/");
			int newCap = Integer.parseInt(arrtempCap[0]);
			t+=newCap;
			temp = String.valueOf(t)+"/"+arrtemp[1];
			//System.out.println(temp);
			Ecapacity.set(index, temp);
		}
		else{
			//System.out.println("Adding "+ Edge);
			Edges.add(Edge);
			Ecapacity.add(Capacity);
		}
	}
	
	
	public Boolean BFS(Graph residualGraph, int source, int destination, int [] bfsVisitedVertex, int V){
		QQueue q = new QQueue(V);
		Boolean [] visited = new Boolean[V];
		
		for(int i=0;i<visited.length;i++){
			visited[i]=false;
		}
		visited[source] = true;
		q.insert(source);
		bfsVisitedVertex[source] = -1;
		
		while(!q.isEmpty()){
			int u =  q.remove();
			//System.out.println(" POP " + u);
			int v;							
			while((v=getUnvisitedVertex(residualGraph, visited, u))!=-1){				
				q.insert(v);
				bfsVisitedVertex[v]= u;
				visited[v]= true;
			}
			
		}
			
		if(visited[destination]==true){
			return true;				
		}
		else{
			return false;
		}
	
	}
	
	public int getUnvisitedVertex(Graph g, Boolean [] visited, int v){
		adjNode adN = g.arrRdjL[v].Head;
		while(adN!=null){
			if(visited[adN.dest]==false && adN.capacity>0 ){
				return adN.dest;
			}
			adN = adN.next;
		}
		return -1;
	}
}
	
	class QQueue{
		int array[];
		int front;
		int rear;
		int size;
		public QQueue(int size){
			this.size = size;
			array = new int[size];
			front =0;
			rear=-1;
		}
		
		public void insert(int value){
			if(rear==size-1){
				rear=-1;
			}
			array[++rear]=value;
		}
		
		public int remove(){
			int temp = array[front++];
			if(front==size){
				front=0;
			}
			return temp;
		}
		
		public boolean isEmpty(){
			return ((rear+1==front) || (front+size-1==rear));
		}
	}



class adjNode{
	int dest;
	int capacity;
	adjNode next;
}

class adjList{
	adjNode Head;
	
	public adjNode createNewNode(int dest, int capacity){
		adjNode ad = new adjNode();
		ad.capacity = capacity;
		ad.dest = dest;
		ad.next = null;
		return ad;		
	}
}

class Graph{
	int V;
	adjList [] arrRdjL;
	
	public Graph createGraph(Graph g, int V){
		g.V = V;
		g.arrRdjL = new adjList[V];
		
		for(int i=0;i<V;i++){
			g.arrRdjL[i] = new adjList();
			g.arrRdjL[i].Head = null;
		}		
		return g;
	}
	
	public void addEdge(Graph g, int source, int destination, int capacity){
		
		Boolean isEdgeExist=false;
		adjNode adn =g.arrRdjL[source].Head;
		while( isEdgeExist==false && adn!=null){
			if(adn.dest==destination){
				adn.capacity += capacity;
				isEdgeExist = true;
			}
			adn = adn.next;
		}
		if(!isEdgeExist){
			adjNode ad = new adjNode();
			ad.dest=destination;
			ad.capacity = capacity;
			ad.next = null;
			
			//adjNode  adn1 = g.arrRdjL[source].Head;
//			while(adn1!=null){
//				adn1 = adn1.next;
//			}
//			adn1 = ad;
			ad.next = g.arrRdjL[source].Head;
			g.arrRdjL[source].Head = ad;
		}
				
	}
}