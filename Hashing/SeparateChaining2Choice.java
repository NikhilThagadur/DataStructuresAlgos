
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
public class SeparateChaining2Choice
{
	private static long findFail;
	/**
	 * Construct the hash table.
	 */
	public SeparateChaining2Choice( )
	{
		this( DEFAULT_TABLE_SIZE );
	}

	/**
	 * Construct the hash table.
	 * @param size approximate table size.
	 */
	public SeparateChaining2Choice( int size )
	{
		theLists = new LinkedList[ nextPrime( size ) ];
		for( int i = 0; i < theLists.length; i++ )
			theLists[ i ] = new LinkedList<>( );
	}

	/**
	 * Insert into the hash table. If the item is
	 * already present, then do nothing.
	 * @param x the item to insert.
	 */
	public void insert( long x )
	{
		List whichList = theLists[ myhash( x ) ];
		List whichList2 = theLists[ myhash2( x )];
		if( !whichList.contains( x ) && !whichList2.contains( x ))
		{
			if(whichList.size()<=whichList2.size())
			{
				whichList.add( x );
			}
			else if(whichList.size()>whichList2.size())
			{
				whichList2.add( x );
			}
			// Rehash; see Section 5.5
			if( ++currentSize > theLists.length )
				rehash( );
		}
	}

	/**
	 * Remove from the hash table.
	 * @param x the item to remove.
	 */
	public void remove(long x )
	{
		List whichList = theLists[ myhash( x ) ];
		List whichList2 = theLists[ myhash2( x )];
		if( whichList.contains( x ))
		{
			whichList.remove( x );
			currentSize--;
		}
		else if(  whichList2.contains( x ))
		{
			whichList2.remove( x );
			currentSize--;
		}
	}

	/**
	 * Find an item in the hash table.
	 * @param x the item to search for.
	 * @return true if x isnot found.
	 */
	public boolean contains(long x )
	{
		List whichList = theLists[ myhash( x ) ];
		List whichList2 = theLists[ myhash2( x ) ];
		if(!whichList.contains( x ) && !whichList2.contains( x )){
			findFail++;
		}
		return whichList.contains(x) || whichList2.contains(x);
	}

	/**
	 * Make the hash table logically empty.
	 */
	public void makeEmpty( )
	{
		for( int i = 0; i < theLists.length; i++ )
			theLists[ i ].clear( );
		currentSize = 0;    
	}

	/**
	 * A hash routine for String objects.
	 * @param key the String to hash.
	 * @param tableSize the size of the hash table.
	 * @return the hash value.
	 */
	public static int hash( String key, int tableSize )
	{
		int hashVal = 0;

		for( int i = 0; i < key.length( ); i++ )
			hashVal = 37 * hashVal + key.charAt( i );

		hashVal %= tableSize;
		if( hashVal < 0 )
			hashVal += tableSize;

		return hashVal;
	}

	private void rehash( )
	{
		List [ ]  oldLists = theLists;

		// Create new double-sized, empty table
		theLists = new List[ nextPrime( 2 * theLists.length ) ];
		for( int j = 0; j < theLists.length; j++ )
			theLists[ j ] = new LinkedList<>( );

			// Copy table over
			for(int i=0; i<oldLists.length; i++)
			{
				List old = oldLists[i];
				List whichList = theLists[i];
				whichList.add(old.get(i));
			}
	}

	private int myhash(Long x )
	{
		long hashVal = x.hashCode( );

		hashVal %= theLists.length;
		if( hashVal < 0 )
			hashVal += theLists.length;
		return (int)hashVal;
	}

	private int myhash2(Long x )
	{
		long hashVal = x.hashCode( );

		hashVal = 7 - x%7;
		if( hashVal < 0 )
			hashVal += theLists.length;
		return (int)hashVal;
	}


	private static final int DEFAULT_TABLE_SIZE = 10000000;

	/** The array of Lists. */
	private List [ ] theLists; 
	private static int currentSize;

	/**
	 * Internal method to find a prime number at least as large as n.
	 * @param n the starting number (must be positive).
	 * @return a prime number larger than or equal to n.
	 */
	private static int nextPrime(int n )
	{
		if( n % 2 == 0 )
			n++;

		for( ; !isPrime( n ); n += 2 )
			;

		return n;
	}

	/**
	 * Internal method to test if a number is prime.
	 * Not an efficient algorithm.
	 * @param n the number to test.
	 * @return the result of the test.
	 */
	private static boolean isPrime(long n )
	{
		if( n == 2 || n == 3 )
			return true;

		if( n == 1 || n % 2 == 0 )
			return false;

		for( int i = 3; i * i <= n; i += 2 )
			if( n % i == 0 )
				return false;

		return true;
	}


	// Simple main
	public static void main( String [ ] args )throws IOException
	{
		SeparateChaining2Choice H = new SeparateChaining2Choice( );
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Separate Chaining Implementation");
		System.out.println("Enter file path..");
		String filepath = in.readLine();
		BufferedReader input = new BufferedReader (new FileReader(filepath));
		String s;
		long startTime = System.currentTimeMillis();
		while((s=input.readLine())!=null)
		{
			if(s.startsWith("Insert"))
			{
				String s1;
				long a=0;
				int i=7;
				while(s.charAt(i)!=' ')
				{
					i++;
				}
				s1=s.substring(7, i);
				a=Long.parseLong(s1, 10);
				H.insert(a);
			}
			else if(s.startsWith("Remove "))
			{
				String s1;
				long a=0;
				int i=7;
				s1=s.substring(i, s.length());
				a=Long.parseLong(s1, 10);
				H.remove(a);
			}
			else if(s.startsWith("Find "))
			{
				String s1;
				long a=0;
				int i=5;
				s1=s.substring(i, s.length());
				a=Long.parseLong(s1, 10);
				H.contains(a);
			}
		}
		System.out.println("Fail Count " +  findFail);
		long endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}

}
