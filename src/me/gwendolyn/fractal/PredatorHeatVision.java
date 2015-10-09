package me.gwendolyn.fractal;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Gwendolyn on 10/2/2015.
 */
public class PredatorHeatVision extends JComponent {
    private Random rand = new Random(System.currentTimeMillis());
    private boolean[][] b;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // If the width or height changes, b gets the new width and height
        if (b == null || b.length != getWidth() || b.length > 0 && b[0].length != getHeight()) {
            b = new boolean[getWidth()][getHeight()];
        }

        Color colorTopLeft = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        Color colorTopRight = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        Color colorBottomLeft = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        Color colorBottomRight = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        g2.setColor(colorTopLeft);
        drawPoint(g2, -1, -1);

        g2.setColor(colorTopRight);
        drawPoint(g2, getWidth() - 2, -1);

        g2.setColor(colorBottomLeft);
        drawPoint(g2, -1, getHeight() - 2);

        g2.setColor(colorBottomRight);
        drawPoint(g2, getWidth() - 2, getHeight() - 2);

        rectangles(0, 0, getWidth()-1, getHeight()-1, g2, colorTopLeft, colorTopRight, colorBottomLeft, colorBottomRight);
    }

    private Color averageColors(Color... colors) {
        int r = 0;
        int g = 0;
        int b = 0;

        for (Color x : colors) {
            r += x.getRed();
            g += x.getGreen();
            b += x.getBlue();
        }
        r/=colors.length;
        g/=colors.length;
        b/=colors.length;

        return new Color(r, g, b);
    }

    private void rectangles(int minX, int minY, int maxX, int maxY, Graphics2D g2,
                           Color colorTopLeft, Color colorTopRight, Color colorBottomLeft, Color colorBottomRight) {

        if(maxX-minX < 2 && maxY-minY < 2)
            return;
        int midX = minX + ((maxX-minX)/2);
        int midY = minY + ((maxY-minY)/2);

        Color colorMidPoint = averageColors(colorTopLeft, colorTopRight, colorBottomLeft, colorBottomRight);
        g2.setColor(colorMidPoint);
        drawPoint(g2, midX - 1, midY - 1);

        Color topMidPoint = averageColors(colorTopLeft, colorTopRight);
        g2.setColor(topMidPoint);
        drawPoint(g2, midX - 1, minY - 1);

        Color MidRightPoint = averageColors(colorTopRight, colorBottomRight);
        g2.setColor(MidRightPoint);
        drawPoint(g2, maxX - 1, midY - 1);

        Color bottomMidPoint = averageColors(colorBottomLeft, colorBottomRight);
        g2.setColor(bottomMidPoint);
        drawPoint(g2, midX - 1, maxY - 1);

        Color MidLeftPoint = averageColors(colorTopLeft, colorBottomLeft);
        g2.setColor(MidLeftPoint);
        drawPoint(g2, minX - 1, midY - 1);

        rectangles(minX, minY, midX, midY, g2, colorTopLeft, topMidPoint, MidLeftPoint, colorMidPoint); // top left
        rectangles(midX, minY, maxX, midY, g2, topMidPoint, colorTopRight, colorMidPoint, MidRightPoint); // top right
        rectangles(minX, midY, midX, maxY, g2, MidLeftPoint, colorMidPoint, colorBottomLeft, bottomMidPoint); // bot left
        rectangles(midX, midY, maxX, maxY, g2, colorMidPoint, MidRightPoint, bottomMidPoint, colorBottomRight); // bot right
    }

    private void drawPoint(Graphics2D g2, int x, int y) {
        if(!b[x+1][y+1]) {
            g2.drawOval(x, y, 1, 1);
            b[x+1][y+1] = true;
        }
    }
}
