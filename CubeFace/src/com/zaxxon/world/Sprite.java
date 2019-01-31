package com.zaxxon.world;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite {
	protected Image image;
    protected int positionX;
    protected int positionY;
    protected int width;
    protected int height;

    public Sprite() {
        positionX = 0;
        positionY = 0;
    }

    public void setImage(Image i) {
        image = i;
        width = (int) i.getWidth();
        height = (int) i.getHeight();
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(int x, int y) {
        positionX = x;
        positionY = y;
    }


    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]";
    }
}