// CS 0445 Fall 2021
// Assignment 2 Test Program B
// This program should run as is with your MyStringBuilder class.
// Because this is timing operations the output will vary greatly based on the
// system being used.  However, note the trends of the times as N increases.
//
// Note: The append() and insert() methods must create new nodes within the linked
// list, and for very large N (ex: 10000000) could be slowed by system allocation 
// of memory for the nodes.

import java.util.*;

public class Assig2B
{
	private MyStringBuilder s1;
	static int [] vals = {100000, 1000000, 10000000};
	
	public Assig2B()
	{
		for (int N: vals)
		{	
			System.out.println("Testing N = " + N + ":");
			testAppend(N);
			testDeleteBack();
			testInsertFront(N);
			testDeleteFront();
			System.out.println();
		}
	}
	
	// Appending a character should be very fast (constant time) since the end of the 
	// list is accessible as the previous of the front.  This code demonstrates that.
	public void testAppend(int N)
	{
		s1 = new MyStringBuilder("");
		long start = System.nanoTime();
		for (int i = 0; i < N; i++)
			s1.append("A");
		long stop = System.nanoTime();
		long delta = stop - start;
		double ave = (double) delta / N;
		System.out.println("Testing append: ");
		System.out.println("\t\tTotal time: " + delta + " ns for " + N + " appends");
		System.out.println("\t\tTime per append: " + ave + " ns");
	}
	
	// Due to the special case of deleting from the back of the list (going backward in
	// the circular list) the deleteCharAt() method at the end of the list should be very
	// very fast (constant time).  This code demonstrates that. 
	public void testDeleteBack()
	{
		int N = s1.length();
		long start = System.nanoTime();
		while (s1.length() > 0)
			s1.deleteCharAt(s1.length()-1);
		long stop = System.nanoTime();
		long delta = stop - start;
		double ave = (double) delta / N;
		System.out.println("Testing delete from back: ");
		System.out.println("\t\tTotal time: " + delta + " ns for " + N + " deletes");
		System.out.println("\t\tTime per delete: " + ave + " ns");
	}
	
	// Inserting at the front should clearly be very fast (constant time) in a linked list.
	public void testInsertFront(int N)
	{
		s1 = new MyStringBuilder("");
		long start = System.nanoTime();
		for (int i = 0; i < N; i++)
			s1.insert(0, "A");
		long stop = System.nanoTime();
		long delta = stop - start;
		double ave = (double) delta / N;
		System.out.println("Testing insert(0): ");
		System.out.println("\t\tTotal time: " + delta + " ns for " + N + " inserts");
		System.out.println("\t\tTime per insert: " + ave + " ns");
	}

	// Deleting at the front should also be very fast (constant time) in a linked list.
	public void testDeleteFront()
	{
		int N = s1.length();
		long start = System.nanoTime();
		while (s1.length() > 0)
			s1.deleteCharAt(0);
		long stop = System.nanoTime();
		long delta = stop - start;
		double ave = (double) delta / N;
		System.out.println("Testing delete from front: ");
		System.out.println("\t\tTotal time: " + delta + " ns for " + N + " deletes");
		System.out.println("\t\tTime per delete: " + ave + " ns");
	}	
	
	
	public static void main(String [] args)
	{
		Assig2B E = new Assig2B();
	}
}


