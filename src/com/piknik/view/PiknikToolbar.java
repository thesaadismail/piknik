package com.piknik.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by sismail on 8/28/14.
 *
 * Back/Forward Icon:
 * Icon made by Rami McMin - www.flaticon.com
 */
public class PiknikToolbar extends PiknikBasePanel
{
    private JToggleButton travelTag;
    private JToggleButton familyTag;
    private JToggleButton artTag;
    private JToggleButton otherTag;

    public PiknikToolbar(PiknikWindow parentFrame, int width, int height)
    {
        super(parentFrame, width, height);
    }

    protected void setup(int width, int height)
    {
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(250, 100));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Color.lightGray));

        setupTags();
        setupNavigationButtons();
    }

    private void setupNavigationButtons()
    {
        JPanel navigationButtonPanel = new JPanel();
        navigationButtonPanel.setLayout(new FlowLayout());

        BufferedImage backButtonOriginalImage = null;
        BufferedImage forwardButtonOriginalImage = null;
        try {
            backButtonOriginalImage = ImageIO.read(new File(getClass().getResource("/resources/ico_back.png").toURI()));
            forwardButtonOriginalImage = ImageIO.read(new File(getClass().getResource("/resources/ico_forward.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image backButtonResizedImage = backButtonOriginalImage.getScaledInstance( 64, 40,  java.awt.Image.SCALE_SMOOTH ) ;
        Image forwardButtonResizedImage = forwardButtonOriginalImage.getScaledInstance( 64, 40,  java.awt.Image.SCALE_SMOOTH ) ;

        JButton backButton = new JButton();
        backButton.setIcon(new ImageIcon(backButtonResizedImage));
        backButton.setPreferredSize(new Dimension(96, 60));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.updateStatus("Previous Button Tapped");
            }
        });


        JButton forwardButton = new JButton();
        forwardButton.setIcon(new ImageIcon(forwardButtonResizedImage));
        forwardButton.setPreferredSize(new Dimension(96, 60));
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.updateStatus("Next Button Tapped");
            }
        });

        navigationButtonPanel.add(backButton);
        navigationButtonPanel.add(forwardButton);

        add(navigationButtonPanel, BorderLayout.SOUTH);
    }


    private void setupTags()
    {
        JPanel tagsPanel = new JPanel();
        tagsPanel.setLayout(new BorderLayout());

        JLabel tagsLabel = new JLabel("TAGS");
        tagsLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        Border paddingBorder = BorderFactory.createEmptyBorder(5,0,10,0);
        tagsLabel.setBorder(paddingBorder);
        tagsLabel.setHorizontalAlignment(JLabel.CENTER);
        tagsLabel.setVerticalAlignment(JLabel.CENTER);

        tagsPanel.add(tagsLabel, BorderLayout.NORTH);

        JPanel tagsButtonPanel = new JPanel();
        GridLayout experimentLayout = new GridLayout(2,2);
        tagsButtonPanel.setLayout(experimentLayout);

        travelTag = new JToggleButton("Travel");
        tagsButtonPanel.add(travelTag);

        familyTag = new JToggleButton("Family");
        tagsButtonPanel.add(familyTag);

        artTag = new JToggleButton("Art/Nature");
        tagsButtonPanel.add(artTag);

        otherTag = new JToggleButton("Other");
        tagsButtonPanel.add(otherTag);

        travelTag.addActionListener(new TagsActionListener(travelTag));
        familyTag.addActionListener(new TagsActionListener(familyTag));
        artTag.addActionListener(new TagsActionListener(artTag));
        otherTag.addActionListener(new TagsActionListener(otherTag));

        tagsPanel.add(tagsButtonPanel, BorderLayout.CENTER);

        add(tagsPanel, BorderLayout.NORTH);
    }

    private class TagsActionListener implements ActionListener
    {
        JToggleButton tagToggleButton;
        public TagsActionListener(JToggleButton toggle)
        {
            tagToggleButton = toggle;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(tagToggleButton.isSelected())
            {
                parentFrame.updateStatus(tagToggleButton.getText()+" Tag Selected");
            }
            else
            {
                parentFrame.updateStatus(tagToggleButton.getText()+" Tag Unselected");
            }
        }
    }
}