/*
 * Name: Divyansh Kanodia
 * PID: A17922611
 */

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Divyansh Kanodia
 * @since May 30, 2024
 */

public class SpellChecker {

    public KeyedSet dictWords;

    public void readDictionary(Reader reader, boolean useHashTable) {
        if (useHashTable){
            dictWords = new MyHashTable();
        }
        else{
            dictWords = new MyBloomFilter();
        }

        Scanner sc = new Scanner(reader);
        while(sc.hasNext()) {
            dictWords.insert(sc.nextLine().trim().toLowerCase());
        }
        sc.close();
    }

    private LinkedList<String> checkWrongLetter(String word) {
        LinkedList<String> output = new LinkedList<String>();
        for (int i = 0; i < word.length(); i++){
            char[] arraycheck = word.toCharArray();
            for (char j = 'a'; j <= 'z'; j++){
                arraycheck[i] = j;
                String wordToAdd = String.valueOf(arraycheck).toLowerCase().trim();
                if (dictWords.lookup(wordToAdd)){
                    output.add(wordToAdd);
                }
            }
        }
        return output;
    }

    private LinkedList<String> checkInsertedLetter(String word) {
        LinkedList<String> output = new LinkedList<String>();
        for (int i = 0; i < word.length(); i++)
        {
            for (char j = 'a'; j <= 'z'; j++)
            {
                String wordToAdd = word.substring(0,i) + j +word.substring(i);
                if (dictWords.lookup(wordToAdd.toLowerCase().trim()))
                {
                    output.add(wordToAdd.toLowerCase().trim());
                }
            }
        }
        return output;
    }

    private LinkedList<String> checkDeleted(String word) {
        LinkedList<String> output = new LinkedList<String>();
        for (int i = 0; i < word.length(); i++){
            if (dictWords.lookup(word.substring(0, i) + word.substring(i + 1))){
                output.add(word.substring(0, i) + word.substring(i + 1));
            }
        }
        return output;
    }

    private LinkedList<String> checkTransposedLetter(String word) {
        LinkedList<String> output = new LinkedList<String>();
        for (int i = 1; i < word.length(); i++){
            char[] charArray = word.toCharArray();
            char tmp = charArray[i];
            charArray[i] = charArray[i-1];
            charArray[i-1] = tmp;
            if (dictWords.lookup(String.valueOf(charArray).toLowerCase())){
                output.add(String.valueOf(charArray).toLowerCase());
            }
        }
        return output;
    }

    private LinkedList<String> checkInsertSpace(String word) {
        LinkedList<String> output = new LinkedList<String>();
        for (int i = 0; i < word.length(); i++){
            if (dictWords.lookup(word.substring(0, i).trim().toLowerCase()) &&
                    dictWords.lookup(word.substring(i).trim().toLowerCase())){
                output.add((String) word.substring(0, i) + " " +  word.substring(i));

            }
        }

        return output;
    }

    private String[] checkWord(String word) {

        if (dictWords.lookup(word)){
            return new String[]{word};
        }

        LinkedList<String> output = checkWrongLetter(word);
        LinkedList<String> checkAdded = new LinkedList<>();
        output.addAll(checkInsertedLetter(word));
        output.addAll(checkDeleted(word));
        output.addAll(checkInsertSpace(word));
        output.addAll(checkTransposedLetter(word));
        String[] tmpOutput = new String[output.size()];
        int looplength = output.size();
        for (int i = 0; i < looplength; i++){
            String wordToAdd = output.remove();
            if (!checkAdded.contains(wordToAdd)) {
                checkAdded.add(wordToAdd);
            }
        }
        return checkAdded.toArray(new String[0]);
    }

    public static void main(String[] args) {
        // args[0]: 0 if we should use a MyHashTable and 1 for a MyBloomFilter
        // args[1]: path to dict file
        // args[2]: path to input file

        SpellChecker checker = new SpellChecker();

        File dictionary = new File(args[1]);
        try {
            Reader reader = new FileReader(dictionary);
            checker.readDictionary(reader, args[0].equals("0"));


        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + dictionary);
            System.exit(1);
        }

        File inputFile = new File(args[2]);
        try {
            Scanner input = new Scanner(inputFile); // Reads list of words
            while(input.hasNext()){
                String word = input.nextLine().trim().toLowerCase();
                System.out.print(word + ": ");
                if (checker.checkWord(word).length == 1 && checker.checkWord(word)[0].equals(word)){
                    System.out.println("ok");
                }
                else if (checker.checkWord(word).length > 0){
                    String[] words = checker.checkWord(word);
                    System.out.println(String.join(", ", words));
                }
                else{
                    System.out.println("not found");
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + inputFile);
            System.exit(1);
        }
    }
}
