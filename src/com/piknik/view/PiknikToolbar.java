package com.piknik.view;

import com.piknik.global.PhotoControlSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;

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

    /**
     * Implementation of abstract setup methods. This will set the preferred size, set background color, border, and
     * call helper setup methods.
     *
     * @param width the preferred width of the current panel
     * @param height the preferred height of the current panel
     */
    protected void setup(int width, int height)
    {
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);

        Color borderColor = new Color(0XE6E6E6);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, borderColor));

        setupTags();
        setupNavigationButtons();
        setupPhotoControls();
    }

    /**
     * Helper setup method to create the previous/next navigation buttons. Navigation Buttons are setup inside of a
     * JPanel with a FlowLayout. This JPanel is added to the PiknikToolbar Panel at the location BorderLayout.SOUTH.
     */
    private void setupNavigationButtons()
    {
        JPanel navigationButtonPanel = new JPanel();
        navigationButtonPanel.setLayout(new FlowLayout());

        BufferedImage backButtonOriginalImage = null;
        BufferedImage forwardButtonOriginalImage = null;
        try {
            InputStream backButtonURL = getClass().getClassLoader().getResourceAsStream("resources/ico_back.png");
            InputStream forwardButtonURL = getClass().getClassLoader().getResourceAsStream("resources/ico_forward.png");
            backButtonOriginalImage = ImageIO.read(backButtonURL);
            forwardButtonOriginalImage = ImageIO.read(forwardButtonURL);
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

    public static java.net.URL getFileAsURL(javax.swing.JComponent parent , String path)
    {
        java.net.URLClassLoader urlLoader = (java.net.URLClassLoader) parent.getClass().getClassLoader();
        return urlLoader.findResource(path);
    }

    /**
     * Helper setup method to create the tags (e.g. Travel, Work, etc). The layout for this screen is complex.
     * It starts off with an overall JPanel with a BorderLayout. This JPanel will contain a JLabel with the text "TAGS"
     * at the location BorderLayout.NORTH. At BorderLayout.CENTER, another JPanel will be created and added to that
     * area. This sub JPanel will have a GridLayout and display the tags  through JToggleButtons.
     */
    private void setupTags()
    {
        JPanel tagsPanel = new JPanel();
        tagsPanel.setLayout(new BorderLayout());

        JLabel tagsLabel = new JLabel("Tags");
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

    private void setupPhotoControls()
    {
        JPanel photoControls = new JPanel();
        photoControls.setLayout(new BorderLayout());

        //photoControls.add(controlsLabel, BorderLayout.NORTH);

        JPanel specificControlsPanel = new JPanel();
        GridLayout experimentLayout = new GridLayout(3,2);
        specificControlsPanel.setLayout(experimentLayout);

        //create photo controls label. somewhat of a hack to fit this with GridLayouts.
        //an alternative would be to use gridbag layouts
        Border paddingBorder = BorderFactory.createEmptyBorder(5,0,10,0);

        JLabel controlsLabel1 = new JLabel("Photo ");
        controlsLabel1.setFont(new Font("Serif", Font.PLAIN, 18));
        controlsLabel1.setBorder(paddingBorder);
        controlsLabel1.setHorizontalAlignment(JLabel.RIGHT);
        controlsLabel1.setVerticalAlignment(JLabel.CENTER);

        JLabel controlsLabel2 = new JLabel("Controls");
        controlsLabel2.setFont(new Font("Serif", Font.PLAIN, 18));
        controlsLabel2.setBorder(paddingBorder);
        controlsLabel2.setHorizontalAlignment(JLabel.LEFT);
        controlsLabel2.setVerticalAlignment(JLabel.CENTER);

        specificControlsPanel.add(controlsLabel1);
        specificControlsPanel.add(controlsLabel2);

        //add buttons and labels for line color and text color
        Border buttonPaddingBorder = BorderFactory.createEmptyBorder(0,5,0,0);

        //create line color label
        JLabel lineColorLabel = new JLabel("Line Color: ");
        lineColorLabel.setHorizontalAlignment(JLabel.CENTER);
        specificControlsPanel.add(lineColorLabel);

        //create line color button
        final JButton lineColorButton = new JButton(getHex(PhotoControlSettings.lineAnnotationColor));
        lineColorButton.setBorder(buttonPaddingBorder);

        //set line color button background and its properties
        lineColorButton.setBackground(PhotoControlSettings.lineAnnotationColor);
        lineColorButton.setContentAreaFilled(false);
        lineColorButton.setOpaque(true);

        lineColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(PiknikToolbar.this, "Pick a Color", PhotoControlSettings.lineAnnotationColor);
                PhotoControlSettings.lineAnnotationColor = selectedColor;
                lineColorButton.setText(getHex(selectedColor));
                lineColorButton.setBackground(selectedColor);
            }
        });
        specificControlsPanel.add(lineColorButton);

        //create text color label
        JLabel textColorLabel = new JLabel("Text Color: ");
        textColorLabel.setHorizontalAlignment(JLabel.CENTER);
        specificControlsPanel.add(textColorLabel);

        //create text color button
        final JButton textColorButton = new JButton(getHex(PhotoControlSettings.textAnnotationColor));
        textColorButton.setBorder(buttonPaddingBorder);

        //set text color button background
        textColorButton.setBackground(PhotoControlSettings.textAnnotationColor);
        textColorButton.setContentAreaFilled(false);
        textColorButton.setOpaque(true);

        textColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(PiknikToolbar.this, "Pick a Color", PhotoControlSettings.textAnnotationColor);
                PhotoControlSettings.textAnnotationColor = selectedColor;
                textColorButton.setText(getHex(selectedColor));

                textColorButton.setBackground(selectedColor);
                textColorButton.setContentAreaFilled(false);
                textColorButton.setOpaque(true);
            }
        });
        specificControlsPanel.add(textColorButton);

        photoControls.add(specificControlsPanel, BorderLayout.NORTH);

        JScrollPane photoControlsScrollPane = new JScrollPane(photoControls);
        add(photoControlsScrollPane, BorderLayout.CENTER);
    }

    /**
     * Generic Tags Action Listener. The JToggleButton will need to be passed in to determine which button was pressed.
     */
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

    private String getHex(Color color)
    {
        return "#" + String.format("%06x", color.getRGB() & 0x00FFFFFF);
    }

}
