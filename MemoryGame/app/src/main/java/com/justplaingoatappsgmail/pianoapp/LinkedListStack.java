package com.justplaingoatappsgmail.pianoapp;

//THIS IS ACTUALLY A STACK!
public class LinkedListStack {

    private Node head;

    public LinkedListStack(){
        head = null;
    }

    public void add(int color){
        if(head == null){
            head = new Node(color, null);
        } else {
            head = new Node(color, head);
        }
    }

    public int remove(){
        int color = head.getColor();
        Node temp = head;
        head = temp.getLink();
        temp = null;
        return color;
    }



}
