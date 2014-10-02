package com.piknik.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Menubar for the Piknik application.
 * Created by sismail on 8/28/14.
 */
public class PiknikMenuBar extends JMenuBar
{
    /**
     * The parent frame that this base panel is contained in. For this application the parentFrame will always be
     * an instance of PiknikWindow
     */
    private PiknikWindow parentFrame;

    public PiknikMenuBar(PiknikWindow parentFrame)
    {
        super();
        this.parentFrame = parentFrame;

        setUpMenuBar();
    }

    /**
     * Sets up the two menus (File and View) and creates the separate menu items for each of those menus.
     */
    private void setUpMenuBar()
    {
        Color menuBarBackgroundColor = new Color(0xDADADA);
        setBackground(menuBarBackgroundColor);

        //File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setBackground(menuBarBackgroundColor);
        add(fileMenu);

        //Menu Items for the File Menu
        JMenuItem importMenuItem = new JMenuItem("Import", KeyEvent.VK_I);
        importMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parentFrame.updateStatus("Opened File Dialog");
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(parentFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    parentFrame.updateStatus("File was Selected for Import");
                    File file = fc.getSelectedFile();
                    parentFrame.setImageInContentArea(file);
                }
                else
                {
                    //action was cancelled by the user
                    parentFrame.updateStatus("Canceled Import");
                }

            }
        });;
        fileMenu.add(importMenuItem);

        JMenuItem deleteMenuItem = new JMenuItem("Delete",KeyEvent.VK_D);
        deleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parentFrame.updateStatus("Photo Has Been Removed from Piknik");
                parentFrame.removeImageFromContentArea();
            }
        });
        fileMenu.add(deleteMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit",KeyEvent.VK_E);
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parentFrame.updateStatus("Exiting...");
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        //View Menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setBackground(menuBarBackgroundColor);
        add(viewMenu);

        //View Modes Radio Button Group
        ButtonGroup viewModeButtonGroup = new ButtonGroup();

        JRadioButtonMenuItem viewModePhotoViewerMenuItem = new JRadioButtonMenuItem("Photo Viewer");
        viewModePhotoViewerMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parentFrame.updateStatus("Photo Viewer Mode Selected");
            }
        });
        viewModeButtonGroup.add(viewModePhotoViewerMenuItem);
        viewMenu.add(viewModePhotoViewerMenuItem);

        JRadioButtonMenuItem viewModeBrowserMenuItem = new JRadioButtonMenuItem("Browser");
        viewModeBrowserMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parentFrame.updateStatus("Browser Mode Selected");
            }
        });
        viewModeButtonGroup.add(viewModeBrowserMenuItem);
        viewMenu.add(viewModeBrowserMenuItem);

        JRadioButtonMenuItem viewModeSplitModeMenuItem = new JRadioButtonMenuItem("Split Mode");
        viewModeSplitModeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parentFrame.updateStatus("Split Mode Selected");
            }
        });
        viewModeButtonGroup.add(viewModeSplitModeMenuItem);
        viewMenu.add(viewModeSplitModeMenuItem);

        viewModeButtonGroup.setSelected(viewModePhotoViewerMenuItem.getModel(), true);
    }
}