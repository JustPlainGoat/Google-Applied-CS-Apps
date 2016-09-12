package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;


public class PuzzleBoard {
    private static final String tag = "tag";

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<PuzzleTile> tiles;

    PuzzleBoard(Bitmap bitmap, int parentWidth, int phoneWidth, int phoneHeight) {
        tiles = new ArrayList<>();
        splitImage(bitmap, parentWidth, phoneWidth, phoneHeight);
    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    private void splitImage(Bitmap bitmap, int parentWidth, int phoneWidth, int phoneHeight) {
        //For the number of rows and columns of the grid to be displayed
        int rows = 4, cols = 4;

        //For height and width of the small image chunks
        int chunkHeight = phoneHeight / rows;
        int chunkWidth = phoneWidth / cols;

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, phoneWidth, phoneHeight, true);

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x = 0; x < rows; x++){
            int xCoord = 0;
            for(int y = 0; y < cols; y++){
                tiles.add(new PuzzleTile(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight), x + y));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {
        return null;
    }

    public int priority() {
        return 0;
    }

}
