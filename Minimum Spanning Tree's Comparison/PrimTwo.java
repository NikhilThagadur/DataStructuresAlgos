
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PrimTwo {
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
        System.out.println("Prim's Algorithm Implementation 2--- Eager Version");
        System.out.println("Enter file path..");
		String filepath = in.readLine();
//		String filepath = "C:\\in.100K";
//		String filepath = "C:\\testp6.txt";
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
    			g.createGraph(g,V);
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
    			g.addEdge(g, (int)a-1, (int)b-1, (int)c);
    		}
    		
    		
    		
    	}
    	//System.out.println(answer);
    	g.PSTMain(g);
    	endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");
    	
    }
}
//node structure in adjacency list
class AdjListNode
{
	int dest;
	int weight;
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
    	adn =  al.newAdjNode(source, weight);
    	adn.next = gph.array[destination].head;
    	gph.array[destination].head = adn;
    }
    
    public void PSTMain(Graph gph){
    	int V = gph.V;
    	int [] parent =new int[V];
    	int [] key =new int[V];
    	int MST=0;
    	
    	minHeap hp = new minHeap();
    	hp.createMinHeap(V);
    	hp.array = new minHeapNode[V];
    	hp.pos = new int[V];
    	for(int i =1;i<V;i++){
    		parent[i]=-1;
    		key[i]=1000000000;
    		//hp.array[i]=  new minHeapNode();
    		hp.array[i] =hp.newMinHeapNode(i, key[i]);
    		hp.pos[i]=i;
    	}
    	
    	key[0]=0;
    	hp.array[0]=hp.newMinHeapNode(0, key[0]);
    	hp.pos[0]=0;
    	hp.size = V;
//    	hp.displayHeap();
    	//System.out.println("prim starting" + hp.size);
    	while (!hp.isEmpty(hp)){
    		minHeapNode hpn = hp.extractMin(hp);    		
    		int u = hpn.v;
    		//System.out.println("extracted vertex " + u);
    		//System.out.println("adding +  " + hpn.key + " with vertex " + u);
    		MST +=hpn.key;
    		AdjListNode pc = gph.array[u].head;
    		
    		while(pc!=null){
    			//System.out.println("Here");
    			int x = pc.dest;
    			//System.out.println("node " + x + " distance " + pc.weight);
    			//System.out.println("comparing  " + pc.weight + "  " + key[x]);
    			if(hp.isInMinHeap(hp, x) && pc.weight<key[x]){
    				//System.out.println("Replacing");
    				key[x] = pc.weight;
    				parent[x]= u;
    				hp.decreaseKey(hp, x, key[x]);
    				//hp.change(x,key[x]);
    				hp.displayHeap(hp);
    			}
    			pc=pc.next;
    		}
    		
    		//hp.displayHeap();
    	}
    	System.out.println("MST " + MST);
    }    
    
}


class minHeapNode{
	int v;
	int key;
}

class minHeap{
	int size;      // Number of heap nodes present currently
    int capacity;  // Capacity of min heap
    int [] pos;     // This is needed for decreaseKey()
    minHeapNode []array= new minHeapNode[100];
    
    public minHeapNode newMinHeapNode(int v, int key)
    {
    	minHeapNode minH = new minHeapNode();               
    	minH.v = v;
        minH.key = key;
        return minH;
    }
    
    public minHeap createMinHeap(int capacity)
    {
        minHeap minH = new minHeap();
        minH.pos = new int[capacity];
        minH.size = 0;
        minH.capacity = capacity;
        minH.array = new minHeapNode[capacity];             
        return minH;
    }
     
    // A utility function to swap two nodes of min heap. Needed for min heapify
    void swapMinHeapNode(minHeapNode a, minHeapNode b)
    {
    	System.out.println( " a " + a.key + " b " + b.key);
        minHeapNode t = a;
        a = b;
        b = t;
        System.out.println( " a " + a.key + " b " + b.key);
    }
     public void displayHeap(minHeap hp){
    	 for(int i=0;i<hp.size;i++){
    		// System.out.println("  " + hp.array[i].key);
    	 }
     }
    
    public void minHeapify(minHeap minH, int idx)
    {
        int smallest, left, right;
        smallest = idx;
        left = 2 * idx + 1;
        right = 2 * idx + 2;
     
        if (left < minH.size &&
        		minH.array[left].key < minH.array[smallest].key )
          smallest = left;
     
        if (right < minH.size &&
        		minH.array[right].key < minH.array[smallest].key )
          smallest = right;
     
        if (smallest != idx)
        {
            // The nodes to be swapped in min heap
            minHeapNode smallestNode = minH.array[smallest];
            minHeapNode idxNode = minH.array[idx];
     
            // Swap positions
            minH.pos[smallestNode.v] = idx;
            minH.pos[idxNode.v] = smallest;
     
            // Swap nodes
           // swapMinHeapNode(minH.array[smallest], minH.array[idx]);
            minHeapNode tt = minH.array[smallest];
            minH.array[smallest]=minH.array[idx];
            minH.array[idx]=tt;
            minHeapify(minH, smallest);
        }
    }
     
    // A utility function to check if the given minHeap is ampty or not
    public boolean isEmpty(minHeap minH)
    {
        return minH.size == 0;
    }
     
    // Standard function to extract minimum node from heap
    public  minHeapNode extractMin(minHeap minH)
    {
        if (isEmpty(minH))
            return null;
     
        // Store the root node
        minHeapNode root = minH.array[0];
     
        // Replace root node with last node
        minHeapNode lastNode = minH.array[minH.size - 1];
        minH.array[0] = lastNode;
     
        // Update position of last node
        minH.pos[root.v] = minH.size-1;
        minH.pos[lastNode.v] = 0;
     
        // Reduce heap size and heapify root
        --minH.size;
        minHeapify(minH, 0);
     
        return root;
    }
     
    // Function to decreasy key value of a given vertex v. This function
    // uses pos[] of min heap to get the current index of node in min heap
    void decreaseKey(minHeap minH, int v, int key)
    {
        // Get the index of v in  heap array
        int i = minH.pos[v];
      //  System.out.println(" position " + i + " key " + key + " index " + v);
        // Get the node and update its key value
        minH.array[i].key = key;
     
        // Travel up while the complete tree is not hepified.
        // This is a O(Logn) loop
        while (i>0 && minH.array[i].key < minH.array[(i - 1) / 2].key)
        {
            // Swap this node with its parent
            minH.pos[minH.array[i].v] = (i-1)/2;
            minH.pos[minH.array[(i-1)/2].v] = i;
            //System.out.println( " minH.array[i] " + minH.array[i].key + " minH.array[(i - 1) / 2] " + minH.array[(i - 1) / 2].key);
            minHeapNode t = minH.array[(i - 1) / 2];
            minH.array[(i - 1) / 2] = minH.array[i];
            minH.array[i] = t;
            
            //System.out.println( " minH.array[i] " + minH.array[i].key + " minH.array[(i - 1) / 2] " + minH.array[(i - 1) / 2].key);
            // move to parent index
            i = (i - 1) / 2;
            //System.out.println( " index " + i);
        }
    }
     
    // A utility function to check if a given vertex
    // 'v' is in min heap or not
    public Boolean isInMinHeap(minHeap minH, int v)
    {
       if (minH.pos[v] < minH.size)
         return true;
       return false;
    }
     
}

