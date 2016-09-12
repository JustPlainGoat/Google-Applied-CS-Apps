package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        if(s.equals("") && !children.containsKey("")) {
            children.put("", null);
            isWord = true;
        } else {
            String letter = s.substring(0,1);
            String restOfWord = s.substring(1, s.length());
            if(!children.containsKey(letter)) {
                children.put(letter, new TrieNode());
            }
            children.get(letter).add(restOfWord);
        }
    }

    public boolean isWord(String s) {
        if(s.equals("")) {
            return true;
        }

        String letter = s.substring(0,1);
        String restOfWord = s.substring(1, s.length());
        if(children.containsKey(letter)) {
            return children.get(letter).isWord(restOfWord);
        }
        return false;
    }

    public String getAnyWordStartingWith(String s) {
        String letter = s.substring(0,1);
        String restOfWord = s.substring(1, s.length());
        String word = "";

        if(restOfWord.equals("")) {
            if(!children.containsKey(letter)) {
                return null;
            }

            boolean found = false;
            TrieNode cursor = children.get(letter);

            while(!found) {
                Object[] strings = cursor.children.keySet().toArray();
                String index = (String) strings[new Random().nextInt(strings.length)];

                word += index;

                TrieNode nextCursor = cursor.children.get(index);
                if (nextCursor.children.containsKey("")) {
                    found = true;
                    return word;
                } else {
                    cursor = nextCursor;
                }
            }
        }

        return "";
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
