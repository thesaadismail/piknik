package com.piknik.view.custom;

import com.piknik.global.PhotoControlSettings;
import com.piknik.model.DotAnnotation;
import com.piknik.model.TextAnnotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by sismail on 9/27/14.
 */

public class PhotoComponent extends JComponent
{
    private boolean isFlipped;
    private boolean isTyping;
    private Image image;
    private ArrayList<ArrayList<DotAnnotation>> lineAnnotationGroups;
    private ArrayList<TextAnnotation> textAnnotationsList;

    public PhotoComponent(Image img)
    {
        this.image = img;
        this.setBackground(Color.yellow);
        this.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));

        //create textAnnotationsList for textAnnotations
        this.textAnnotationsList = new ArrayList<TextAnnotation>();

        //create line annotation groups for groups of a list of dots
        this.lineAnnotationGroups = new ArrayList<ArrayList<DotAnnotation>>();

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

    //draw line annotations from the lineAnnotationGroups
    //this method takes the dots recorded and connects them together to form lines
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

        for(int i = 0; i<lineAnnotationGroups.size(); i++)
        {
            ArrayList<DotAnnotation> annotationPoints = lineAnnotationGroups.get(i);
            for(int j = 1; j<annotationPoints.size(); j++)
            {
                DotAnnotation firstPoint = annotationPoints.get(j-1);
                DotAnnotation secondPoint = annotationPoints.get(j);

                //user the color of the second dot. the order shouldn't matter too much since as you are drawing
                //you are unable to change the color of the line.
                g.setColor(secondPoint.annotationColor);
                g.drawLine(imageCoordinates.x+firstPoint.annotationCoordinates.x, imageCoordinates.y+firstPoint.annotationCoordinates.y,
                        imageCoordinates.x+secondPoint.annotationCoordinates.x, imageCoordinates.y+secondPoint.annotationCoordinates.y);
            }
        }
    }

    //draw text annotations from draw text annotation objects
    private void drawTextAnnotations(Graphics g)
    {
        Point imageCoordinates = getImageStartingCoordinates();
        int imageRightX = imageCoordinates.x + image.getWidth(null);

        for(int i = 0; i<textAnnotationsList.size(); i++)
        {
            TextAnnotation textAnnotation = textAnnotationsList.get(i);
            g.setColor(textAnnotation.annotationColor);

            Point textAnnotationPoint = textAnnotation.annotationCoordinates;
            String textAnnotationString = textAnnotation.text;

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
                            if(absoluteYValueForTextAnnotation > (image.getHeight(null) + imageCoordinates.y))
                            {
                                //only if the y value does not go before the top of the image then move the text box up
                                //if it does go above, then do not do anything for now. this was a design decision,
                                //as to what needs to be done if the text keeps on moving up due to size
                                //for now i have decided to not allow the user to see the addition text he is typing.
                                if(absoluteYValueForTextAnnotation >= imageCoordinates.y)
                                {
                                    textAnnotation.annotationCoordinates.y = textAnnotation.annotationCoordinates.y - 15;
                                }
                                break;
                            }

                            //go back an iteration to process the element again
                            --k;
                        }

                    }
                }
                else
                {
                    //if the sentence is too large then go back to the starting x position and choose a new line
                    absoluteXValueForTextAnnotation = textAnnotationPoint.x+imageCoordinates.x;
                    absoluteYValueForTextAnnotation += 15;
                    if(absoluteYValueForTextAnnotation > (image.getHeight(null) + imageCoordinates.y))
                    {
                        if(absoluteYValueForTextAnnotation >= imageCoordinates.y)
                        {
                            textAnnotation.annotationCoordinates.y = textAnnotation.annotationCoordinates.y - 15;
                        }
                        break;
                    }

                    //go back an iteration to process the element again
                    --j;
                }

            }

        }
    }

    //drawing a generic checkered pattern as background
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
        if(lineAnnotationGroups.size() == 0 || lineAnnotationGroups.get(lineAnnotationGroups.size()-1).size() != 0)
        {
            lineAnnotationGroups.add(new ArrayList<DotAnnotation>());
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

    //checks to see if x,y is in the image area
    private boolean isCoordinateInImage(Point coordinates)
    {
        Point imageCoordinates = getImageStartingCoordinates();

        int topLeftX = imageCoordinates.x;
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
        //if key is typed then check to see if its a backspace or escape.
        //if its backspace then backspace character and call repaint
        //if escape is pressed then exit out of typing
        //if its any other key, add it on to the string from the last text annotation in the list
        @Override
        public void keyTyped(KeyEvent e) {
            if(isFlipped)
            {
                if((int)e.getKeyChar() == 27)
                {
                    isTyping = false;
                    repaint();
                }
                else if((int)e.getKeyChar() == 8)
                {
                    TextAnnotation textAnnotation = textAnnotationsList.get(textAnnotationsList.size()-1);
                    if(textAnnotation.text.length() > 0)
                    {
                        textAnnotation.text = textAnnotation.text.substring(0, textAnnotation.text.length()-1);
                        repaint();
                    }
                }
                else if(isTyping)
                {
                    if(textAnnotationsList.size() > 0)
                    {
                        TextAnnotation textAnnotation = textAnnotationsList.get(textAnnotationsList.size()-1);
                        textAnnotation.text = (textAnnotation.text+e.getKeyChar());
                        repaint();
                    }
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

        //if mouse is clicked, check to see if its a double click
        //if it is a double click then flip the image
        //if its a regular click, and the image is flipped start text entry
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

                    TextAnnotation textAnnotation;
                    if(textAnnotationsList.size()!=0 &&
                            textAnnotationsList.get(textAnnotationsList.size()-1).text.equals(""))
                    {
                        textAnnotation = textAnnotationsList.get(textAnnotationsList.size()-1);
                        textAnnotation.annotationCoordinates = relativeClickedPoint;
                        textAnnotation.annotationColor = PhotoControlSettings.textAnnotationColor;
                    }
                    else
                    {
                        textAnnotation = new TextAnnotation(relativeClickedPoint, "");
                        textAnnotationsList.add(textAnnotation);
                        textAnnotation.annotationColor = PhotoControlSettings.textAnnotationColor;
                    }

                    repaint();
                }
            }
        }
    }

    private class ComponentMouseMotionListener implements MouseMotionListener {


        //if mouse is dragged and image is flipped and point that the mouse was dragged to
        //is inside the image then add it to the dot annotations list
        //if it is not in the image then create a new grouping so when the user does come back in the image
        //the line does not connect. (see below for comments)
        @Override
        public void mouseDragged(MouseEvent e) {
            if(isFlipped)
            {
                Point point = new Point(e.getX(), e.getY());

                if(isCoordinateInImage(point))
                {
                    Point imageStartingCoordinates = getImageStartingCoordinates();
                    Point relativeClickedPoint = new Point(e.getX()-imageStartingCoordinates.x,e.getY()-imageStartingCoordinates.y);

                    ArrayList<DotAnnotation> annotationPoints = lineAnnotationGroups.get(lineAnnotationGroups.size()-1);
                    DotAnnotation dot = new DotAnnotation(relativeClickedPoint);
                    dot.annotationColor = PhotoControlSettings.lineAnnotationColor;
                    annotationPoints.add(dot);
                    repaint();
                }
                else
                {
                    //if coordinate is not in image, then create a new grouping so if the user does come back in the image
                    //the line does not connect from where the user left off
                    createNewPointGrouping();
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}


