package com.piknik.view;

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
        setBackground(Color.WHITE);
    }
}
