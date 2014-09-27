package com.piknik.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sismail on 8/28/14.
 */
public class PiknikWindow extends JFrame
{
    private PiknikMenuBar menuBar;
    private PiknikToolbar toolbarArea;
    private PiknikContentArea contentArea;
    private PiknikStatusBar statusBar;

    //SIZE PROPERTIES of PiknikWindow
    private final int MIN_WIDTH = 680;
    private final int MIN_HEIGHT = 300;
    private final int WIDTH = 900;
    private final int HEIGHT = 600;

    public PiknikWindow()
    {
        super("Piknik");

        menuBar = new PiknikMenuBar(this);

        setJMenuBar(menuBar);

        //instantiates and adds a PiknikToolbar (contains tags and navigation buttons) to BorderLayout.WEST
        toolbarArea    = new PiknikToolbar(this, 250, 100);
        getContentPane().add(toolbarArea, BorderLayout.WEST);

        //instantiates and adds a PiknikContentArea (contains an empty JPanel for now) to BorderLayout.CENTER
        contentArea = new PiknikContentArea(this, 650, 475);
        getContentPane().add(contentArea, BorderLayout.CENTER);

        //instantiates and adds a PiknikStatusBar (contains a JLabel) to BorderLayout.SOUTH
        statusBar = new PiknikStatusBar(this, WIDTH, 25);
        getContentPane().add(statusBar, BorderLayout.SOUTH);

        //set preferred size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //sets the minimum size to half the dimensions
        setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));

        //setting close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    /**
     * Updates the status inside the statusBar. Sub panels that have access to the parentFrame can use this method
     * to update the status instead of getting an instance of statusBar and doing it manually.
     * @param status the status that needs to be set
     */
    public void updateStatus(String status)
    {
        statusBar.setStatus(status);
    }

}
