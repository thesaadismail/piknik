package com.piknik.view;

import javax.swing.*;

/**
 * Created by sismail on 8/28/14.
 */
public abstract class PiknikBasePanel extends JPanel
{
    protected PiknikWindow parentFrame;

    public PiknikBasePanel()
    {
        super();
    }

    public PiknikBasePanel(PiknikWindow parentFrame, int width, int height)
    {
        super();
        this.parentFrame = parentFrame;

        setup(width, height);
    }

    protected abstract void setup(int width, int height);
}
