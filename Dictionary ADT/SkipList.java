
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class SkipList
{
	public static long j=0L;
	static long startTime;
	static long endTime;
	private class Node
	{
		public long key;
		public long value;
		public long level;
		public Node next;
		public Node down;

		public Node(long key, long value, long level, Node next, Node down)
		{
			this.key = key;
			this.value = value;
			this.level = level;
			this.next = next;
			this.down = down;
		}
	}

	private Node _head;
	private Random _random;
	private static long _size;
	private double _p;

	private long _level()
	{
		long level = 0;
		while (level <= _size && _random.nextDouble() < _p) {
			level++;
		}

		return level;
	}

	public SkipList()
	{
		_head = new Node(0, 0, 0, null, null);
		_random = new Random();
		_size = 0;
		_p = 0.5;
	}

	public void add(long key, long value)
	{
		long level = _level();
		if (level > _head.level) {
			_head = new Node(0, 0, level, null, _head);
		}

		Node cur = _head;
		Node last = null;

		while (cur != null) {
			if (cur.next == null || cur.next.key>key) {
				if (level >= cur.level) {
					Node n = new Node(key, value, cur.level, cur.next, null);
					if (last != null) {
						last.down = n;
					}

					cur.next = n;
					last = n;
				}

				cur = cur.down;
				continue;
			} else if (cur.next.key==key) {
				cur.next.value = value;
				return;
			}

			cur = cur.next;
		}

		_size++;
	}

	public boolean containsKey(long key)
	{
		return get(key) != 0;
	}

	public long remove(long key)
	{
		long value = 0;
		boolean delCondition = false;
		Node cur = _head;
		while (cur != null) {
			if (cur.next == null || cur.next.key>=key) {
				if (cur.next != null && cur.next.key==key) {
					value = cur.next.value;
					cur.next = cur.next.next;
					delCondition = true;
				}
				cur = cur.down;
				continue;
			}
			cur = cur.next;
		}
		if(delCondition == true)
			_size--;
		return value;
	}

	public long removeValue(long value)
	{
		Node cur = _head;
		while (cur.down != null)
			cur = cur.down;
		cur=cur.next;
		while (cur != null) 
		{
			if (cur.value==value) 
			{
				remove(cur.key);
				j++;
			}
			cur=cur.next;
		}
		return value;
	}
	public void display()
	{
		Node cur = _head;
		cur = cur.next;
		while(cur != null)
		{
			System.out.println(cur.key+", "+cur.value);
			cur = cur.next;
		}
	}

	public long get(long key)
	{
		Node cur = _head;
		while (cur != null) {
			if (cur.next == null || cur.next.key>key) {
				cur = cur.down;
				continue;
			} else if (cur.next.key==key) {
				return cur.next.value;
			}

			cur = cur.next;
		}

		return 0;
	}

	public long findMax()
	{
		Node cur = _head;
		while (cur.next != null)
			cur = cur.next;
		while (cur.down != null)
			cur = cur.down;
		while (cur.next != null)
			cur = cur.next;
		return cur.value;
	}

	public long findMin()
	{
		Node cur = _head;
		while (cur.down != null)
			cur = cur.down;
		return cur.next.value;
	}

	public static void main(String args[])throws IOException
	{
		long x=0;
		SkipList t=new SkipList();
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Skip List Implementation");
		System.out.println("Enter file path..");
		String filepath = in.readLine();
		BufferedReader input = new BufferedReader (new FileReader(filepath));
		String s;
		startTime = System.currentTimeMillis();
		while((s=input.readLine())!=null)
		{
			if(s.startsWith("Insert"))
			{
				String s1;
				long a=0, b=0;
				int i=7;
				while(s.charAt(i)!=' ')
				{
					i++;
				}
				s1=s.substring(7, i);
				a=Long.parseLong(s1, 10);
				s1=s.substring(i+1, s.length());
				b=Long.parseLong(s1, 10);
				t.add(a, b);
			}
			else if(s.startsWith("FindMin"))
			{
				x += t.findMin();
			}
			else if(s.startsWith("FindMax"))
			{
				x += t.findMax();
			}
			else if(s.startsWith("Size"))
			{
				x += _size;
			}
			else if(s.startsWith("Remove "))
			{
				String s1;
				long a=0;
				int i=7;
				s1=s.substring(i, s.length());
				a=Long.parseLong(s1, 10);
				t.remove(a);
			}
			else if(s.startsWith("RemoveValue"))
			{
				String s1;
				long a=0;
				int i=12;
				s1=s.substring(i, s.length());
				a=Long.parseLong(s1, 10);
				t.removeValue(a);
				x += j;
				j=0;
			}
			else if(s.startsWith("Find "))
			{
				String s1;
				long a=0;
				int i=5;
				s1=s.substring(i, s.length());
				a=Long.parseLong(s1, 10);
				t.get(a);
			}
		}
		System.out.println(x);
		endTime = System.currentTimeMillis();
		System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
}