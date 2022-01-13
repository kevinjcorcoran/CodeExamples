/*
Kevin Corcoran
kjc100@pitt.edu
ID: 3855660
CS 0445 Fall 2021
Assignment 2: MyStringBuilder Class
SEE METHOD DESCRIPTIONS FOR MORE IMPLEMENTATION DETAILS
-	Allows the user to create and perform operations on MyStringBuilders using
 	a circular, doubly linked list.
-	MyStringBuilders are built using the inner CNode class, which creates nodes
	for each character. CNodes have references to their data(character), the next
	node, and the previous node.
-	MyStringBuilders only have a references to the first node in the list: CNode
	firstC, and the length of the list: int length.
-	MyStringBuilders can be constructed as empty MyStringBuilders, or by using
 	String or char [] arguments. Copies can be made using a MyStringBuilder
	argument.
-	MyStringBuilders can be converted to String using the toString method.
-	MyStringBuilders can be appended using MyStringBuilder, String, char[], and
	char arguments.
-	Each character in a MyStringBuilder can be accessed using the charAt method,
	which takes an int argument representing the "index" of the character.
-	Portions of a MyStringBuilder can be deleted using the delete method, while
	single characters can be deleted using the deleteCharAt method.
-	Users can search if a MyStringBuilder contains a certain substring using the
	indexOf method.
-	Strings and characters can be added to any position in a MyStringBuilder
	using the insert methods.
-	A portion of a MyStringBuilder can be replaced with another String using the
	replace method.
-	Portions of a MyStringBuilder can be accessed using the substring method.
-	The revString method returns the characters of a MyStringBuilder in reverse
	as a String.
-	If a MyStringBuilder includes multiple words separated my spaces, each word
	can be accessed using the getWord method.
-	Two MyStringBuilders can be compared using the equals method.
-	The length of a MyStringBuilder can be accessed using the length method.
*/

public class MyStringBuilder
{
	private CNode firstC;	// Reference to the first node/front in the list

	private int length;  	// number of characters in the list

	// Create a new MyStringBuilder which contains the contents of the
	// String argument. If s is null or of length 0, an empty MyStringBuilder is
	// made.
	public MyStringBuilder(String s)
	{
		if (s == null || s.length() == 0)  // special case for empty String argument
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(s.charAt(0));  // create first node
			length = 1;
			CNode currNode = firstC;
			// Iterate through remainder of the String, creating a new
			// node at the end of the list for each character.  Note
			// how the nodes are being linked and the current reference
			// being moved down the list.
			for (int i = 1; i < s.length(); i++)
			{
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				newNode.prev = currNode;
				currNode = newNode;
				length++;
			}
			// Connect end back to front to make list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Create a new MyStringBuilder initialized with the chars in array s
	public MyStringBuilder(char [] s)
	{
		if (s == null || s.length == 0)  // special case for empty array
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(s[0]);  // create first node
			length = 1;
			CNode currNode = firstC;
			// Iterate through remainder of the array, creating a new
			// node at the end of the list for each character.  Note
			// how the nodes are being linked and the current reference
			// being moved down the list.
			for (int i = 1; i < s.length; i++)
			{
				CNode newNode = new CNode(s[i]);
				currNode.next = newNode;
				newNode.prev = currNode;
				currNode = newNode;
				length++;
			}
			// Connect end back to front to make list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Copy constructor -- make a new MyStringBuilder from an old one.
	public MyStringBuilder(MyStringBuilder old)
	{
		if (old == null || old.length == 0)  // special case for empty old
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(old.firstC.data);  // create first node
			length = 1;
			CNode currNode = firstC;
			CNode oldCurrNode = old.firstC;
			// Iterate through remainder of the MyStringBuilder, creating a new
			// node at the end of the list for each character.  Note
			// how the nodes are being linked and the current reference
			// being moved down the list.
			for (int i = 1; i < old.length; i++)
			{
				CNode newNode = new CNode(oldCurrNode.next.data);
				currNode.next = newNode;
				newNode.prev = currNode;
				currNode = newNode;
				oldCurrNode = oldCurrNode.next;
				length++;
			}
			// Connect end back to front to make list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Create a new empty MyStringBuilder
	public MyStringBuilder()
	{
		firstC = null;
		length = 0;
	}

	// Return the entire contents of the current MyStringBuilder as a String
	public String toString()
	{
		char [] c = new char[length];
		int i = 0;
		CNode currNode = firstC;
		while (i < length)
		{
			c[i] = currNode.data;
			i++;
			currNode = currNode.next;
		}
		return new String(c);
	}

	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the new MyStringBuilder.
	// If b is empty, the original MyStringBuilder is returned.
	// The method first determines where to start the append:
	// If the current MyStringBuilder is empty, the first character of b is set
	// as the first node.
	// If the current MyStringBuilder only has one character, the append starts
	// after the first node.
	// Otherwise, the append starts after the last node (firstC.prev)
	public MyStringBuilder append(MyStringBuilder b)
	{
		if (b == null || b.length == 0)  // special case for empty String
		{
			return this;
		}

		CNode bNode = b.firstC;
		CNode newNode = new CNode(bNode.data);
		CNode lastNode = firstC;

		if (length == 0)
		{
			firstC = newNode;
			length++;
		}
		else if (length == 1)
		{
			lastNode.next = newNode;
			newNode.prev = lastNode;
			length++;
		}
		else
		{
			lastNode = firstC.prev;
			lastNode.next = newNode;
			newNode.prev = lastNode;
			length++;
		}

		lastNode = newNode;

		for (int i = 1; i < b.length; i++)
		{
			bNode = bNode.next;
			newNode = new CNode(bNode.data, null, lastNode);
			lastNode.next = newNode;
			lastNode = newNode;
			length++;
		}
		// Connect end back to front to make list circular
		lastNode.next = firstC;
		firstC.prev = lastNode;

		return this;

	}


	// Append String s to the end of the current MyStringBuilder, and return the
	// new MyStringBuilder.
	// If s is empty, the original MyStringBuilder is returned.
	// The method first determines where to start the append:
	// If the current MyStringBuilder is empty, the first character of s is set
	// as the first node.
	// If the current MyStringBuilder only has one character, the append starts
	// after the first node.
	// Otherwise, the append starts after the last node (firstC.prev)
	public MyStringBuilder append(String s)
	{
		if (s == null || s.length() == 0)  // special case for empty String
		{
			return this;
		}

		CNode newNode = new CNode(s.charAt(0));
		CNode lastNode = firstC;

		if (length == 0)
		{
			firstC = newNode;
			length++;
		}
		else if (length == 1)
		{
			lastNode.next = newNode;
			newNode.prev = lastNode;
			length++;
		}
		else
		{
			lastNode = firstC.prev;
			lastNode.next = newNode;
			newNode.prev = lastNode;
			length++;
		}

		lastNode = newNode;

		for (int i = 1; i < s.length(); i++)
		{
			newNode = new CNode(s.charAt(i), null, lastNode);
			lastNode.next = newNode;
			lastNode = newNode;
			length++;
		}
		// Connect end back to front to make list circular
		lastNode.next = firstC;
		firstC.prev = lastNode;
		return this;

	}

	// Append char [] c to the end of the current MyStringBuilder, and return the
	// new MyStringBuilder.
	// If c is empty, the original MyStringBuilder is returned.
	// The method first determines where to start the append:
	// If the current MyStringBuilder is empty, the first character of c is set
	// as the first node.
	// If the current MyStringBuilder only has one character, the append starts
	// after the first node.
	// Otherwise, the append starts after the last node (firstC.prev)
	public MyStringBuilder append(char [] c)
	{
		if (c == null || c.length == 0)  // special case for empty String
		{
			return this;
		}

		CNode newNode = new CNode(c[0]);
		CNode lastNode = firstC;

		if (length == 0)
		{
			firstC = newNode;
			length++;
		}
		else if (length == 1)
		{
			lastNode.next = newNode;
			newNode.prev = lastNode;
			length++;
		}
		else
		{
			lastNode = firstC.prev;
			lastNode.next = newNode;
			newNode.prev = lastNode;
			length++;
		}

		lastNode = newNode;

		for (int i = 1; i < c.length; i++)
		{
			newNode = new CNode(c[i], null, lastNode);
			lastNode.next = newNode;
			lastNode = newNode;
			length++;
		}
		// Connect end back to front to make list circular
		lastNode.next = firstC;
		firstC.prev = lastNode;
		return this;
	}

	// Append char c to the end of the current MyStringBuilder, and return the
	// new MyStringBuilder.
	// If the current MyStringBuilder is empty, the c is set as the first node.
	// If the current MyStringBuilder only has one character, c is added after
	// the first node.
	// Otherwise, c is added after the last node (firstC.prev)
	public MyStringBuilder append(char c)
	{
		if (length == 0)
		{
			CNode newNode = new CNode(c);
			firstC = newNode;
			length++;

			return this;
		}
		else if (length == 1)
		{
			CNode newNode = new CNode(c, firstC, firstC);
			firstC.next = newNode;
			firstC.prev = newNode;
			length++;

			return this;
		}
		else
		{
			CNode lastNode = firstC.prev;
			CNode newNode = new CNode(c, firstC, lastNode);
			lastNode.next = newNode;
			firstC.prev = newNode;
			length++;
			return this;
		}
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		if (index >= length || index < 0)
		{
			throw new IndexOutOfBoundsException("Illegal index " + index);
		}
		else
		{
			CNode currentNode = firstC;

			for (int i = 0; i < index; i++)
			{
				currentNode = currentNode.next;
			}

			char c = currentNode.data;

			return c;
		}
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the new MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder,
	// only remove up until the end of the MyStringBuilder.
	// If start == 0, the delete will start at the first node, and a new first
	// node will be assigned.
	// Otherwise, the list is traversed to one before the start index, then
	// continued to the end index. Then start and end are connected, thus deleting
	// the nodes in between them.
	public MyStringBuilder delete(int start, int end)
	{
		if (length == 0 || start >= length || start < 0 || end <= start)
		{
			return this;
		}

		CNode startNode = firstC;
		CNode prevNode = startNode.prev;

		if (start == 0)
		{
			if (end >= length)
			{
				startNode.prev = null;
				startNode.next = null;
				firstC = null;
				length = 0;
			}
			else
			{
				CNode endNode = firstC;
				for (int i = 0; i < end; i++)
				{
					endNode = endNode.next;
					length--;
				}
				prevNode.next = endNode;
				endNode.prev = prevNode;
				firstC = endNode;
			}
		}
		else if (end >= length)
		{
			length = 0;
			for (int i = 0; i < start; i++)
			{
				startNode = startNode.next;
				prevNode = startNode.prev;
				length++;
			}
			prevNode.next = firstC;
			firstC.prev = prevNode;
		}
		else
		{
			for (int i = 0; i < start; i++)
			{
				startNode = startNode.next;
				prevNode = startNode.prev;
			}

			CNode endNode = startNode;

			for (int i = start; i < end; i++)
			{
				endNode = endNode.next;
			}

			prevNode.next = endNode;
			endNode.prev = startNode;
			length -= (end-start);
		}
		return this;
	}


	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).  If "index"
	// is the last character in the MyStringBuilder, go backward in the list in
	// order to make this operation faster (since the last character is simply
	// the previous of the first character)
	// If index == 0, the first node is deleted and firstC is set to firstC.next
	// If the index is past the halfway point of the list, traversing is done in
	// reverse to increase speeed.
	// Otherwise, traversing in done from the front of the list.
	// The nodes before and after the index are connected, thus deleting the node
	// at index.
	public MyStringBuilder deleteCharAt(int index)
	{
		if (length == 0 || index >= length || index < 0)
		{
			return this;
		}

		CNode currentNode = firstC;
		CNode prevNode = firstC.prev;
		CNode nextNode = firstC.next;

		if (index == 0)
		{
				prevNode.next = nextNode;
				nextNode.prev = prevNode;
				firstC = nextNode;
				length--;
		}
		else if (index > length/2)
		{
			int indexFromLast = length - index;

			for (int i = 0; i < indexFromLast; i++)
			{
				currentNode = currentNode.prev;
				prevNode = prevNode.prev;
				nextNode = nextNode.prev;
			}
			prevNode.next = nextNode;
			nextNode.prev = prevNode;
			length--;
		}
		else
		{
			for (int i = 0; i < index; i++)
			{
				currentNode = currentNode.next;
				prevNode = prevNode.next;
				nextNode = nextNode.next;
			}
			prevNode.next = nextNode;
			nextNode.prev = prevNode;
			length--;
		}

		return this;
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.
	// If the length of str > the length of the list or str length is 0, return -1
	// as the item can not be found.
	// The index of where the string is found is kept track of as foundIndex,
	// which starts at 0 and increments as the current MyStringBuilder is traversed.
	// To search the current MyStringBuilder for str, a MyStringBuilder is created
	// with the contents of str (newCopy).
	// Then, a MyStringBuilder is created with length = newCopy (oldCopy).
	// This allows for the equals method to used to compare a portion of the
	// current MyStringBuilder with the MyStringBuilder version of str.
	// oldCopy traverses as a block of characters through the current
	// MyStringBuilder object by adding the next character in MyStringBuilder and
	// deleting the first character in oldCopy until it reaches the last node in
	// the current MyStringBuilder.
	// Along the way, the oldCopy is compared to newCopy using the equals method,
	// and foundIndex is incremented if they are not equal.
	// Once oldCopy.equals(newCopy), the found index is returned.
	// If oldCopy reaches the end of the current MyStringBuilder, then newCopy has
	// not been found, so -1 is returned.
	public int indexOf(String str)
	{
		int strLength = str.length();

		if (strLength > length || strLength == 0)
		{
			return -1;
		}

		MyStringBuilder newCopy = new MyStringBuilder(str);
		MyStringBuilder oldCopy = new MyStringBuilder();

		CNode currentNode = firstC;
		int foundIndex = 0;

		while (oldCopy.length < newCopy.length)
		{
			oldCopy.append(currentNode.data);
			currentNode = currentNode.next;
		}

		for (int i = 0; i < length; i++)
		{
			if (newCopy.equals(oldCopy))
			{
				return foundIndex;
			}
			else
			{
				oldCopy.append(currentNode.data);
				oldCopy.deleteCharAt(0);
				foundIndex++;
			}
			currentNode = currentNode.next;
		}

		return -1;
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" ==
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	// The current MyStringBuilder is traversed using current node, which determines
	// where to start the insert.
	// offSetNode is used to keep track of where to connect back to after the
	// insert.
	// newNode creates new CNodes for each character in str.
	// If offSet == 0, the insert starts at the first CNode, and firstC is changed
	// to reference the first character in str.
	// Otherwise, currentNode traverses the list until it reaches the insert
	// position, then offset is set to the next node to keep a reference to the
	// node to reconnect to after inserting.
	// The string is then inserted in the position and the last inserted node is
	// reconnected to offset node.
	public MyStringBuilder insert(int offset, String str)
	{
		if (offset > length)
		{
			return this;
		}
		else if (offset == length || length == 0)
		{
			this.append(str);
			return this;
		}

		CNode offsetNode = firstC;
		CNode currentNode = firstC;
		CNode newNode = new CNode(str.charAt(0));

		if (offset == 0)
		{
			newNode.prev = firstC.prev;
			firstC = newNode;
			currentNode = newNode;
			length++;
		}
		else
		{
			for (int i = 1; i < offset; i ++)
			{
				currentNode = currentNode.next;
			}

			offsetNode = currentNode.next;
			currentNode.next = newNode;
			newNode.prev = currentNode;
			currentNode = currentNode.next;
			length++;
		}

		for (int i = 1; i < str.length(); i++)
		{
			newNode = new CNode(str.charAt(i));
			currentNode.next = newNode;
			newNode.prev = currentNode;
			currentNode = currentNode.next;
			length++;
		}
		currentNode.next = offsetNode;
		offsetNode.prev = currentNode;

		return this;
	}

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid,
	// do nothing.
	// The current MyStringBuilder is traversed using current node, which determines
	// where to start the insert.
	// offSetNode is used to keep track of where to connect back to after the
	// insert.
	// newNode creates a CNode for the character
	// If offSet == 0, the insert starts at the first CNode, and firstC is changed
	// to reference newNode
	// Otherwise, currentNode traverses the list until it reaches the insert
	// position, then offset is set to the next node to keep a reference to the
	// node to reconnect to after inserting.
	// The character is then inserted in the position and the last inserted node is
	// reconnected to offset node.
	public MyStringBuilder insert(int offset, char c)
	{
		if (offset > length)
		{
			return this;
		}
		else if (offset == length || length == 0)
		{
			this.append(c);
			return this;
		}

		CNode offsetNode = firstC;
		CNode currentNode = firstC;
		CNode newNode = new CNode(c);

		if (offset == 0)
		{
			newNode.prev = firstC.prev;
			firstC = newNode;
			currentNode = newNode;
			length++;
		}
		else
		{
			for (int i = 1; i < offset; i ++)
			{
				currentNode = currentNode.next;
			}

			offsetNode = currentNode.next;
			currentNode.next = newNode;
			newNode.prev = currentNode;
			currentNode = currentNode.next;
			length++;
		}

		currentNode.next = offsetNode;
		offsetNode.prev = currentNode;

		return this;
	}

	// Return the length of the current MyStringBuilder
	public int length()
	{
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.
	// If start > 0, the list is traversed to until it reaches the node before
	// index "start"
	//	If end <= length, the list keeps traversing using endNode, starting at
	// the starting node, and decrements length until it reaches the node at index
	// end.
	// Otherwise, endNode references firstC, and length is set = start.
	// The newNodes are then created for each character in str and added to from
	// the starting point, then the last added node is connected back to endNode.
	public MyStringBuilder replace(int start, int end, String str)
	{
		if (length == 0 || start >= length || start < 0 || end <= start)
		{
			return this;
		}

		CNode currentNode = firstC;
		CNode endNode = firstC;

		if (start > 0)
		{
			for (int i = 0; i < start; i++)
			{
				currentNode = currentNode.next;
			}
		}

		if (end <= length)
		{
			endNode = currentNode;

			for (int i = start; i < end; i++)
			{
				endNode = endNode.next;
				length--;
			}
		}
		else
		{
			endNode = firstC;
			length = start;
		}

		currentNode.data = str.charAt(0);
		length++;

		for (int i = 1; i < str.length(); i++)
		{
			CNode newNode = new CNode(str.charAt(i), null, currentNode);
			currentNode.next = newNode;
			currentNode = newNode;
			length++;
		}

		currentNode.next = endNode;
		endNode.prev = currentNode;

		return this;
	}

	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder.
	// If length == 0, returns an empty string.
	// Otherwise, the list is traversed until it reaches the node at index start.
	// Then the contents of that node and the following nodes are added to a char
	// array until the node at index end is reached.
	// Returns the char array as a string.
	public String substring(int start, int end)
	{
		if (length == 0)
		{
			return new String("");
		}
		int subLength = end - start;
		char [] sub = new char[subLength];
		CNode currNode = firstC;

		for (int i = 0; i < start; i++)
		{
			currNode = currNode.next;
		}

		for(int i = 0; i < subLength; i ++)
		{
			sub[i] = currNode.data;
			currNode = currNode.next;
		}
		return new String(sub);
	}

	// Return as a String the reverse of the contents of the MyStringBuilder.
	// If length == 0, returns an empty string.
	// Otherwise, the list is traversed in reverse order and the contents of each
	// node are added to a character array.
	// When the counter reaches length, the char array is returned as a string.
	public String revString()
	{
		if (length == 0)
		{
			return new String("");
		}

		char [] c = new char[length];
		int i = 0;
		CNode currNode = firstC.prev;
		while (i < length)
		{
			c[i] = currNode.data;
			i++;
			currNode = currNode.prev;
		}
		return new String(c);
	}

	// Returns the loc-th word in the MyStringBuilder as a String.
	// This method keeps track of how many words there are by detecting spaces
	// between words.
	// If loc < 0 or length ==0, an empty string is returned.
	// Otherwise, the list is traversed node by node, currChar is incremented
	// for each character, and numWords is incremented if a space is found until
	// the entire list is traversed or numWords reaches loc.
	// MyStringBuilder word is used to build a copy of the desired word by
	// appending word until another space is reached or the end of the current
	// MyStringBuilder is reached.
	// word is then converted to a string and returned.
	public String getWord(int loc)
	{
		if (loc < 0 || length == 0)
		{
			return new String("");
		}

		CNode currNode = firstC;
		int numWords = 0;
		int currChar = 0;
		MyStringBuilder word = new MyStringBuilder();

		while (currChar < length && numWords < loc)
		{
			if (currNode.data == ' ')
			{
				numWords++;
			}
			currNode = currNode.next;
			currChar++;
		}

		while (currNode.data != ' ' && currChar < length)
		{
			word.append(currNode.data);
			currChar++;
			currNode = currNode.next;
		}

		String wordString = word.toString();

		return wordString;
	}

	// Compares two MyStringBuilders to see if their contents are equal and in
	// the same order.
	// If both are empty, returns true.
	// If their lengths are not equal, return false.
	// Otherwise, each character in each MyStringBuilder is compared in order,
	// and if any two characters are not equal, return false.
	// If both lists are traversed, then the two MyStringBuilders are equal and
	// returns true. 
  public boolean equals(MyStringBuilder old)
   {
	   if (length == 0 && old.length == 0)
	   {
		   return true;
	   }
	   else if (length == old.length)
	   {
		   CNode currentOld = old.firstC;
		   CNode currentThis = firstC;
		   boolean isEqual = false;

		   for (int i = 0; i < length; i++)
		   {
			   if(currentOld.data == currentThis.data)
			   {
				   isEqual = true;
			   }
			   else
			   {
				   return false;
			   }
			   currentOld = currentOld.next;
			   currentThis = currentThis.next;
		   }

		   return isEqual;
	   }
	   else
	   {
		   return false;
	   }
   }
	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data, next and prev fields directly.
	private class CNode
	{
		private char data;
		private CNode next, prev;

		public CNode(char c)
		{
			data = c;
			next = null;
			prev = null;
		}

		public CNode(char c, CNode n, CNode p)
		{
			data = c;
			next = n;
			prev = p;
		}
	}
}
