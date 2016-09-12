package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final String TAG = "TAG";

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private int wordLength = DEFAULT_WORD_LENGTH;

    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<String,ArrayList<String>> lettersToWord;
    private HashMap<Integer, ArrayList<String>> sizeToWords;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        wordList = new ArrayList<String>();
        wordSet = new HashSet();
        lettersToWord = new HashMap();
        sizeToWords = new HashMap();

        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);

            if(sizeToWords.containsKey(word.length())) {
                sizeToWords.get(word.length()).add(word);
            } else {
                ArrayList<String> list = new ArrayList();
                list.add(word);
                sizeToWords.put(word.length(), list);
            }

            if(lettersToWord.containsKey(sortLetters(word))) {
                lettersToWord.get(sortLetters(word)).add(word);
            } else {
                ArrayList<String> list = new ArrayList();
                list.add(word);
                lettersToWord.put(sortLetters(word), list);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(!wordSet.contains(word) || word.contains(base)) {
            return false;
        }
        return true;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        for(int i = 0; i < wordList.size(); i++) {
            String sortedListWord = sortLetters(wordList.get(i));
            String sortedTargetWord = sortLetters(targetWord);
            if(sortedListWord.length() == sortedTargetWord.length()) {
                boolean isAnagram = true;
                for(int j = 0; j < sortedListWord.length(); j++) {
                    if(sortedListWord.toCharArray()[j] != sortedTargetWord.toCharArray()[j]) {
                        isAnagram = false;
                    }
                }
                if(isAnagram) {
                    result.add(wordList.get(i));
                }
            }
        }

        return result;
    }

    private String sortLetters(String targetWord) {
        char[] charArray = targetWord.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for(int i = 0; i < alphabet.length; i++) {
            String sortedWord = sortLetters(word + alphabet[i]);
            if(lettersToWord.containsKey(sortedWord)) {
                ArrayList<String> anagramList = lettersToWord.get(sortedWord);
                for(int j = 0; j < anagramList.size(); j++) {
                    result.add(anagramList.get(j));
                }
            }
        }

        for(int i = 0; i < result.size(); i++) {
            Log.d(TAG, result.get(i));
        }

        return result;
    }

    public String pickGoodStarterWord() {
        boolean isGoodStarterWord = false;
        String word = "";
        while(!isGoodStarterWord) {
            ArrayList<String> lengthStrings = sizeToWords.get(wordLength);
            word = lengthStrings.get(random.nextInt(lengthStrings.size()));
            if(getAnagramsWithOneMoreLetter(word).size() >= MIN_NUM_ANAGRAMS) {
                if(word.length() == MAX_WORD_LENGTH) {
                    wordLength = DEFAULT_WORD_LENGTH;
                } else {
                    wordLength++;
                }
                isGoodStarterWord = true;
            }
        }
        return word;
    }
}
