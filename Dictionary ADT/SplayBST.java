
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


//import ImplementationDS.AVLTree.AVLNode;

public class SplayBST  {

    private static Node root;   // root of the BST
    private static long size;
    private static long removeCount=0;
    private static String removevalues="";
    static long startTime;
	static long endTime;
    // BST helper node data type
    private class Node {
        private long key;            // key
        private long value;        // associated data
        private Node left, right;   // left and right subtrees

        public Node(long key, long value) {
            this.key   = key;
            this.value = value;
        }
    }

    public long find(long key) {
        root = splay(root, key);
        if (key==root.key) return root.value;
        else return 0;
    }    
    
    public long findMin(){    	
    	if( root == null )
            return 0;
    	Node nd = root;    	
        while( nd.left != null ){
        	nd = nd.left;        	
        }
        root = splay(root,nd.key);        
        return root.value;

    }
    
    public long findMax(){
    	if( root == null )
            return 0;
//    	System.out.println("current " + root.key);
    	Node nd = root;
        while( nd.right != null )
        	nd = nd.right;
        root = splay(root,nd.key);
        return root.value;

    }

    public void put(long key, long value) {
        if (root == null) {
            root = new Node(key, value);
            return;
        }
        
        root = splay(root, key);
        // Insert new node at root
        if (key < root.key) {
            Node n = new Node(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }
        // Insert new node at root
        else if (key > root.key) {
            Node n = new Node(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }
        // It was a duplicate key. Simply replace the value
        else if (key == root.key) {
            root.value = value;
        }

    }
    
    
    public long remove(long key) {
    	long delVal=0;
        if (root == null) return 0; // empty tree
        
        root = splay(root, key);
        //System.out.println("RootXXXX  " + root.key + "    " + key);
        //display();        
        if (key == root.key) {
            if (root.left == null) {
            	delVal = root.key;
                root = root.right;
            } 
            else {
            	//System.out.println("Root Right XXXX  " + root.right.key + "    " + key);
                Node x = root.right;
                delVal = root.key;
                //System.out.println("Root Left XXXX  " + root.left.right.value);
                Node nd = root.left;
               // System.out.println("nd  " + nd.key);
                while( nd.right != null )
                	nd = nd.right;
                nd = splay(root.left,nd.key);
                //System.out.println("nd  " + nd.key + "  " );
                root = nd;
                //splay(root, key);
                root.right = x;
            }
        }
        return delVal;
    }
    
    public long removeValue(long value){
    	removeValue(root, value);
    	return removeValueInternal(removevalues);
    }
    
    private long removeValueInternal(String strKeys){
    	long deleteCount =0;
    	String arrTemp[] = strKeys.split(";");
    	for(int i=0;i<arrTemp.length;i++){
    		if(arrTemp[i].trim()!=""){
    			remove(Long.parseLong(arrTemp[i]));
    			deleteCount++;
    		}
    	}
    	return deleteCount;
    }
    private void removeValue(Node localRoot, long value){
		if(localRoot != null)
		{	
			if(removeCount==0){
				removevalues="";
			}
			removeValue(localRoot.left,value);
			//System.out.print(localRoot.value + " ");
			if(localRoot.value==value){
				//System.out.println("keys " + " " + localRoot.key);
				removevalues =  localRoot.key + ";" + removevalues;
				//System.out.print("");
				//remove(localRoot.key);
				removeCount++;
				display();
			}
			removeValue(localRoot.right,value);
		}
	}
    
    private Node splay(Node h, long key) {
        if (h == null) return null;
        if (key < h.key) {
            if (h.left == null) {
                return h;
            }

            if (key < h.left.key) {
                h.left.left = splay(h.left.left, key);
                h = rotateRight(h);
            }
            else if (key > h.left.key) {
                h.left.right = splay(h.left.right, key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }
            
            if (h.left == null) return h;
            else return rotateRight(h);
        }

        else if (key > h.key) {
            if (h.right == null) {
                return h;
            }
            if (key < h.right.key) {
                h.right.left  = splay(h.right.left, key);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            }
            else if (key > h.right.key) {
                h.right.right = splay(h.right.right, key);
                h = rotateLeft(h);
            }
            
            if (h.right == null) return h;
            else return rotateLeft(h);
        }

        else return h;
    }


    public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }
    public int size() {
        return size(root);
    }
    
    private int size(Node x) {
        if (x == null) return 0;
        else return (1 + size(x.left) + size(x.right));
    }
    
    // right rotate
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    // left rotate
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }
    
    private void inOrder(Node localRoot)
	{
		if(localRoot != null)
			{
				size++;
				inOrder(localRoot.left);
				//System.out.print(localRoot.value + " ");
				inOrder(localRoot.right);
			}
	}
    
    public void display(){
    	inOrder(root);
    }
    
    public void rootDisplay(){
    	//System.out.println("Root : " + root.key);
    }

    // test client
    public static void main(String[] args) throws NumberFormatException, IOException {
        SplayBST st1 = new SplayBST();
        long answer=0;
        
        BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("Splay Tree Implementation");
        System.out.println("Enter file path..");
		String filepath = in.readLine();
		BufferedReader input = new BufferedReader (new FileReader(filepath));
    	String s;
    	startTime = System.currentTimeMillis();
    	while((s=input.readLine())!=null){
    		if(s.startsWith("Insert")){
    			String s1;
    			long a=0, b=0;
    			int i=7;
    			while(s.charAt(i)!=' '){
    				i++;
    			}
    			s1=s.substring(7, i);
    			a=Long.parseLong(s1, 10);
    			s1=s.substring(i+1, s.length());
    			b=Long.parseLong(s1, 10);
    			st1.put(a, b);
    		}
    		else if(s.startsWith("FindMin")){
    			answer += st1.findMin();
    		}
    		else if(s.startsWith("FindMax")){
    			answer += st1.findMax();
    		}
    		else if(s.startsWith("Size")){
    			size = 0;
    			st1.display();
    			//System.out.println("Splay " + size);
    			answer += size;
    		}
    		else if(s.startsWith("Remove ")){
    			String s1;
    			long a=0;
    			int i=7;
    			s1=s.substring(i, s.length());
    			a=Long.parseLong(s1, 10);
    			st1.remove(a);
    		}
    		else if(s.startsWith("Find ")){
    			String s1;
    			long a=0;
    			int i=5;
    			s1=s.substring(i, s.length());
    			a=Long.parseLong(s1, 10);
    			//System.out.println(a);
    			//System.out.println(st1.find(a));
    			st1.find(a);
    		}
    	else if(s.startsWith("RemoveValue")){
    			String s1;
	    		long a=0;
	    		int i=12;
	    		s1=s.substring(i, s.length());
	    		a=Long.parseLong(s1, 10);
	    		removeCount=0;
	    		st1.removeValue(a);
	    		//System.out.println(removeCount);
	    		answer +=removeCount;	    				
    		}
    }
    	System.out.println(answer);
    	endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");
    }

}

