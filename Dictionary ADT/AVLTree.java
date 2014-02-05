
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AVLTree
{
	public static long size;
	static long startTime;
	static long endTime;
	private class AVLNode
	{
		long value,key;	//value to be stored
		AVLNode left, right;	// left and right subtrees
		long height;	//height of the node	
		public AVLNode(long key,long value)
		{
			//call our other constructor
			this(key, value, null, null);
		}	
		public AVLNode(long key,long value, AVLNode right, AVLNode left)
		{
			this.value = value;
			this.key = key;
			this.right = right;
			this.left = left;
			height = 0;
		}
		void resetHeight()
		{
			long leftHeight = AVLTree.getHeight(left);
			long rightHeight = AVLTree.getHeight(right);
			height = 1 + Math.max(leftHeight, rightHeight);
		}
	}
	
	private void inOrder(AVLNode localRoot)
	{
		if(localRoot != null)
			{
				size++;
				inOrder(localRoot.left);
				//System.out.print(localRoot.value + " ");
				inOrder(localRoot.right);
			}
	}
//	
	private long removeValue(long value){
		removeValue(root, value);
		return removeCount;
	}
	
	private void removeValue(AVLNode localRoot, long value){
		if(localRoot != null)
		{			
			removeValue(localRoot.left,value);
			//System.out.print(localRoot.value + " ");
			if(localRoot.value==value){
				delete(localRoot.key);
				removeCount++;
			}
			removeValue(localRoot.right,value);
		}
	}
	
	
	//Continuing on with the remaining AVL functions
	public static AVLNode root = null;	
	public static long removeCount=0;// Root of the AVL tree
	
	public static void main(String args[]) throws NumberFormatException, IOException{
		
		
		AVLTree avl = new AVLTree();
		
        long answer=0;
        long insertcount=0;
        BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("AVL Tree Implementation");
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
    			avl.add(a, b);
    			insertcount++;
    		}
    		else if(s.startsWith("FindMin")){
    			answer += avl.findMin();
    		}
    		else if(s.startsWith("FindMax")){
    			answer += avl.findMax();
    		}
    		else if(s.startsWith("Size")){
    			size = 0;
    			avl.inOrder(root);
    			answer += size;
    			//System.out.println(size);
    		}
    		else if(s.startsWith("Remove ")){
    			String s1;
    			long a=0;
    			int i=7;
    			s1=s.substring(i, s.length());
    			a=Long.parseLong(s1, 10);
    			avl.delete(a);
    		}
    		else if(s.startsWith("Find ")){
    			String s1;
    			long a=0;
    			int i=5;
    			s1=s.substring(i, s.length());
    			a=Long.parseLong(s1, 10);
    			avl.find(a);
    			//System.out.println(avl.find(a));
    		}
    	else if(s.startsWith("RemoveValue")){
    			String s1;
	    		long a=0;
	    		int i=12;
	    		s1=s.substring(i, s.length());
	    		a=Long.parseLong(s1, 10);
	    		removeCount=0;
	    		avl.removeValue(a);
	    		answer +=removeCount;	    				
    		}
    }
    	System.out.println(answer);
    	endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");
//
	}
	static long getHeight(AVLNode tree)
	{
		if(tree == null) return -1;
		else return tree.height;
	}
	public boolean add(long key,long value)
	{
		//System.out.println("keyyyy" + key);
		root = add(root,key,value);
		return true;
	}
	
	public boolean isEmpty()
	{
		return root == null;
	}
	
	private AVLNode add(AVLNode bTree, long key, long value)
	{
		
		if( bTree == null ){
            return new AVLNode( key, value);
		}
		//System.out.println( bTree.key + "  " + bTree.value);
		if(bTree.key == key){
			//System.out.println("override");
			bTree.value = value;
			return bTree ;
		}
        if(key< bTree.key){
        	//System.out.println("Left " + key);
        	bTree.left = add(bTree.left,key,value);        	
        }
        else if(key>bTree.key){
        	//System.out.println("Right");
        	bTree.right = add(bTree.right,key,value);
        }            
        return balancenew( bTree );		
        //return bTree ;
	}
	
	private static final int ALLOWED_IMBALANCE = 1;

	// Assume t is either balanced or within one of being balanced
	private AVLNode balancenew( AVLNode t ){
		if( t == null )
		return t;	
		if( getHeight( t.left ) - getHeight( t.right ) > ALLOWED_IMBALANCE )
		if( getHeight( t.left.left ) >= getHeight( t.left.right ) )
		t = rotateWithLeftChild( t );
		else
		t = doubleWithLeftChild( t );
		else
		if( getHeight( t.right ) - getHeight( t.left ) > ALLOWED_IMBALANCE )
		if( getHeight( t.right.right ) >= getHeight( t.right.left ) )
		t = rotateWithRightChild( t );
		else
		t = doubleWithRightChild( t );	
		t.height = Math.max( getHeight( t.left ), getHeight( t.right ) ) + 1;
		return t;
	}
	
	private AVLNode rotateWithLeftChild( AVLNode k2 ){
		AVLNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max( getHeight( k2.left ), getHeight( k2.right ) ) + 1;
		k1.height = Math.max( getHeight( k1.left ), k2.height ) + 1;
		return k1;
	}

	private AVLNode rotateWithRightChild( AVLNode k1 ){
		AVLNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max( getHeight( k1.left ), getHeight( k1.right ) ) + 1;
		k2.height = Math.max( getHeight( k2.right ), k1.height ) + 1;
		return k2;
	}

	private AVLNode doubleWithLeftChild( AVLNode k3 )	{
		k3.left = rotateWithRightChild( k3.left );
		return rotateWithLeftChild( k3 );
	}
	private AVLNode doubleWithRightChild( AVLNode k1 )	{
		k1.right = rotateWithLeftChild( k1.right );
		return rotateWithRightChild( k1 );
	}

	public long  findMin(){
		AVLNode tmp = findMin(root);
		if(tmp==null)
			return 0;
		else
			return tmp.value;
	}
	public long find(long key){
		AVLNode tmp = find(root,key);
		if(tmp==null)
			return 0;
		else
			return tmp.value;
	}
	 private AVLNode find(AVLNode bTree, long key ){
	        while( bTree != null ){
	        	if( key<bTree.key )
	        		bTree = bTree.left;
	            else if(key>bTree.key)
	            	bTree = bTree.right;
	            else
	                return bTree;    // Match
	        }
	        return null;   // No match
	    }
	
	private AVLNode findMin(AVLNode bTree){
		if( bTree == null )
            return bTree;

        while( bTree.left != null )
        	bTree = bTree.left;
        return bTree;
	}
	public long delete(long key){
		long val = find(key);
		root = delete(root,key);	
		return val;
	}
	public AVLNode delete(AVLNode bTree, long key){		
		if( bTree == null )
            return bTree;   // Item not found; do nothing
            
        if( key < bTree.key )
        	bTree.left = delete( bTree.left,key);
        else if( key > bTree.key ){
        	//System.out.println("right");
        	bTree.right = delete(bTree.right,key);
        }
        else if( bTree.left != null && bTree.right != null ){ // Two children
           	//System.out.println("2 bacche");
        	//deletedNode = bTree;
           	AVLNode smallestKeyNode =findMin( bTree.right );
        	bTree.key = smallestKeyNode.key;
        	bTree.value = smallestKeyNode.value;        	
        	bTree.right = delete(bTree.right,bTree.key);
        }
        else
            bTree = ( bTree.left != null ) ? bTree.left : bTree.right;
        return balancenew( bTree );
	}
	public long  findMax(){
		AVLNode tmp = findMax(root);
		if(tmp==null)
			return 0;
		else
			return tmp.value;
	}
//	
	private AVLNode findMax(AVLNode bTree){
		if( bTree == null )
            return bTree;

        while( bTree.right != null )
        	bTree = bTree.right;
        return bTree;
	}	
}
