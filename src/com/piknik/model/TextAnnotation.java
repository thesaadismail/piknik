package com.piknik.model;

import java.awt.*;

/**
 * Created by sismail on 9/30/14.
 */
public class TextAnnotation extends BaseAnnotation
{
    public String text;

    public TextAnnotation(Point coordinates, String text)
    {
        super(coordinates);
        this.text = text;
    }

    public TextAnnotation(Point coordinates)
    {
        super(coordinates);
        this.text = "";
    }

}
