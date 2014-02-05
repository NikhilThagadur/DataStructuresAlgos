

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MaxMatching {
	public static int numVertexLeft;
	public static int numVertexRight;
	public static void main(String arg[]) throws IOException{

		int V = 9;
		int E = 5;
		long startTime;
		long endTime;
		Graph g = new Graph();

		//    	g.addEdge(g, 0, 1, 4);
		//    	g.addEdge(g, 0, 4, 6);
		//    	g.addEdge(g, 1, 2, 5);
		//    	g.addEdge(g, 1, 4, 7);
		//    	g.addEdge(g, 0, 3, 5);
		//    	g.addEdge(g, 3, 4, 9);
		////    	g.addEdge(g, 2, 3, 5);
		//    	g.addEdge(g, 0, 1, 4);
		//    	g.addEdge(g, 0, 7, 8);
		//    	g.addEdge(g, 1, 2, 8);
		//    	g.addEdge(g, 1, 7, 11);
		//    	g.addEdge(g, 2, 3, 7);
		//    	g.addEdge(g, 2, 8, 2);
		//    	g.addEdge(g, 2, 5, 4);
		//    	g.addEdge(g, 3, 4, 9);
		//    	g.addEdge(g, 3, 5, 14);
		//    	g.addEdge(g, 4, 5, 10);
		//    	g.addEdge(g, 5, 6, 2);
		//    	g.addEdge(g, 6, 7, 1);
		//    	g.addEdge(g, 6, 8, 6);
		//    	g.addEdge(g, 7, 8, 7);

		//    	g.PSTMain(g);
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Maximum Bipartite Matching Implementation");
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
				int a,b;
				while(s.charAt(i)!=' ')
				{
					i++;
				}
				a = Integer.parseInt(s.substring(0, i));
				//System.out.println("a   " + a);
				i++;
				int j=i;
				//System.out.println("b   " + b);
				while(s.charAt(j)!=' ')
				{
					j++;
				}
				b = Integer.parseInt(s.substring(i, j));
				//System.out.println(a + "   " + b);
				ReadlineCounter++;
				V=(int) a;
				g.createGraph(g,V);
				numVertexLeft =(int)a;
				numVertexRight = (int)b;
			}
			else{
				String s1="";
				String s2="";;
				long a=0, b=0,c=0;
				int i=0;
				Boolean aFound =false;
				Boolean bFound =false;
				Boolean cFound =false;
				//System.out.println(s);
				s= s.trim();
				//System.out.println(s);

				for (int k=0;k<s.length();k++){
					if(s.substring(k, k+1).matches("[0-9]")){
						s1 = s1 + s.substring(k, k+1);    	
						//System.out.println(s1);
					}
					else{
						if(!aFound && s1!=""){
							//System.out.println(s1);
							a=Integer.parseInt(s1, 10);
							s1="";
							aFound = true;
						}
						if(aFound==true && bFound==false && s1!=""){
							b=Integer.parseInt(s1, 10);
							//System.out.println(s1);
							s1="";
							bFound = true;
						}
					}
				}

				if(aFound==true && bFound==true && cFound ==false && s1!=""){
					//System.out.println(s1);
					c=Integer.parseInt(s1, 10);
					s1="";
					cFound = true;
				}
				//System.out.println(a + "   " + b + "  " + c);
				//numVertexRight =(int)b;
				g.addEdge(g, (int)a-1, (int)b-1, (int)c);
			}



		}
		//System.out.println(answer);
		//    	g.PSTMain(g);
		//MaxMatching m = new MaxMatching();
		maxBM(g);

		endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");

	}

	static boolean bM(Graph gph, int x, boolean visited[], int match[])
	//static boolean bM(Graph gph, int x, boolean visited[], int match[])
	{
		int start = 0;

		AdjListNode pc = gph.array[x].head;
		//		
		while(pc!=null)
		{
			if(!visited[pc.dest])
			{
				visited[pc.dest] = true;
				/*if(x==0){
					System.out.println(x + "  " + pc.dest);
				}*/
				if(match[pc.dest] < 0 || bM(gph, match[pc.dest], visited, match))
				{
					match[pc.dest]=x;
					return true;
				}
			}			
			pc=pc.next;
		}
		//		for(int i=0; i<N; i++)
		//		{
		//			if((graph[x][i]!=-1) && !visited[i])
		//			{
		//				visited[i] = true;
		//				if(match[i] < 0 || bM(graph, match[i], visited, match))
		//				{
		//					match[i]=x;
		//					return true;
		//				}
		//			}
		//		}
		return false;
	}

	static int maxBM(Graph gph) 
	{
		int num = 0;
		int[] match = new int[numVertexRight];
		//System.out.println("match " + match.length + "   " +numVertexRight  );
		for(int i=0; i<numVertexRight; i++)
		{
			match[i]=-1;
		}
		for(int i=0; i<numVertexLeft; i++)
		{
			boolean[] visited = new boolean[numVertexRight];
			for(int j=0; j<numVertexRight; j++)
			{
				visited[j]=false;
			}
			//				//if(i==0)
			//				//	System.out.println(j + "    ");
			//			}
			//System.out.println(i);
			/*			AdjListNode pc = gph.array[i].head;
			while(pc!=null){
				pc.visited = false;
				if(i==0)
					System.out.println( pc.dest + "    " + pc.weight);
				pc=pc.next;
			}
			 */			//if(bM(gph, i, match))
			if(bM(gph, i,visited, match))
				num++;
		}
		System.out.println(num);
				for(int i=0; i<numVertexRight; i++)
				{
					if(match[i]!=-1)
					{
						System.out.print("("+match[i]+","+(i+1)+"), ");
					}
				}
		return 0;
	}
}
class AdjListNode
{
	int dest;
	int weight;
	//Boolean visited;
	AdjListNode next;
}
//A structure to represent an adjacency liat
class AdjList
{
	AdjListNode head;  // pointer to head node of list

	public AdjListNode newAdjNode(int destination, int weight){
		AdjListNode adjN  = new AdjListNode();
		adjN.dest = destination;
		adjN.weight = weight;
		//adjN.visited = false;
		adjN.next=null;
		return adjN;
	}
}
// A structure to represent a graph. A graph is an array of adjacency lists.
//Size of array will be V (number of vertices in graph)
class Graph
{
	int V;
	AdjList array[];    


	public Graph createGraph(Graph gph, int V){
		//Graph gph = new Graph();
		gph.V = V;
		gph.array = new AdjList[V+1]; 
		//System.out.println(V + " vertexsx");
		for(int i=0;i<V;i++){
			//System.out.println(i + " node created");
			gph.array[i] = new AdjList();
			gph.array[i].head = null;
		}
		//System.out.println("return");
		return gph;
	}

	public void addEdge(Graph gph, int source, int destination, int weight){
		AdjList al = new AdjList();
		AdjListNode adn =  al.newAdjNode(destination, weight);

		adn.next = gph.array[source].head;
		gph.array[source].head = adn;    	
		//adn =  al.newAdjNode(source, weight);
		//System.out.println(source + "  " + destination); 
		//adn.next = gph.array[destination].head;
		//gph.array[destination].head = adn;
	}
}





