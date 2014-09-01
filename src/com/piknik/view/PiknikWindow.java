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

    //SIZE PROPERTIES
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public PiknikWindow()
    {
        super("Piknik");

        getContentPane().setBackground(Color.YELLOW);

        menuBar = new PiknikMenuBar(this);
        setJMenuBar(menuBar);

        toolbarArea    = new PiknikToolbar(this, WIDTH, HEIGHT);
        getContentPane().add(toolbarArea, BorderLayout.WEST);

        contentArea = new PiknikContentArea(this, WIDTH, HEIGHT);
        getContentPane().add(contentArea, BorderLayout.CENTER);

        statusBar = new PiknikStatusBar(this, WIDTH, HEIGHT);
        getContentPane().add(statusBar, BorderLayout.SOUTH);


        setMinimumSize(new Dimension(WIDTH/2,HEIGHT/2));

        //Create and set up the window.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    public void updateStatus(String status)
    {
        statusBar.setStatus(status);
    }

}
