/*
Kevin Corcoran
kjc100@pitt.edu
ID: 3855660
CS 0445 Fall 2021
Assignment 3: Extra Credit Tests
    -   Tests functionality of methods not required in the assignment.
    -   Tests for getNode method are not done here since that method is private and the
        existence of nodes is hidden from the user. 
 */
public class ExtraCreditTests
{
    public static void main(String[] args)
    {
        MyStringBuilder2 one = new MyStringBuilder2("This is a string");
        MyStringBuilder2 oneTwin = new MyStringBuilder2("This is a string");
        MyStringBuilder2 sameLength = new MyStringBuilder2("Tifferent string");
        MyStringBuilder2 diffLength = new MyStringBuilder2("Short string");
        MyStringBuilder2 emptyOne = new MyStringBuilder2();
        MyStringBuilder2 emptyTwo = new MyStringBuilder2();

        if (one.equals(oneTwin)) {
            System.out.println(one.toString() + " equals " + oneTwin.toString());
        } else {
            System.out.println(one.toString() + " does not equal " + oneTwin.toString());
        }

        if (one.equals(sameLength)) {
            System.out.println(one.toString() + " equals " + sameLength.toString());
        } else {
            System.out.println(one.toString() + " does not equal " + sameLength.toString());
        }
        if (one.equals(diffLength)) {
            System.out.println(one.toString() + " equals " + diffLength.toString());
        } else {
            System.out.println(one.toString() + " does not equal " + diffLength.toString());
        }

        if (one.equals(emptyOne)) {
            System.out.println(one.toString() + " equals " + emptyOne.toString());
        } else {
            System.out.println(one.toString() + " does not equal " + emptyOne.toString());
        }

        if (emptyOne.equals(emptyTwo)) {
            System.out.println(emptyOne.toString() + " equals " + emptyTwo.toString());
        } else {
            System.out.println(emptyOne.toString() + " does not equal " + emptyTwo.toString());
        }

    }
}
