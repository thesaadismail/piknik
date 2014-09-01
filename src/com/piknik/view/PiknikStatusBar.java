package com.piknik.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sismail on 8/28/14.
 */
public class PiknikStatusBar extends PiknikBasePanel
{
    private JLabel statusBarLabel;

    public PiknikStatusBar(PiknikWindow parentFrame, int width, int height)
    {
        super(parentFrame, width, height);
    }

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

