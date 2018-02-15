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

        JPanel panelButton = new JPanel();
        this.add(panelButton);
        panelButton.setLayout(new MigLayout("", "[184px]", "[23px]"));

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            boolean algorithmPaused = Bridge.sceneSide.isAlgorithmPaused();

            source.setText(algorithmPaused ? "Stop" : "Start");
            Bridge.sceneSide.setAlgorithmStatus(!algorithmPaused);
        });
        panelButton.add(btnStart, "flowy,cell 0 0,growx,aligny top");
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSpeed = new JLabel("Sebesség:");
        panelButton.add(lblSpeed, "cell 0 0");

        JSlider sliderSpeed = new JSlider();
        sliderSpeed.setMajorTickSpacing(1);
        sliderSpeed.setPaintLabels(true);
        sliderSpeed.setPaintTicks(true);
        sliderSpeed.setSnapToTicks(true);
        sliderSpeed.setValue(Config.MIN_SPEED);
        sliderSpeed.setMaximum(Config.MAX_SPEED);
        sliderSpeed.setMinimum(Config.MIN_SPEED);
        sliderSpeed.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            Bridge.sceneSide.setAlgorithmSpeed(source.getValue());
        });
        panelButton.add(sliderSpeed, "cell 0 0");

        JPanel panelPopulation = new JPanel();
        panelPopulation.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPopulation.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Populáció", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.add(panelPopulation);
        panelPopulation.setLayout(new MigLayout("", "[172px]", "[21px][21px]"));

        JPanel panelPopulationCount = new JPanel();
        panelPopulationCount.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPopulation.add(panelPopulationCount, "cell 0 0,grow");
        panelPopulationCount.setLayout(new GridLayout(0, 3, 0, 0));

        JLabel lblPopulationCount = new JLabel("Méret:");
        lblPopulationCount.setHorizontalAlignment(SwingConstants.LEFT);
        panelPopulationCount.add(lblPopulationCount);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        panelPopulationCount.add(horizontalStrut);

        JSpinner spinnerPopulationCount = new JSpinner();
        spinnerPopulationCount.setModel(new SpinnerNumberModel(Config.MIN_POPULATION_COUNT, Config.MIN_POPULATION_COUNT, Config.MAX_POPULATION_COUNT, 1));
        spinnerPopulationCount.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            Bridge.sceneSide.setPopulationCount((Integer) spinner.getValue());
        });
        panelPopulationCount.add(spinnerPopulationCount);

        JPanel panelPopulationVisibility = new JPanel();
        panelPopulationVisibility.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPopulation.add(panelPopulationVisibility, "cell 0 1,grow");
        panelPopulationVisibility.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblPopulationVisibility = new JLabel("Összes mutatása:");
        panelPopulationVisibility.add(lblPopulationVisibility);

        JCheckBox chckbxPopulationVisibility = new JCheckBox("");
        chckbxPopulationVisibility.setHorizontalAlignment(SwingConstants.RIGHT);
        chckbxPopulationVisibility.addItemListener(e -> Bridge.sceneSide.showAll(e.getStateChange() == ItemEvent.SELECTED));
        panelPopulationVisibility.add(chckbxPopulationVisibility);

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
