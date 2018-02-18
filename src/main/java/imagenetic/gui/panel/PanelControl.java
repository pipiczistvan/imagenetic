package imagenetic.gui.panel;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;

public class PanelControl extends JPanel {

    private JLabel lblImage;

    public PanelControl() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // PANEL BUTTON

        JPanel panelButton = new JPanel();
        this.add(panelButton);
        panelButton.setLayout(new MigLayout("", "[172, grow]", "[23px][14px][45px]"));

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            boolean algorithmPaused = Bridge.sceneSide.isAlgorithmPaused();

            source.setText(algorithmPaused ? "Stop" : "Start");
            Bridge.sceneSide.setAlgorithmStatus(!algorithmPaused);
        });
        panelButton.add(btnStart, "flowy,cell 0 0,growx,aligny top");
        panelButton.add(btnStart, "cell 0 0,growx,aligny center");
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSpeed = new JLabel("Sebesség:");
        panelButton.add(lblSpeed, "cell 0 1,alignx left,aligny center");

        JSlider sliderSpeed = new JSlider();
        sliderSpeed.setMajorTickSpacing(1);
        sliderSpeed.setPaintLabels(true);
        sliderSpeed.setPaintTicks(true);
        sliderSpeed.setSnapToTicks(true);
        sliderSpeed.setValue(Config.DEF_SPEED);
        sliderSpeed.setMaximum(Config.MAX_SPEED);
        sliderSpeed.setMinimum(Config.MIN_SPEED);
        sliderSpeed.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            Bridge.sceneSide.setAlgorithmSpeed(source.getValue());
        });
        panelButton.add(sliderSpeed, "cell 0 2,alignx center,aligny center");

        // PANEL POPULATION

        JPanel panelPopulation = new JPanel();
        panelPopulation.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPopulation.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Populáció", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.add(panelPopulation);
        panelPopulation.setLayout(new MigLayout("", "[172px,grow]", "[21px][grow][21px]"));

        // population count

        JPanel panelPopulationCount = new JPanel();
        panelPopulationCount.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPopulation.add(panelPopulationCount, "cell 0 0,grow");
        panelPopulationCount.setLayout(new GridLayout(0, 3, 0, 0));

        JLabel lblPopulationCount = new JLabel("Számosság:");
        lblPopulationCount.setHorizontalAlignment(SwingConstants.LEFT);
        panelPopulationCount.add(lblPopulationCount);

        Component hstrutPopulationCount = Box.createHorizontalStrut(20);
        panelPopulationCount.add(hstrutPopulationCount);

        JSpinner spinnerPopulationCount = new JSpinner();
        spinnerPopulationCount.setModel(new SpinnerNumberModel(Config.DEF_POPULATION_COUNT, Config.MIN_POPULATION_COUNT, Config.MAX_POPULATION_COUNT, 1));
        spinnerPopulationCount.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            Bridge.sceneSide.setPopulationCount((Integer) spinner.getValue());
        });
        panelPopulationCount.add(spinnerPopulationCount);

        // population size

        JPanel panelPopulationSize = new JPanel();
        panelPopulationCount.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPopulation.add(panelPopulationSize, "cell 0 1,grow");
        panelPopulationSize.setLayout(new GridLayout(0, 3, 0, 0));

        JLabel lblPopulationSize = new JLabel("Méret:");
        lblPopulationSize.setHorizontalAlignment(SwingConstants.LEFT);
        panelPopulationSize.add(lblPopulationSize);

        Component hstrutPopulationSize = Box.createHorizontalStrut(20);
        panelPopulationSize.add(hstrutPopulationSize);

        JSpinner spinnerPopulationSize = new JSpinner();
        spinnerPopulationSize.setModel(new SpinnerNumberModel(Config.DEF_POPULATION_SIZE, Config.MIN_POPULATION_SIZE, Config.MAX_POPULATION_SIZE, 50));
        spinnerPopulationSize.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            Bridge.sceneSide.setPopulationSize((Integer) spinner.getValue());
        });
        panelPopulationSize.add(spinnerPopulationSize);

        // population visibility

        JPanel panelPopulationVisibility = new JPanel();
        panelPopulationVisibility.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPopulation.add(panelPopulationVisibility, "cell 0 2,grow");
        panelPopulationVisibility.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblPopulationVisibility = new JLabel("Összes mutatása:");
        panelPopulationVisibility.add(lblPopulationVisibility);

        JCheckBox chckbxPopulationVisibility = new JCheckBox("");
        chckbxPopulationVisibility.setHorizontalAlignment(SwingConstants.RIGHT);
        chckbxPopulationVisibility.addItemListener(e -> Bridge.sceneSide.showAll(e.getStateChange() == ItemEvent.SELECTED));
        panelPopulationVisibility.add(chckbxPopulationVisibility);

        // PANEL ENTITY

        JPanel panelEntity = new JPanel();
        panelEntity.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Egyed", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.add(panelEntity);
        panelEntity.setLayout(new MigLayout("", "[172px]", "[26px][26px][20px]"));

        // entity length

        JPanel panelEntityLength = new JPanel();
        panelEntity.add(panelEntityLength, "cell 0 0,alignx center,aligny center");
        panelEntityLength.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblEntityLength = new JLabel("Hosszúság:");
        lblEntityLength.setHorizontalAlignment(SwingConstants.LEFT);
        panelEntityLength.add(lblEntityLength);

        JSlider sliderEntityLength = new JSlider();
        sliderEntityLength.setMajorTickSpacing(1);
        sliderEntityLength.setSnapToTicks(true);
        sliderEntityLength.setValue(Config.DEF_ENTITY_LENGTH);
        sliderEntityLength.setMaximum(Config.MAX_ENTITY_LENGTH);
        sliderEntityLength.setMinimum(Config.MIN_ENTITY_LENGTH);
        sliderEntityLength.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            Bridge.sceneSide.setLength(source.getValue());
        });
        panelEntityLength.add(sliderEntityLength);

        // entity thickness

        JPanel panelEntityThickness = new JPanel();
        panelEntity.add(panelEntityThickness, "cell 0 1,alignx center,aligny center");
        panelEntityThickness.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblEntityThickness = new JLabel("Vastagság:");
        lblEntityThickness.setHorizontalAlignment(SwingConstants.LEFT);
        panelEntityThickness.add(lblEntityThickness);

        JSlider sliderEntityThickness = new JSlider();
        sliderEntityThickness.setMajorTickSpacing(1);
        sliderEntityThickness.setSnapToTicks(true);
        sliderEntityThickness.setValue(Config.DEF_ENTITY_THICKNESS);
        sliderEntityThickness.setMaximum(Config.MAX_ENTITY_THICKNESS);
        sliderEntityThickness.setMinimum(Config.MIN_ENTITY_THICKNESS);
        sliderEntityThickness.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            Bridge.sceneSide.setThickness(source.getValue());
        });
        panelEntityThickness.add(sliderEntityThickness);

        // entity threshold

        JPanel panelEntityThreshold = new JPanel();
        panelEntity.add(panelEntityThreshold, "cell 0 2,alignx center,aligny center");
        panelEntityThreshold.setLayout(new GridLayout(0, 3, 0, 0));

        JLabel lblEntityThreshold = new JLabel("Fitness határ:");
        panelEntityThreshold.add(lblEntityThreshold);

        Component hstrutEntityThreshold = Box.createHorizontalStrut(20);
        panelEntityThreshold.add(hstrutEntityThreshold);

        JSpinner spinnerEntityThreshold = new JSpinner();
        spinnerEntityThreshold.setModel(new SpinnerNumberModel(Config.DEF_ENTITY_THRESHOLD, Config.MIN_ENTITY_THRESHOLD, Config.MAX_ENTITY_THRESHOLD, 0.1f));
        spinnerEntityThreshold.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            Bridge.sceneSide.setThreshold((float)(double) spinner.getValue());
        });
        panelEntityThreshold.add(spinnerEntityThreshold);

        // PANEL IMAGE

        JPanel panelImage = new JPanel();
        panelImage.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(panelImage);
        panelImage.setLayout(new GridLayout(0, 1, 0, 0));

        lblImage = new JLabel("");
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        panelImage.add(lblImage);

        JPanel panelBottom = new JPanel();
        this.add(panelBottom);
        panelBottom.setLayout(new BorderLayout(0, 0));
    }

    public void updateImage(final BufferedImage image) {
        int newImageWidth;
        int newImageHeight;

        BufferedImage originalImage = copyImage(image);
        if (originalImage.getWidth() > originalImage.getHeight()) {
            newImageWidth = lblImage.getWidth();
            newImageHeight = (int) (newImageWidth / (float) originalImage.getWidth() * originalImage.getHeight());
        } else {
            newImageHeight = lblImage.getWidth();
            newImageWidth = (int) (newImageHeight / (float) originalImage.getHeight() * originalImage.getWidth());
        }

        Image scaledImage = originalImage.getScaledInstance(newImageWidth, newImageHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        lblImage.setIcon(imageIcon);

        Bridge.sceneSide.setImage(image);
    }

    private static BufferedImage copyImage(final BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
