/*
Kevin Corcoran
kjc100@pitt.edu
ID: 3855660
CS 0445 Fall 2021
Assignment 3: MyStringBuilder2 Class
SEE METHOD DESCRIPTIONS FOR IMPLEMENTATION DETAILS
All methods are recursive, so most public methods have a private recursive counterpart
by either the same name, or a common name with varying parameters.
**MyStringBuilder 2 may be referred to as MSB2 to simplify documentation.**
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
	-	Two MyStringBuilders can be compared using the equals() method.
	-	The length of a MyStringBuilder can be accessed using the length method.
 */

public class MyStringBuilder2 {

	private CNode firstC;    // reference to front of list.
	private int length;    // number of characters in the list

	// Create a new MyStringBuilder which contains the contents of the String
	// argument.
	// If the string is not null or empty, make a call to makeBuilder method to
	// make the MSB2 recursively.
	public MyStringBuilder2(String s) {
		if (s != null && s.length() > 0)
			makeBuilder(s, 0);
		else {	 // no String so initialize empty MSB2
			firstC = null;
			length = 0;
		}
	}

	// Create a new MSB2 initialized with the chars in array s.
	// If the array is not null or empty, make a call to makeBuilder method to
	// make the MSB2 recursively.
	public MyStringBuilder2(char[] s) {
		if (s != null && s.length > 0)
			makeBuilder(s, 0);
		else {	// Empty array so initialize empty MSB2
			firstC = null;
			length = 0;
		}
	}

	// Copy constructor -- make a new MSB2 from an old one.
	// If the old MSB2 is not null or empty, make a call to makeBuilder
	// method to make the MSB2 recursively.
	public MyStringBuilder2(MyStringBuilder2 old) {
		if (old.firstC != null && old.length > 0)
			makeBuilder(old, old.firstC);
		else { // Empty old so initialize empty MSB2
			firstC = null;
			length = 0;
		}
	}

	// Create a new empty MSB2
	public MyStringBuilder2() {
		firstC = null;
		length = 0;
	}

	// Recursive method to set up a new MSB2 from a String
	private void makeBuilder(String s, int pos) {
		// Recursive case – we have not finished going through the String
		if (pos < s.length() - 1) {
			// Make recursive calls first until the last character in the string
			// is found. Then add each character until the first character is reached.
			makeBuilder(s, pos + 1);
			CNode temp = new CNode(s.charAt(pos));
			CNode back = firstC.prev;
			temp.prev = back;
			back.next = temp;
			temp.next = firstC;
			firstC.prev = temp;
			firstC = temp;
			length++;
		} else if (pos == s.length() - 1) { // Special case for last char in String
			// This is a base case and initializes
			// firstC in a circular way
			firstC = new CNode(s.charAt(pos));
			firstC.next = firstC;
			firstC.prev = firstC;
			length = 1;
		} else {
			// This case should never be reached, due to the way the
			// constructor is set up. However, I included it as a
			// safeguard (in case some other method calls this one)
			length = 0;
			firstC = null;
		}
	}

	// Recursive method to set up a new MSB2 from a char array
	private void makeBuilder(char[] s, int pos) {
		// Recursive case – we have not finished going through the array
		if (pos < s.length - 1) {
			// Make recursive calls first until the last character in the array
			// is found. Then add each character until the first character is reached.
			makeBuilder(s, pos + 1);
			CNode temp = new CNode(s[pos]);
			CNode back = firstC.prev;
			temp.prev = back;
			back.next = temp;
			temp.next = firstC;
			firstC.prev = temp;
			firstC = temp;
			length++;
		} else if (pos == s.length - 1) { // Special case for last char in array
			// This is a base case and initializes
			// firstC in a circular way
			firstC = new CNode(s[pos]);
			firstC.next = firstC;
			firstC.prev = firstC;
			length = 1;
		} else {
			// This case should never be reached, due to the way the
			// constructor is set up. However, I included it as a
			// safeguard (in case some other method calls this one)
			length = 0;
			firstC = null;
		}
	}

	// Recursive method to set up a copy MSB2
	private void makeBuilder(MyStringBuilder2 old, CNode curr) {
		// Recursive case – we have not finished going through the array
		if (curr.next != old.firstC) {
			// Make recursive calls first until the last character in the MSB2
			// is found. Then add each character until the first character is reached.
			makeBuilder(old, curr.next);
			CNode temp = new CNode(curr.data);
			CNode back = firstC.prev;
			temp.prev = back;
			back.next = temp;
			temp.next = firstC;
			firstC.prev = temp;
			firstC = temp;
			length++;
		} else { // Special case for last node in MSB2
			// This is a base case and initializes
			// firstC in a circular way
			firstC = new CNode(curr.data);
			firstC.next = firstC;
			firstC.prev = firstC;
			length = 1;
		}
	}

	// Return the entire contents of the current MSB2 as a String.
	public String toString() {
		char[] c = new char[length];
		getString(c, 0, firstC);
		return (new String(c));
	}

	// Here we need the char array to store the characters, the pos value to
	// indicate the current index in the array and the curr node to access
	// the data in the actual MSB2. Note that these rec methods
	// are private – the user of the class should not be able to call them.
	private void getString(char[] c, int pos, CNode curr) {
		if (pos < length) {	// Not at end of the list
			c[pos] = curr.data; // put next char into array
			getString(c, pos + 1, curr.next); // recurse to next node and
		} // next pos in array
	}

	// Append MSB2 b to the end of the current MSB2, and return the current MSB2.
	// If b is null or empty, just return the current MSB2.
	public MyStringBuilder2 append(MyStringBuilder2 b) {
		if (b.firstC != null && b.length > 0)
			this.appendBuilder(b, b.firstC);
		return this;
	}

	// Recursive method for appending a MSB2 to another MSB2
	// Add each character in order to the end of the current MSB2.
	private void appendBuilder(MyStringBuilder2 b, CNode curr) {
		if (firstC == null) {
			//If the current MSB2 is empty, initialize first char in b
			// as firstC in curr.
			firstC = new CNode(b.firstC.data);
			firstC.next = firstC;
			firstC.prev = firstC;
			curr = curr.next;
			length++;
		}

		CNode temp = new CNode(curr.data);
		CNode back = firstC.prev;
		firstC.prev = temp;
		temp.next = firstC;
		temp.prev = back;
		back.next = temp;
		length++;

		if (curr.next != b.firstC && curr.next != null) {
			appendBuilder(b, curr.next); // Recursive call to add next char.
		}
	}

	// Append String s to the end of the current MSB2, and return the current MSB2.
	// If s is null or empty, just return the current MSB2.
	public MyStringBuilder2 append(String s) {
		if (s != null && s.length() != 0)
			appendBuilder(s, 0);
		return this;
	}

	// Recursive method for appending a string to the end of an MSB2
	// Add each character in order to the end of the current MSB2.
	private void appendBuilder(String s, int curr) {
		if (firstC == null) {
			//If the current MSB2 is empty, initialize first char in s
			// as firstC in curr.
			firstC = new CNode(s.charAt(curr));
			firstC.next = firstC;
			firstC.prev = firstC;
			curr++;
			length++;
		}

		CNode temp = new CNode(s.charAt(curr));
		CNode back = firstC.prev;
		firstC.prev = temp;
		temp.next = firstC;
		temp.prev = back;
		back.next = temp;
		length++;

		if (curr + 1 != s.length()) {
			appendBuilder(s, curr + 1); // Recursive call to add next char.
		}
	}

	// Append char array c to the end of the current MSB2, and return the current MSB2.
	// If c is null or empty, just return the current MSB2.
	public MyStringBuilder2 append(char[] c) {
		if (c != null && c.length != 0)
			appendBuilder(c, 0);
		return this;
	}

	// Recursive method for appending a char array to the end of an MSB2
	// Add each character in order to the end of the current MSB2.
	private void appendBuilder(char[] c, int curr) {
		if (firstC == null) {
			//If the current MSB2 is empty, initialize first char in c
			// as firstC in curr.
			firstC = new CNode(c[curr]);
			firstC.next = firstC;
			firstC.prev = firstC;
			curr++;
			length++;
		}

		CNode temp = new CNode(c[curr]);
		CNode back = firstC.prev;
		firstC.prev = temp;
		temp.next = firstC;
		temp.prev = back;
		back.next = temp;
		length++;

		if (curr + 1 != c.length) {
			appendBuilder(c, curr + 1); // Recursive call to add next char.
		}
	}

	// Append char c to the end of the current MSB2, and return the current MSB2.
	public MyStringBuilder2 append(char c) {
		appendBuilder(c);
		return this;
	}

	// Private method for appending a char to the end of an MSB2.
	// This method does not necessarily have to be private, but it is made private
	// for consistency and data encapsulation.
	private void appendBuilder(char c) {
		if (firstC == null) {
			//If the current MSB2 is empty, initialize as firstC in curr.
			firstC = new CNode(c);
			firstC.next = firstC;
			firstC.prev = firstC;
		} else {
			CNode temp = new CNode(c);
			CNode back = firstC.prev;
			firstC.prev = temp;
			temp.next = firstC;
			temp.prev = back;
			back.next = temp;
		}
		length++;
	}

	// Return the character at location "index" in the current MSB2.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index) {
		if (index >= length || index < 0) {
			throw new IndexOutOfBoundsException("Illegal index " + index);
		} else {
			return charAt(index, 0, firstC);
		}
	}

	// Recursive method for charAt. Creates a temporary char c and recurses through
	// the MSB2 until the index is reached, then c is returned.
	private char charAt(int index, int i, CNode curr) {
		char c;
		if (index != i) {
			c = charAt(index, i + 1, curr.next);
		} else {
			c = curr.data;
		}
		return c;
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MSB2, and return the current MSB2.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MSB2 as is).
	// This method utilizes the getNode() method, which finds a node a certain
	// distance from another node. This cuts out a lot of traversal code and
	// makes this method a lot simpler.
	public MyStringBuilder2 delete(int start, int end) {
		if (end > start && start >= 0 && start < length) {
			CNode startNode = getNode(firstC, start, 0);
			CNode endNode = getNode(startNode, end-start, 0);
			delete(start, end, startNode, endNode);
		}
		return this;
	}

	// Private method for deleting multiple characters in a MSB2.
	// No recursion is done in this method, since the recursion was completed when
	// finding the startNode and endNodes. This is kept private for consistency
	// and data encapsulation.
	// If start == 0, the firstC is adjusted.
	// If end >= length, the list is deleted up until the end.
	private void delete(int start, int end, CNode startNode, CNode endNode) {
		if (start == 0) {
			if (end >= length) {
				startNode.prev = null;
				startNode.next = null;
				firstC = null;
				length = 0;
			} else {
				startNode = startNode.prev;
				startNode.next = endNode;
				endNode.prev = startNode;
				firstC = endNode;
				length -= (end-start);
			}
		} else{
			if (end >= length) {
				startNode.next = firstC;
				firstC.prev = startNode;
				length = start;
			} else {
				startNode = startNode.prev;
				startNode.next = endNode;
				endNode.prev = startNode;
				length -= (end-start);
			}
		}
	}

	// Delete the character at location "index" from the current
	// MSB2 and return the current MSB2.  If "index" is
	// invalid, do nothing (just return the MSB2 as is).
	public MyStringBuilder2 deleteCharAt(int index) {
		if (length == 1 && index == 0) { // Special case for MSB2 of size 1.
			firstC = null;
			length = 0;
		} else if (index < length && index >= 0) {
			deleteCharAt(index, 0, firstC);
		}
		return this;
	}

	// Recursive method for deleteCharAt
	// Traverses through the MSB2 until the index is reached, the
	// deletes the character at that index but connecting the previous and next characters.
	private void deleteCharAt(int index, int i, CNode curr) {
		if (index != i) {
			deleteCharAt(index, i + 1, curr.next);
		} else {
			CNode prev = curr.prev;
			CNode next = curr.next;

			if (index == 0)
				firstC = next;

			prev.next = next;
			next.prev = prev;
			length--;
		}
	}

	// Find and return the index within the current MSB2 where
	// String str first matches a sequence of characters within the current
	// MSB2.  If str does not match any sequence of characters
	// within the current MSB2, return -1.
	// Creates a MSB2 for the string, and copies the current MSB2
	// into an MSB2 with length == str.length. Note that no characters are
	// deleted in oldCopy. Its length is only adjusted to allow comparison
	// only strLength characters at a time.
	public int indexOf(String str) {
		int strLength = str.length();
		int foundIndex;

		if (strLength > length || strLength == 0) {
			foundIndex = -1;
		} else {
			MyStringBuilder2 strCopy = new MyStringBuilder2(str);
			MyStringBuilder2 oldCopy = new MyStringBuilder2(this);
			oldCopy.length = strLength;
			foundIndex = indexOf(strCopy, oldCopy, 0, 0);
		}
		return foundIndex;
	}

	// Recursive method for indexOf:
	// Compares the copy of the old MSB2 to the str MSB2, and if they
	// don't match, shift the firstC of the copy over to check the next
	// str.length letters. This method uses the equals() method to compare the
	// MSB2s.  Comps keeps track of the number of comparisons made so far
	// prevent dropping off the end of the copy MSB2. If the str is found in
	// copy, return the found index, if not return -1.
	private int indexOf(MyStringBuilder2 str, MyStringBuilder2 copy, int index, int comps) {
		int foundIndex;

		if (!str.equals(copy) && comps < (length - str.length)) {
			copy.firstC = copy.firstC.next;
			foundIndex = indexOf(str, copy, index + 1, comps + 1);
		} else if (!str.equals(copy)) {
			foundIndex = -1;
		} else if (str.equals(copy) && comps == 0) {
			foundIndex = 0;
		} else {
			foundIndex = index;
		}
		return foundIndex;
	}

	// Insert String str into the current MSB2 starting at index
	// "offset" and return the current MSB2.  if "offset" ==
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder2 insert(int offset, String str) {
		if (offset == length) {
			this.append(str);
		} else if (offset < length && offset >= 0) {

			insert(offset, str, firstC, 0);
		}
		return this;
	}

	// Recursive method for insert:
	// Traverse the list until the offset is found. Then save the previous
	// node to the offset, insert the string at this position, and connect the
	// first char in the str back to the prev node.
	private void insert(int offset, String str, CNode curr, int pos) {
		if (offset != pos) {
			insert(offset, str, curr.next, pos + 1);
		} else {
			CNode prev = curr.prev;
			CNode firstChar = insertStr(str, curr, str.length() - 1);
			if (offset == 0)
				firstC = firstChar;
			prev.next = firstChar;
			firstChar.prev = prev;
		}
	}

	// Builds nodes for each character in str and connects them to node curr.
	// This proceeds in reverse to make it easier to connect back to the
	// current MSB2. The first time through, the last char in str is connected
	// to curr(the offset node from the current MSB2).
	// The first node for the first char in str is returned.
	private CNode insertStr(String str, CNode curr, int strChar) {
		CNode firstChar;
		if (strChar >= 0) {
			CNode currentChar = new CNode(str.charAt(strChar));
			curr.prev = currentChar;
			currentChar.next = curr;
			length++;
			firstChar = insertStr(str, currentChar, strChar - 1);
		} else {
			firstChar = curr;
		}
		return firstChar;
	}

	// Insert character c into the current MSB2 at index
	// "offset" and return the current MSB2.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder2 insert(int offset, char c) {
		if (offset == length) {
			this.append(c);
		} else if (offset < length && offset >= 0) {
			insertChar(offset, c, firstC, 0);
		}
		return this;
	}

	// Recursive method for insertion of a character.
	// Finds the offset node, then insets creates a new node for the char
	// and connects it to the offset and the previous node.
	private void insertChar(int offset, char c, CNode curr, int pos) {
		if (offset != pos) {
			insertChar(offset, c, curr.next, pos + 1);
		} else {
			CNode prev = curr.prev;
			CNode newChar = new CNode(c);
			if (offset == 0)
				firstC = newChar;
			prev.next = newChar;
			newChar.prev = prev;
			newChar.next = curr;
			curr.prev = newChar;
			length++;
		}
	}

	// Return the length of the current MSB2
	public int length() {
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MSB2, then insert String "str" into the current
	// MSB2 starting at index "start", then return the current
	// MSB2.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MSB2, only delete until the
	// end of the MSB2, then insert.
	// All recursion in this method is done through the getNode method to
	// reduce unnecessary code.
	// The start and end nodes are found, then the node before the startNode
	// is saved. A new MSB2 is made for the string, and that is inserted
	// by connecting the prev node and end nodes to its firstC and last Node.
	public MyStringBuilder2 replace(int start, int end, String str) {
		if (start == length) {
			append(str);
		} else if (end > start && start >= 0 && start < length && str.length() > 0) {
			CNode startNode = getNode(firstC, start, 0);
			CNode prev = startNode.prev;
			CNode endNode;

			if (end >= length) {
				endNode = firstC;
				length = start + str.length();
			} else {
				endNode = getNode(startNode, end - start, 0);
				length = (length - (end - start)) + str.length();
			}

			MyStringBuilder2 strCopy = new MyStringBuilder2(str);
			CNode firstChar = strCopy.firstC;
			CNode lastChar = strCopy.firstC.prev;

			if (start == 0) {
				firstC = firstChar;
			}

			prev.next = firstChar;
			firstChar.prev = prev;
			endNode.prev = lastChar;
			lastChar.next = endNode;
		}
		return this;
	}

	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MSB2
	// startNode is found using the recursive getNode method.
	// This is then passed to the recursive version of this method.
	// A new char array is created and filled using the recursive version
	// then that array is converted into a string and returned.
	public String substring(int start, int end) {
		if (end > start && start >= 0 && start < length) {
			int length = end - start;
			CNode startNode = getNode(firstC, start, 0);
			char[] c = substring(0, end-start, startNode, new char[length]);
			return new String(c);
		} else {
			return new String("");
		}
	}

	// Recursive method for substring:
	// Adds characters from MSB2 into a char array until the last char is met.
	// This starts it startNode, found in the non-recursive version of this method
	// which uses getNode to find it.
	private char[] substring(int currentChar, int end, CNode curr, char[] c) {
		if (currentChar != end) {
			c[currentChar] = curr.data;
			c = substring(currentChar + 1, end, curr.next, c);
		}
		return c;
	}

	// Return the entire contents of the current MSB2 as a String
	// in the reverse of the order that it is stored.
	// A char array is created then filled in the recursive reverse()
	// method. The array is then converted into a string and returned.
	public String revString() {
		char[] c = new char[length];

		if (firstC != null && length > 0)
			reverse(c, length - 1, firstC);

		return new String(c);
	}

	// Recursive method to add characters of a MSB2 into a char array
	// in reverse order.
	private void reverse(char[] c, int i, CNode curr) {
		if (i < length && i >= 0) {
			c[i] = curr.data;
			reverse(c, i - 1, curr.next);
		}
	}

	// Find and return the index within the current MSB2 where
	// String str LAST matches a sequence of characters within the current
	// MSB2.  If str does not match any sequence of characters
	// within the current MSB2, return -1.
	// If the length of str is longer than the MSB2 or 0, -1 is returned
	// since it can not be found.
	// Similar to the indexOf method, an MSB2 is made for the str
	// and a copy of the current MSB2 is made at length strLength to
	// allow comparison of strLength characters at a time.
	public int lastIndexOf(String str) {
		int strLength = str.length();
		int lastIndex;

		if (strLength > length || strLength == 0) {
			lastIndex = -1;
		} else {
			MyStringBuilder2 strCopy = new MyStringBuilder2(str);
			MyStringBuilder2 oldCopy = new MyStringBuilder2(this);
			oldCopy.length = strLength;
			lastIndex = lastIndexOf(strCopy, oldCopy, 0, 0);
		}
		return lastIndex;
	}

	// Recursive version of lastIndexOf
	// Traverses to the end of copy using the number of comparisons: comps to prevent
	// going past the end.
	// Compares portion of copy with str until it is found, then returns the found index
	// or -1 if not found.
	private int lastIndexOf(MyStringBuilder2 str, MyStringBuilder2 copy, int index, int comps) {
		int lastIndex = -1;

		if (comps < (length - str.length)) {
			copy.firstC = copy.firstC.next;
			lastIndex = lastIndexOf(str, copy, index + 1, comps + 1);
		}

		if (lastIndex == -1) {
			if (str.equals(copy) && comps == 0) {
				lastIndex = 0;
			} else if (str.equals(copy)) {
				lastIndex = index;
			} else {
				copy.firstC = copy.firstC.prev;
			}
		}
		return lastIndex;
	}

	// Find and return an array of MSB2, where each MSB2
	// in the return array corresponds to a part of the match of the array of
	// patterns in the argument.  If the overall match does not succeed, return
	// null.
	// An array of MSB2 is created. If all patterns have been found, pass with == true
	// and the array will be returned. Otherwise, null is returned.
	public MyStringBuilder2[] regMatch(String[] pats) {
		MyStringBuilder2[] ans = new MyStringBuilder2[pats.length];
		boolean pass = regMatch(firstC, ans, pats, 0);
		if (pass) {
			return ans;
		} else {
			return null;
		}
	}

	// Recursive version of regMatch:
	// Creates a MSB2 is the current index of the ans array is empty.
	// Checks each character in MSB2 to see if it matches with any characters in
	// current index of pats.
	// First tries to match a character to the first pattern.
	// If it matches with the current pattern, it will try to match the next
	// character with the same pattern.
	// If it does not match that pattern, the next pattern is tried, and so on.
	// If at some point a pattern can not be matched, the previous character will be compared
	// with the next pattern.
	// If the previous character did not match with anything, the process is restarted with the next c
	// character.
	// When characters are matched, they are added to the i index of ans.
	// If backtracking is required, the last character added to i is deleted.
	private boolean regMatch(CNode curr, MyStringBuilder2 [] ans, String [] pats, int i)
	{
		if (ans[i] == null)
			ans[i] = new MyStringBuilder2();
		boolean matches = (pats[i].indexOf(curr.data) != -1);

		// Trying to find first match to first pattern
		if (ans[i].length == 0 && i == 0) {
			if (curr.next == firstC) {
				return false;
			}
			else if (!matches) {
				matches = regMatch(curr.next, ans, pats, i);
			}
			else {
				ans[i].append(curr.data);
				matches = regMatch(curr.next, ans,pats,i);
				if (!matches) {
					matches = regMatch(curr.next, ans, pats, i);
				}
			}
		}
		// Trying to continue with pattern
		else if(ans[i].length > 0) {
			if (matches) {
				ans[i].append(curr.data);
				if (curr.next != firstC) {
					matches = regMatch(curr.next, ans, pats, i);
				} else if (i < pats.length-1) {
					matches = false;
					ans[i].deleteCharAt(ans[i].length - 1);
				}
			}
			if (!matches && i == pats.length-1) {
				return true;
			}
			if (!matches && i < pats.length-1) {
				matches = regMatch(curr, ans, pats, i+1);

				if (!matches && ans[i].length > 0) {
					ans[i].deleteCharAt(ans[i].length - 1);
				}
			}
		}
		// Trying next pattern
		else if (ans[i].length == 0) {
			if(matches) {
				ans[i].append(curr.data);
				matches = regMatch(curr.next, ans, pats, i);
			}
			if(!matches) {
				return false;
			}
		}
		return matches;
	}

	// Equals method check if one MSB2 is equal to another
	// based on the characters within the length of the MSB2.
	// Order matters.
	public boolean equals(MyStringBuilder2 compare) {
		if (length == 0 && compare.length == 0) {
			return true;
		} else if (length == compare.length) {
			return equals(this.firstC, compare.firstC, 0);
		} else {
			return false;
		}
	}

	// Recursive version of equals:
	// Checks each node in each MSB2 to see if their characters are equal.
	// If a character is ever not equal, return false.
	private boolean equals(CNode thisNode, CNode compNode, int i) {
		boolean isEqual;
		if (thisNode.data != compNode.data) {
			isEqual = false;
		} else if (i < length-1) {
			isEqual = equals(thisNode.next, compNode.next, i+1);
		} else {
			isEqual = true;
		}
		return isEqual;
	}

	// Recursive method to get a node in an MSB2 in relation to another node.
	// This returns the node at distance away from curr, and if
	// this method reaches the end of the MSB2, the last character is returned.
	private CNode getNode(CNode curr, int distance, int i) {
		CNode returnNode;
		if (distance != i && curr.next != firstC) {
			returnNode = getNode(curr.next, distance, i + 1);
		} else {
			returnNode = curr;
		}
		return returnNode;
	}

	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MSB2 class MAY access the
	// data and next fields directly.
	private class CNode {
		private char data;
		private CNode next, prev;

		public CNode(char c) {
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

