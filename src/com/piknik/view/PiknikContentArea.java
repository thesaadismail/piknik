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

    protected void setup(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
    }
}
