package com.piknik.view;

import com.piknik.view.custom.PhotoComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sismail on 8/28/14.
 */
public class PiknikContentArea extends PiknikBasePanel {

    public PiknikContentArea(PiknikWindow parentFrame, int width, int height)
    {
        super(parentFrame, width, height);
    }

    /**
     * Implementation of the abstract setup method. For PiknikContentArea, we only set the background color and
     * preferred size. Later on this will manage viewing of photos.
     *
     * @param width the preferred width of the current panel
     * @param height the preferred height of the current panel
     */
    protected void setup(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.white);

        setLayout(new BorderLayout());
    }

    //set photo component in content area
    public void setImage(Image image)
    {
        //remove any existing photo components
        removeImage();

        PhotoComponent component = new PhotoComponent(image);
        JScrollPane scrollPane = new JScrollPane(component);
        add(scrollPane, BorderLayout.CENTER);
    }

    //remove photo component from content area
    public void removeImage()
    {
        //as of now only the photo component exists in border layout.center so we remove whatever is in there
        //when removing the photo component
        BorderLayout layout = (BorderLayout)getLayout();
        Component component = layout.getLayoutComponent(BorderLayout.CENTER);
        if(component!=null)
            remove(component);

        repaint();
    }
}
