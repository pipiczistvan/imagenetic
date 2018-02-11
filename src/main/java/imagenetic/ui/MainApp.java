package imagenetic.ui;

import imagenetic.common.Bridge;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainApp {
    public JPanel panel_main;
    public JPanel panel_opengl;
    private JPanel panel_labels;
    private JPanel panel_control;
    private JButton button_pause;
    private JPanel panel_buttons;
    private JSlider slider_speed;
    private JLabel label_populationCount;
    private JLabel label_generationCount;
    private JLabel label_averageFitness;
    private JLabel label_bestFitness;
    private JLabel label_populationCount_value;
    private JLabel label_generationCount_value;
    private JLabel label_averageFitness_value;
    private JLabel label_bestFitness_value;
    private JLabel label_image;
    private JPanel panel_image;

    public MainApp() {
        button_pause.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            source.setText(Bridge.mainScene.getStickAsset().paused ? "Stop" : "Start");
            Bridge.mainScene.getStickAsset().paused = !Bridge.mainScene.getStickAsset().paused;
        });
        slider_speed.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            Bridge.mainScene.getStickAsset().speed = source.getValue();
        });
    }

    public void updateLabels() {
        label_populationCount_value.setText(String.format("%s", Bridge.mainScene.getGeneticAlgorithm().getNumberOfPopulations()));
        label_generationCount_value.setText(String.format("%s", Bridge.mainScene.getGeneticAlgorithm().getNumberOfGenerations()));
        label_averageFitness_value.setText(String.format("%1.3f", Bridge.mainScene.getGeneticAlgorithm().getAverageFitness()));
        label_bestFitness_value.setText(String.format("%1.3f", Bridge.mainScene.getGeneticAlgorithm().getBestFitness()));
    }

    public void updateImage(final BufferedImage image) {
        int newImageWidth;
        int newImageHeight;

        if (image.getWidth() > image.getHeight()) {
            newImageWidth = label_image.getWidth();
            newImageHeight = (int) (newImageWidth / (float) image.getWidth() * image.getHeight());
        } else {
            newImageHeight = label_image.getHeight();
            newImageWidth = (int) (newImageHeight / (float) image.getHeight() * image.getWidth());
        }

        Image scaledImage = image.getScaledInstance(newImageWidth, newImageHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        label_image.setIcon(imageIcon);
    }
}
