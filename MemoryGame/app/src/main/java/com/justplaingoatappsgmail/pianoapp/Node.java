package com.justplaingoatappsgmail.pianoapp;

public class Node {

    private int color;

    private Node link;

    public Node(int color, Node link){
        this.color = color;
        this.link = link;
    }

    public int getColor(){
        return color;
    }

    public Node getLink(){
        return link;
    }

}