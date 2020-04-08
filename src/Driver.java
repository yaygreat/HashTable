import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Driver
{
    static HashTable hashtable;
    static String input = "";

    public static void main(String[] args) throws Exception {
        Scanner keyboard = new Scanner(System.in);

        //create hashtable
        int prob = 1;
        fileToHashTable(prob); //linear probing
        System.out.println("The number of collisions using linear probing: ");
        hashtable.Collisions();
        fileToHashTable(prob + 1); //quadratic probing
        System.out.println("The number of collisions using quadratic probing: ");
        hashtable.Collisions();

        //run spellchecker
        while(true)
        {
            System.out.println("\nType a word to spellcheck, or # to exit:");
            input = keyboard.next();
            if(input.equals("#"))
                break;
            if (hashtable.spellChecker(prob, input))
                System.out.println(input + " was found!");
            else
                System.out.println(input + " was NOT found.");
        }

        //add 10 words and rehash
        addWordsToFile();
        prob = 1;
        fileToHashTable(prob); //linear probing
        System.out.println("\nIncreasing table size to: " + hashtable.getN());
        System.out.println("The number of collisions using linear probing: "); //linear probing
        hashtable.Collisions();
        fileToHashTable(prob + 1); //quadratic probing
        System.out.println("The number of collisions using quadratic probing: ");
        hashtable.Collisions();
    }

    public static void addWordsToFile() throws Exception
    {
        //append 10 words to text file
        FileWriter  file = new FileWriter("hundredwords.txt", true);
        BufferedWriter bw = new BufferedWriter(file);
        String[] tenWords = {"why", "dont", "horses", "get", "divorced", "because", "they", "have", "stable", "relationships" };
        bw.newLine();
        bw.flush();
        for (int i = 0; i < tenWords.length; i++)
        {
            bw.write(tenWords[i]);
            bw.newLine();
            bw.flush();
        }
        file.close();
    }

    public static void fileToHashTable(int prob) throws Exception
    {
        FileReader file = new FileReader("hundredwords.txt");
        BufferedReader br = new BufferedReader(file);
        hashtable = new HashTable(53);
        String word = "";
        input = "";
        int wordCount = 0;

        //For each line, insert that word into the hash table
        while ((input = br.readLine()) != null)
        {
            wordCount++;
            word = input;
            hashtable.insert(prob, word, wordCount);
        }
        file.close();
    }
}
