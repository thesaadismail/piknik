package com.piknik.view.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

/**
 * Created by sismail on 9/27/14.
 */

public class PhotoComponent extends JComponent
{
    private boolean isFlipped;
    private boolean isTyping;
    private Image image;
    private ArrayList<ArrayList<Point>> annotationPointGroups;
    private ArrayList<SimpleEntry<Point, String>> textAnnotationsList;

    public PhotoComponent(Image img)
    {
        this.image = img;
        this.setBackground(Color.yellow);
        this.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));

        this.textAnnotationsList = new ArrayList<SimpleEntry<Point, String>>();
        this.annotationPointGroups = new ArrayList<ArrayList<Point>>();

        addMouseListener(new ComponentMouseListener());
        addMouseMotionListener(new ComponentMouseMotionListener());
        addKeyListener(new ComponentKeyListener());
    }

    //Paint Methods
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawCheckeredPattern(g);

        //if picture is not flipped then draw image. if picture is flipped, then flip the image and draw annotations
        if(!isFlipped)
        {
            Point imageCoordinates = getImageStartingCoordinates();
            g.drawImage(image, imageCoordinates.x, imageCoordinates.y, null);
        }
        else
        {
            drawLineAnnotations(g);
            drawTextAnnotations(g);
        }
    }

    private void drawLineAnnotations(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.white);

        Point imageCoordinates = getImageStartingCoordinates();
        g.fillRect(imageCoordinates.x, imageCoordinates.y, image.getWidth(null), image.getHeight(null));

        RenderingHints rh = g2.getRenderingHints();
        rh.put (RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints (rh);

        g.setColor(Color.blue);
        for(int i = 0; i<annotationPointGroups.size(); i++)
        {
            ArrayList<Point> annotationPoints = annotationPointGroups.get(i);
            for(int j = 1; j<annotationPoints.size(); j++)
            {
                Point firstPoint = annotationPoints.get(j-1);
                Point secondPoint = annotationPoints.get(j);
                g.drawLine(imageCoordinates.x+firstPoint.x, imageCoordinates.y+firstPoint.y,
                        imageCoordinates.x+secondPoint.x, imageCoordinates.y+secondPoint.y);
            }
        }
    }

    private void drawTextAnnotations(Graphics g)
    {
        g.setColor(Color.DARK_GRAY);

        Point imageCoordinates = getImageStartingCoordinates();
        int imageRightX = imageCoordinates.x + image.getWidth(null);

        for(int i = 0; i<textAnnotationsList.size(); i++)
        {
            SimpleEntry<Point, String> textAnnotation = textAnnotationsList.get(i);
            Point textAnnotationPoint = textAnnotation.getKey();
            String textAnnotationString = textAnnotation.getValue();

            //if the user is currently typing and we are at the last textAnnotation then add a cursor at the end
            if(isTyping && i == textAnnotationsList.size()-1)
            {
                textAnnotationString = textAnnotationString + "_";
            }

            //split string into words to handle word wrap
            String[] textAnnotationStringArray = textAnnotationString.split(" ");
            int absoluteXValueForTextAnnotation = textAnnotationPoint.x+imageCoordinates.x;
            int absoluteYValueForTextAnnotation = textAnnotationPoint.y+imageCoordinates.y;
            for(int j = 0; j < textAnnotationStringArray.length; j++)
            {
                String wordInTextAnnotation = textAnnotationStringArray[j]+" ";

                FontMetrics metrics = g.getFontMetrics();
                int wordWidth = metrics.stringWidth(wordInTextAnnotation);

                if(isCoordinateInImage(new Point(absoluteXValueForTextAnnotation+wordWidth, absoluteYValueForTextAnnotation)))
                {
                    g.drawString(wordInTextAnnotation, absoluteXValueForTextAnnotation, absoluteYValueForTextAnnotation);
                    absoluteXValueForTextAnnotation += wordWidth;
                }
                else if(imageRightX - (wordWidth+absoluteXValueForTextAnnotation) < 0)
                {
                    //split up word into letters if the word is too large
                    String[] lettersArray = wordInTextAnnotation.split("");
                    for(int k = 0; k<lettersArray.length; k++) {
                        String letterTextAnnotation = lettersArray[k];

                        int letterWidth = metrics.stringWidth(letterTextAnnotation);

                        if (isCoordinateInImage(new Point(absoluteXValueForTextAnnotation + letterWidth, absoluteYValueForTextAnnotation))) {
                            g.drawString(letterTextAnnotation, absoluteXValueForTextAnnotation, absoluteYValueForTextAnnotation);
                            absoluteXValueForTextAnnotation += letterWidth;
                        }
                        else
                        {
                            //if the sentence is too large then go back to the starting x position and choose a new line
                            //if the new line below exceeds the image coordinates, then wrap up to the top
                            absoluteXValueForTextAnnotation = textAnnotationPoint.x + imageCoordinates.x;
                            absoluteYValueForTextAnnotation += 15;
                            if(absoluteYValueForTextAnnotation > (image.getWidth(null) + imageCoordinates.y))
                            {
                                absoluteYValueForTextAnnotation = 2;
                            }

                            //go back an iteration to process the element again
                            --k;
                        }

                    }
                }
                else
                {
                    //if the sentence is too large then go back to the starting x position and choose a new line
                    //if the new line below exceeds the image coordinates, then wrap up to the top
                    absoluteXValueForTextAnnotation = textAnnotationPoint.x+imageCoordinates.x;
                    if(absoluteYValueForTextAnnotation > (image.getWidth(null) + imageCoordinates.y))
                    {
                        absoluteYValueForTextAnnotation = 2;
                    }

                    //go back an iteration to process the element again
                    --j;
                }

            }

        }
    }

    private void drawCheckeredPattern(Graphics g)
    {
        Color lightGreyCheckeredBox = new Color(0xE6E6E6);

        int boxDimension = 10;
        int numOfColumns = getWidth()/boxDimension;
        int numOfRows = getHeight()/boxDimension;

        for(int row = 0; row <= numOfRows; row++)
        {
            for(int col = 0; col <= numOfColumns; col++)
            {
                if(row%2 == 0)
                {
                    if(col%2 == 0)
                    {
                        g.setColor(Color.white);
                    }
                    else
                    {
                        g.setColor(lightGreyCheckeredBox);
                    }
                }
                else
                {
                    if(col%2 == 0)
                    {
                        g.setColor(lightGreyCheckeredBox);
                    }
                    else
                    {
                        g.setColor(Color.white);
                    }
                }


                g.fillRect(boxDimension*col,boxDimension*row,boxDimension,boxDimension);
            }
        }
    }

    //Helper Methods
    private double getCenterX()
    {
        return getWidth()/2.0;
    }

    private double getCenterY()
    {
        return getHeight()/2.0;
    }

    //Annotation Grouping Methods
    private void createNewPointGrouping()
    {
        //check the annotationPointGroups list to see if the last list is empty
        //if its not empty, create a new empty grouping
        if(annotationPointGroups.size() == 0 || annotationPointGroups.get(annotationPointGroups.size()-1).size() != 0)
        {
            annotationPointGroups.add(new ArrayList<Point>());
        }
    }

    //Coordinate Helper Methods
    //calculates and returns the top left corner of the image within the component
    private Point getImageStartingCoordinates()
    {
        int relativeWidthStartingPoint = (image.getWidth(null)/2);
        int relativeHeightStartingPoint = (image.getHeight(null)/2);
        return new Point((int)(getCenterX()-relativeWidthStartingPoint),
                (int)(getCenterY()-relativeHeightStartingPoint));
    }

    private boolean isCoordinateInImage(Point coordinates)
    {
        Point imageCoordinates = getImageStartingCoordinates();

        int topLeftX = imageCoordinates.y;
        int topRightX = imageCoordinates.x+image.getWidth(null);

        int topLeftY = imageCoordinates.y;
        int bottomLeftY = imageCoordinates.y+image.getHeight(null);

        if(coordinates.x >= topLeftX && coordinates.x <= topRightX && coordinates.y >= topLeftY && coordinates.y <= bottomLeftY)
        {
            return true;
        }

        return false;
    }

    private class ComponentKeyListener implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e) {

            if((int)e.getKeyChar() == 27)
            {
                isTyping = false;
                repaint();
            }
            else if((int)e.getKeyChar() == 8)
            {
                SimpleEntry<Point, String> text = textAnnotationsList.get(textAnnotationsList.size()-1);
                if(text.getValue().length() > 0)
                {
                    text.setValue(text.getValue().substring(0, text.getValue().length()-1));
                    repaint();
                }
            }
            else if(isTyping)
            {
                if(textAnnotationsList.size() > 0)
                {
                    SimpleEntry<Point, String> textAnnotation = textAnnotationsList.get(textAnnotationsList.size()-1);
                    textAnnotation.setValue(textAnnotation.getValue()+e.getKeyChar());
                    repaint();
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    //Mouse Listener for PhotoComponent
    private class ComponentMouseListener implements MouseListener
    {
        public void mousePressed(MouseEvent e) {
            //when mouse is pressed check to see if we are in flipped mode
            //if we are in flipped mode then check to see if the coordinate of the mouse is inside the image
            //if it is inside the image, then create new point grouping

            //what is happening here is that we are adding an empty list to annotationPointGroups when mouse is pressed
            //this way there will be a new grouping of lines and the user can draw naturally while pressing and
            //releasing the mouse

            if(isFlipped &&
                    isCoordinateInImage(new Point(e.getX(), e.getY())))
            {
                createNewPointGrouping();
            }
        }

        public void mouseReleased(MouseEvent e) {


        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
            Point clickedPoint = new Point(e.getX(),e.getY());
            if (e.getClickCount() == 2) {
                //if its a double click then flip image
                if(isCoordinateInImage(clickedPoint))
                {
                    isFlipped = !isFlipped;
                    repaint();
                }
            }
            else
            {
                if(isFlipped)
                {
                    Point imageStartingCoordinates = getImageStartingCoordinates();
                    Point relativeClickedPoint = new Point(e.getX()-imageStartingCoordinates.x,e.getY()-imageStartingCoordinates.y);

                    requestFocusInWindow();

                    isTyping = true;
                    textAnnotationsList.add(new SimpleEntry<Point, String>(relativeClickedPoint, ""));

                    repaint();
                }
            }
        }
    }

    private class ComponentMouseMotionListener implements MouseMotionListener {


        @Override
        public void mouseDragged(MouseEvent e) {
            Point point = new Point(e.getX(), e.getY());

            if(isCoordinateInImage(point))
            {
                Point imageStartingCoordinates = getImageStartingCoordinates();
                Point relativeClickedPoint = new Point(e.getX()-imageStartingCoordinates.x,e.getY()-imageStartingCoordinates.y);

                ArrayList<Point> annotationPoints = annotationPointGroups.get(annotationPointGroups.size()-1);
                annotationPoints.add(relativeClickedPoint);
                repaint();
            }
            else
            {
                //if coordinate is not in image, then create a new grouping so if the user does come back in the image
                //the line does not connect from where the user left off
                createNewPointGrouping();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}


