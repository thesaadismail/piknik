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
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public PiknikWindow()
    {
        super("Piknik");

        getContentPane().setBackground(Color.YELLOW);

        menuBar = new PiknikMenuBar(this);

        setJMenuBar(menuBar);

        //instantiates and adds a PiknikToolbar (contains tags and navigation buttons) to BorderLayout.WEST
        toolbarArea    = new PiknikToolbar(this, WIDTH, HEIGHT);
        getContentPane().add(toolbarArea, BorderLayout.WEST);

        //instantiates and adds a PiknikContentArea (contains an empty JPanel for now) to BorderLayout.CENTER
        contentArea = new PiknikContentArea(this, WIDTH, HEIGHT);
        getContentPane().add(contentArea, BorderLayout.CENTER);

        //instantiates and adds a PiknikStatusBar (contains a JLabel) to BorderLayout.SOUTH
        statusBar = new PiknikStatusBar(this, WIDTH, HEIGHT);
        getContentPane().add(statusBar, BorderLayout.SOUTH);

        //sets the minimum size to half the dimensions
        setMinimumSize(new Dimension(WIDTH/2,HEIGHT/2));

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
