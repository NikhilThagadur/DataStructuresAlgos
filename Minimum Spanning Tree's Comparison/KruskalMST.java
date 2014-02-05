
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KruskalMST {
	public static void main(String arg[]) throws IOException{
    	int V = 9;
    	int E = 5;
    	long startTime;
    	long endTime;
    	Graph g = new Graph();
    	Graph gph = null;
    	int edgeCounter =0;
    	Edge e;
    	
    	
//    	g.addEdge(g, 0, 1, 4);
//    	g.addEdge(g, 0, 4, 6);
//    	g.addEdge(g, 1, 2, 5);
//    	g.addEdge(g, 1, 4, 7);
//    	g.addEdge(g, 0, 3, 5);
//    	g.addEdge(g, 3, 4, 9);
////    g.addEdge(g, 2, 3, 5);
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
        System.out.println("Kruskal MST Implementation....");
        System.out.println("Enter file path..");
		String filepath = in.readLine();
//		String filepath = "C:\\in.100K";
//		String filepath = "C:\\222.txt";
		BufferedReader input = new BufferedReader (new FileReader(filepath));
    	String s;
    	startTime = System.currentTimeMillis();
    	int ReadlineCounter=0;
    	while((s=input.readLine())!=null){    		
    		if(ReadlineCounter==0){
    			String s1;
    			long a=0, b=0;
    			int i=0;
    			while(s.charAt(i)!=' '){
    				i++;
    			}
    			s1=s.substring(0, i);
    			a=Integer.parseInt(s1, 10);    			
    			s1=s.substring(i+1, s.length());
    			b=Integer.parseInt(s1, 10);
    			//System.out.println(a + "   " + b);
    			ReadlineCounter++;
    			V=(int) a;
    			E = (int)b;
    			gph = g.createGraph(V,E);
    			//System.out.println("Graph initialized");
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
				e = new Edge((int)a-1, (int)b-1, (int)c);
				//System.out.println("Edge Counter " + gph.edge.length);
    			gph.edge[edgeCounter++]=e;
    		}  		   		
    	}
    	
    	Sort srt = new Sort(gph.edge);
    	
    	gph.edge = srt.mSort();
    	
    	for(int i = 0;i<gph.edge.length;i++){
    		//System.out.println(gph.edge[i].Source);
    	}
    	new KruskalMST().Kruskal(gph);
    	
    	
    	//System.out.println(answer);
    	
    	endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");    	
    }
	
	public void Kruskal(Graph g){
		int MST=0;
		int V = g.Vertex;
		int e=0;
		int sorted_EdgeCounter=0;
		
		subset sb[] = new subset[V];
		//System.out.println("subset size " + sb.length);
		for (int v = 0; v < V; ++v)
	    {
			sb[v] = new subset();					
	        sb[v].parent = v;
	        sb[v].rank = 0;
	    }
		
		while(e<V-1){
			//System.out.println("It took " + g.edge[sorted_EdgeCounter].weight + "  vvvv " + sorted_EdgeCounter + "eeeee " + e);
			Edge nxtEdge = g.edge[sorted_EdgeCounter++];
			//System.out.println("nxtEdge.Source " + nxtEdge.Source);
			int x = g.find(sb, nxtEdge.Source);
			int y = g.find(sb, nxtEdge.destination);
			//System.out.println("x " + x + " y " + y);
			if(x!=y){
				e=e+1;
				MST += nxtEdge.weight;
				g.Union(sb, x, y);
				//System.out.println("MST " + MST + "   e  " + e);
			}
		}
		
		System.out.println("MST " + MST);
		
	}

}

class Edge{
	int Source;
	int destination;
	int weight;
	
	public Edge(int Source, int destination, int weight){
		this.Source = Source;
		this.destination = destination;
		this.weight = weight;
	}
}
class Graph{
	int Vertex;
	int Edges;
	Edge [] edge;

	public Graph createGraph(int Vertex, int Edges){
		Graph g = new Graph();
		g.Vertex = Vertex;
		g.Edges = Edges;
		g.edge = new Edge[Edges];
		return g;
	}
	
	public int find(subset subsets[], int i){
		//System.out.println("inside subset " + i);
		if (subsets[i].parent != i)
			subsets[i].parent = find(subsets, subsets[i].parent);

		return subsets[i].parent;
	}

	public void Union(subset subsets[], int x, int y)
	{
	    int xroot = find(subsets, x);
	    int yroot = find(subsets, y);
	 
	    // Attach smaller rank tree under root of high rank tree
	    // (Union by Rank)
	    if (subsets[xroot].rank < subsets[yroot].rank)
	        subsets[xroot].parent = yroot;
	    else if (subsets[xroot].rank > subsets[yroot].rank)
	        subsets[yroot].parent = xroot;
	 
	    // If ranks are same, then make one as root and increment
	    // its rank by one
	    else
	    {
	        subsets[yroot].parent = xroot;
	        subsets[xroot].rank++;
	    }
	}

}
class subset{
	int parent;
	int rank;			 
}


class Sort{
	int size;
	Edge [] main;
	Edge [] Aux;
	
	public Sort(Edge[] e){
		size = e.length;
		main =e;
		Aux = new Edge [size];
	}
	
	public Edge[] mSort() throws IOException{
		
		//System.out.println("main size : " + main.length);
		//System.out.println("Aux size : " + Aux.length);
		mergeSortAB(0, main.length-1);
		return main;
	}
	
	private void mergeSortAB(int low, int high) throws IOException{
		if(low<high){
			int middle = low+(high-low)/2;
			mergeSortAB(low, middle);
			mergeSortAB(middle+1, high);
			//System.out.println("main size : " + main.length);
			//System.out.println("Aux size : " + Aux.length);
			mergingABAlter(low, middle, high);
		}
	}
	
	public void mergingABAlter(int low, int middle, int high) throws IOException{
		
		for(int i =low;i<=high;i++){
			Aux[i] = main[i]; 
		}
		
		int i = low;
		int j = middle+1;
		int k = low;		
		while(i<=middle && j<=high ){	
			if(Aux[i]!=null && Aux[j]!=null){
				//System.out.println("Compared : " + Aux[i] + " and " + Aux[j]);
				if (Aux[i].weight<=Aux[j].weight){
					main[k]= Aux[i];
					i++;
				}
				else{
					main[k]= Aux[j];
					j++;
				}
			}
			
								
			k++;
		}
		while(i<=middle){
			main[k]=Aux[i];
			k++;
			i++;			
		}
		while(j<=high){
			main[k]=Aux[j];
			k++;
			j++;			
		}
		//DisplayArray(numbers);
		//DisplayArray(Aux);
				
	}
	
	
}

//
//
//class minHeapNode{
//	int v;
//	//	int u;
//	int key;
//}
//
//class minHeap{
//	int size;      // Number of heap nodes present currently
//	int capacity;  // Capacity of min heap
//	int [] pos;     // This is needed for decreaseKey()
//	int posCounter;
//	minHeapNode []array;
//
//	public minHeapNode newMinHeapNode(int v, int key)
//	{
//		minHeapNode minH = new minHeapNode();               
//		minH.v = v;
//		minH.key = key;
//		return minH;
//	}
//	public void insert(minHeap hp,minHeapNode mhn){
//
//		//System.out.println("Sumit " +  mhn.v + "  " + mhn.key);
//		hp.array[size]=mhn;
//		hp.pos[mhn.v] = size;
//
//		size++;
//		hp.decreaseKey(hp, mhn.v, mhn.key);
//	}
//
//	public minHeap createMinHeap(int capacity)
//	{
//		minHeap minH = new minHeap();
//		minH.pos = new int[capacity];
//		minH.size = 0;
//		//  capacity = capacity*10;
//		minH.capacity = capacity;
//		// System.out.println("Capacity " + capacity);
//		minH.array = new minHeapNode[capacity];             
//		return minH;
//	}
//
//	// A utility function to swap two nodes of min heap. Needed for min heapify
//	void swapMinHeapNode(minHeapNode a, minHeapNode b)
//	{
//		System.out.println( " a " + a.key + " b " + b.key);
//		minHeapNode t = a;
//		a = b;
//		b = t;
//		System.out.println( " a " + a.key + " b " + b.key);
//	}
//	public void displayHeap(minHeap hp){
//		for(int i=0;i<hp.size;i++){
//			// System.out.println("  " + hp.array[i].v + "  " + hp.array[i].key);
//		}
//	}
//
//	public void minHeapify(minHeap minH, int idx)
//	{
//		int smallest, left, right;
//		smallest = idx;
//		left = 2 * idx + 1;
//		right = 2 * idx + 2;
//
//		if (left < minH.size &&
//				minH.array[left].key < minH.array[smallest].key )
//			smallest = left;
//
//		if (right < minH.size &&
//				minH.array[right].key < minH.array[smallest].key )
//			smallest = right;
//
//		if (smallest != idx)
//		{
//			// The nodes to be swapped in min heap
//			minHeapNode smallestNode = minH.array[smallest];
//			minHeapNode idxNode = minH.array[idx];
//
//			// Swap positions
//			minH.pos[smallestNode.v] = idx;
//			minH.pos[idxNode.v] = smallest;
//
//			// Swap nodes
//			// swapMinHeapNode(minH.array[smallest], minH.array[idx]);
//			minHeapNode tt = minH.array[smallest];
//			minH.array[smallest]=minH.array[idx];
//			minH.array[idx]=tt;
//			minHeapify(minH, smallest);
//		}
//	}
//
//	// A utility function to check if the given minHeap is ampty or not
//	public boolean isEmpty(minHeap minH)
//	{
//		return minH.size == 0;
//	}
//
//	// Standard function to extract minimum node from heap
//	public  minHeapNode extractMin(minHeap minH)
//	{
//		if (isEmpty(minH))
//			return null;
//
//		// Store the root node
//		minHeapNode root = minH.array[0];
//
//		// Replace root node with last node
//		//System.out.println("ex min  " + (minH.size - 1));
//		minHeapNode lastNode = minH.array[minH.size - 1];
//		minH.array[0] = lastNode;
//
//		// Update position of last node
//		minH.pos[root.v] = minH.size-1;
//		minH.pos[lastNode.v] = 0;
//
//		// Reduce heap size and heapify root
//		--minH.size;
//		minHeapify(minH, 0);
//
//		return root;
//	}
//
//	// Function to decreasy key value of a given vertex v. This function
//	// uses pos[] of min heap to get the current index of node in min heap
//	void decreaseKey(minHeap minH, int v, int key)
//	{
//		// Get the index of v in  heap array
//		int i = minH.pos[v];
//
//		//  System.out.println(" position " + i + " key " + key + " index " + v);
//		// Get the node and update its key value
//		minH.array[i].key = key;
//
//		// Travel up while the complete tree is not hepified.
//		// This is a O(Logn) loop
//		while (i>0 && minH.array[i].key < minH.array[(i - 1) / 2].key)
//		{
//			// Swap this node with its parent
//			minH.pos[minH.array[i].v] = (i-1)/2;
//			minH.pos[minH.array[(i-1)/2].v] = i;
//			// System.out.println( " minH.array[i] " + minH.array[i].key + " minH.array[(i - 1) / 2] " + minH.array[(i - 1) / 2].key);
//			minHeapNode t = minH.array[(i - 1) / 2];
//			minH.array[(i - 1) / 2] = minH.array[i];
//			minH.array[i] = t;
//
//			//System.out.println( " minH.array[i] " + minH.array[i].key + " minH.array[(i - 1) / 2] " + minH.array[(i - 1) / 2].key);
//			// move to parent index
//			i = (i - 1) / 2;
//			//System.out.println( " index " + i);
//		}
//	}
//
//	// A utility function to check if a given vertex
//	// 'v' is in min heap or not
//	public Boolean isInMinHeap(minHeap minH, int v)
//	{
//		if (minH.pos[v] < minH.size)
//			return true;
//		return false;
//	}
//
//}
//
//
//
