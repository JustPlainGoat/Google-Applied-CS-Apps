package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private Random random = new Random();

    private ArrayList<String> oddLengthWords;
    private ArrayList<String> evenLengthWords;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        oddLengthWords = new ArrayList<>();
        evenLengthWords = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix.isEmpty()) {
            return words.get(random.nextInt(words.size()));
        }
        int index = binarySearch(prefix, findMiddle(0, words.size() - 1), 0, words.size() - 1);
        if(index != -1) {
            return words.get(index);
        } else {
            return null;
        }
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        oddLengthWords.clear();
        evenLengthWords.clear();

        if(prefix.isEmpty()) {
            return words.get(random.nextInt(words.size()));
        }
        betterBinarySearch(prefix, findMiddle(0, words.size() - 1), 0, words.size() - 1);
        if(!GhostActivity.userStartedFirst) {
            if(evenLengthWords.isEmpty()) {
                return null;
            }
            return evenLengthWords.get(random.nextInt(evenLengthWords.size()));
        } else {
            if(oddLengthWords.isEmpty()) {
                return null;
            }
            return oddLengthWords.get(random.nextInt(oddLengthWords.size()));
        }
    }

    private int findMiddle(int start, int end) {
        return (start + end) / 2;
    }

    private int binarySearch(String target, int middle, int start, int end) {
        if(start > end) {
            return -1;
        }
        String word = words.get(middle);
        if(word.startsWith(target)) {
            return middle;
        } else if(target.compareTo(word) < 0) {
            // less than
            return binarySearch(target, findMiddle(start, middle - 1), start, middle - 1);
        } else {
            // greater than
            return binarySearch(target, findMiddle(middle + 1, end), middle + 1, end);
        }
    }

    private void betterBinarySearch(String target, int middle, int start, int end) {
        if(start > end) {
            return;
        }
        Log.d("TAG", "above");
        String word = words.get(middle);
        if(target.length() < word.length() && target.equals(word.substring(0, target.length()))) {
            Log.d("TAG", "same");
            if(word.length() % 2 == 0) {
                evenLengthWords.add(word);
                Log.d("TAG","even");
            } else {
                oddLengthWords.add(word);
                Log.d("TAG","odd");
            }
        }

        if(target.length() < word.length() && target.compareTo(word.substring(0, target.length())) < 0) {
            // less than
            Log.d("TAG","less than");
            betterBinarySearch(target, findMiddle(start, middle - 1), start, middle - 1);
        } else if(target.length() < word.length()){
            // greater than
            Log.d("TAG","greater than");
            betterBinarySearch(target, findMiddle(middle + 1, end), middle + 1, end);
        }
    }

}
