// package edu.miracosta.cs113;
import java.io.*;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * MorseCodeTree : A BinaryTree, with Nodes of type Character to represent each letter of the English alphabet,
 * and a means of traversal to be used to decipher Morse code.
 *
 * @version 1.0
 */
public class MorseCodeTree extends BinaryTree<Character> {

  String dotsnDashes;
  public BinaryTree<Character> morseTree;
  Node<Character> traverse, builder;

  // TODO:
  // Build this class, which includes the parent BinaryTree implementation in addition to
  // the `translateFromMorseCode` and `readMorseCodeTree` methods. Documentation has been suggested for the former,
  // where said exceptional cases are to be handled according to the
  // corresponding unit tests.

  // Empty constructor(builds the morse code tree)
  
  public MorseCodeTree()
	{
    traverse = root;
		readMorseCodeTree();
	}
	
  // For whatever amount of characters in the substring
  // If the character is a dot, move left
  // If the character is a dash, move right
  // Every time we reach a leaf node, break up the piece of morse we have been 
  // decoding and check to see if there are more characters ahead of it.
  // If not, in any case, print the result.
	public void readMorseCodeTree()
	{
    try
    {
      File morseFile = new File("MorseCodeTree.txt");
      Scanner r = new Scanner(morseFile);

      // TODO:
      // plant a tree - a node tree!

      morseTree = new BinaryTree<Character>(new Node<Character>(' '));

      builder = morseTree.root;

      while(r.hasNextLine())
      {
        // TODO: 
        // read the line to a string, isolate the first char of the string as // the letter to be added to a new Node (node to be added to the tree)
        // then trim the rest of the String to the MorseCode bit

        String s = r.nextLine();
        char c = s.charAt(0);
        dotsnDashes = s.substring(2);
        builder = morseTree.root;

        for(int i = 0; i < dotsnDashes.length(); i++)
        {
          if(dotsnDashes.charAt(i) == '*')
          {
            if(i == dotsnDashes.length() - 1)
            {
              builder.left = new Node(c);
            }
            else
            {
              builder = builder.left;
            }
          }
          else if(dotsnDashes.charAt(i) == '-')
          {
            if(i == dotsnDashes.length() - 1)
            {
              builder.right = new Node(c);
            }
            else
            {
              builder = builder.right;
            }
          }
          else
          {
            throw new IllegalArgumentException("He's dead, Jim.");
          }
        }
      }
    }
    catch(FileNotFoundException fnfe)
    {
      System.out.println("File not found: " + fnfe.getMessage());
    }
	}

  // add string argument for one token
  // replace dotsnDashes to the name of the argument
  public String translateOneChar(String arg)
  {
    // traverse = new Node<Character>(' ');
    Node<Character> traverse = morseTree.root;
    // TODO: To decode the message using the MorseCodeTree, translate each 
    // letter using the tree (branch left for a dot, branch right for a dash).
    // Make sure you use a space ( ) as a delimiter between coded letters 
    // (i.e., **** * *-** *-** --- *-- --- *-* *-** -** translates to 
    // helloworld).
    // Read a given morse code string and then traverse the tree until you 
    // find the letter
    // concatenate the letter to the final message, building it as you decode
    // move to the next part recursively until there's nothing.
    // Finally, return the decoded phrase.

    for(int i = 0; i < arg.length(); i++)
    {
      if(arg.charAt(i) == '*')
      {
        // left
        traverse = traverse.left;
      }
      else if(arg.charAt(i) == '-')
      {
        // right
        traverse = traverse.right;
      }
      else
      {
        System.out.println("Something's wrong, I can feel it");
      }
    }
    char temp = (traverse.data);
    return traverse.data.toString();
  }
	
      /**
     * Non-recursive method for translating a String comprised of morse code values through traversals
     * in the MorseCodeTree.
     *
     * The given input is expected to contain morse code values, with '*' for dots and '-' for dashes, representing
     * only letters in the English alphabet.
     *
     * This method will also handle exceptional cases, namely if a given token's length exceeds that of the tree's
     * number of possible traversals, or if the given token contains a character that is neither '*' nor '-'.
     *
     * @param morseCode The given input representing letters in Morse code
     * @return a String representing the decoded values from morseCode
     */

	public String translateFromMorseCode(String conChar)
	{
    String[] sequences;
    String decode = "";
    sequences = conChar.split(" ");
    for(int i = 0; i < sequences.length; i++)
    {
      decode += translateOneChar(sequences[i]);
    }
    return decode;
	}

} // End of class MorseCodeTree
