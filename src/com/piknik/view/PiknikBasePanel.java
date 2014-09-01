package com.piknik.view;

import javax.swing.*;

/**
 * PiknikBasePanel should be used on any JPanel inside Piknik window. It implements default constructors and
 * lays out abstract methods that are required for UI setup.
 *
 * Created by sismail on 8/28/14.
 */
public abstract class PiknikBasePanel extends JPanel
{
    /**
     * The parent frame that this base panel is contained in. For this application the parentFrame will always be
     * an instance of PiknikWindow
     */
    protected PiknikWindow parentFrame;

    /**
     * Constructor for the Abstract BasePanel class. This will call the default constructor on JPanel.
     */
    public PiknikBasePanel()
    {
        super();
    }

    /**
     * Consturctur for PiknikBasePanel that takes in parentFrame, width, and height. The width and height refers to
     * the dimensions of the current frame/panel and not the parentFrame.
     *
     * Current the width and height are not being used to set the width and height of the current panel. These
     * dimensions are passed to the abstract setup method. It is up to the setup method to determine
     * what is done with the width and height.
     *
     * @param parentFrame the frame that the current panel is contained in
     * @param width the preferred width of the current panel
     * @param height the preferred height of the current panel
     */
    public PiknikBasePanel(PiknikWindow parentFrame, int width, int height)
    {
        super();
        this.parentFrame = parentFrame;

        setup(width, height);
    }

    /**
     * Abstract setup method. This is called in the PiknikBasePanel constructor. Your UI setup (e.g. adding panels
     * buttons, images, and etc) should go inside this method.
     *
     * @param width the preferred width of the current panel
     * @param height the preferred height of the current panel
     */
    protected abstract void setup(int width, int height);
}
