package com.piknik.view;

import javax.swing.*;
import java.awt.*;

/**
 * The status bar located on the bottom of the Piknik Window. The status bar is located at BorderLayout.SOUTH
 * and only contains a JLabel to represent the status.
 * Created by sismail on 8/28/14.
 */
public class PiknikStatusBar extends PiknikBasePanel
{
    private JLabel statusBarLabel;

    public PiknikStatusBar(PiknikWindow parentFrame, int width, int height)
    {
        super(parentFrame, width, height);
    }

    /**
     * Implementation of abstract method. This will set the size and background color of the status bar panel.
     * It will also instantiate the status label with the text "No Status".
     *
     * @param width the preferred width of the current panel
     * @param height the preferred height of the current panel
     */
    public void setup(int width, int height)
    {
        Color statusBarBackgroundColor = new Color(0xBFBFBF);

        setPreferredSize(new Dimension(WIDTH, 25));
        setBackground(statusBarBackgroundColor);

        statusBarLabel = new JLabel("No Status");
        //Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        //statusBarLabel.setBorder(paddingBorder);
        //statusBarLabel.setPreferredSize(new Dimension(WIDTH, 25));

        add(statusBarLabel, BorderLayout.CENTER);
    }

    /**
     * SetStatus will set the label text to the string that was passed in. It will also start a timer for 10 seconds
     * and clear the status back to "No Status" after that time has passed.
     * @param statusText the status that needs to be displayed
     */
    protected void setStatus(String statusText)
    {
        statusBarLabel.setText(statusText);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        statusBarLabel.setText("No Status");
                    }
                },
                10000
        );
    }


}

