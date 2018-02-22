package imagenetic.gui.panel;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import imagenetic.gui.common.ImageChooser;
import net.miginfocom.swing.MigLayout;
import piengine.core.base.resource.ResourceLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

public class PanelControl extends JPanel {

    private final ResourceLoader imageLoader = new ResourceLoader(get(IMAGES_LOCATION), "png");
    private final ImageIcon backwardButtonIcon;
    private final ImageIcon forwardButtonIcon;
    private final ImageIcon pauseButtonIcon;
    private final ImageIcon playButtonIcon;
    private final ImageIcon stopButtonIcon;

    private JButton btnPlay;
    private JButton btnReset;
    private JSpinner spinnerPopulationCount;
    private JSpinner spinnerPopulationSize;
    private JSlider sliderEntityLength;
    private JSlider sliderEntityThickness;
    private JLabel lblImage;

    private boolean paused = false;
    private boolean reseted = true;
    private boolean imageLoaded = false;
    private int speed = Config.DEF_SPEED;

    public PanelControl() {
        backwardButtonIcon = new ImageIcon(imageLoader.getUrl("backward-button"));
        forwardButtonIcon = new ImageIcon(imageLoader.getUrl("forward-button"));
        pauseButtonIcon = new ImageIcon(imageLoader.getUrl("pause-button"));
        playButtonIcon = new ImageIcon(imageLoader.getUrl("play-button"));
        stopButtonIcon = new ImageIcon(imageLoader.getUrl("stop-button"));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // PANEL BUTTON

        JPanel panelButton = new JPanel();
        this.add(panelButton);
        panelButton.setLayout(new MigLayout("", "[200, grow]", "[23px][14px]"));

        JPanel panelPlay = new JPanel();
        panelButton.add(panelPlay, "cell 0 0");
        panelPlay.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnSlower = new JButton("");
        btnSlower.setIcon(backwardButtonIcon);
        panelPlay.add(btnSlower);
        btnSlower.addActionListener(e -> {
            if (speed > Config.MIN_SPEED) {
                speed--;
            }

            Bridge.sceneSide.setAlgorithmSpeed(speed);
        });

        btnPlay = new JButton("");
        btnPlay.setIcon(playButtonIcon);
        panelPlay.add(btnPlay);
        btnPlay.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            paused = Bridge.sceneSide.isAlgorithmPaused();
            reseted = false;
            updatePermissions();

            source.setIcon(paused ? pauseButtonIcon : playButtonIcon);
            Bridge.sceneSide.setAlgorithmStatus(!paused);
        });
        btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnFaster = new JButton("");
        btnFaster.setIcon(forwardButtonIcon);
        panelPlay.add(btnFaster);
        btnFaster.addActionListener(e -> {
            if (speed < Config.MAX_SPEED) {
                speed++;
            }

            Bridge.sceneSide.setAlgorithmSpeed(speed);
        });

        btnReset = new JButton("");
        btnReset.setIcon(stopButtonIcon);
        panelPlay.add(btnReset);
        btnReset.addActionListener(e -> {
            reseted = true;
            paused = true;
            btnPlay.setIcon(playButtonIcon);
            updatePermissions();

            Bridge.sceneSide.setAlgorithmStatus(paused);
            Bridge.sceneSide.reset();
        });

        JPanel panelInterpolation = new JPanel();
        panelButton.add(panelInterpolation, "cell 0 1,grow");
        panelInterpolation.setLayout(new GridLayout(0, 1, 0, 0));

        JButton btnInterpolation = new JButton("Diszkrét");
        panelInterpolation.add(btnInterpolation);
        btnInterpolation.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            boolean interpolated = Bridge.sceneSide.isInterpolated();

            source.setText(!interpolated ? "Folytonos" : "Diszkrét");
            Bridge.sceneSide.setInterpolated(!interpolated);
        });

        // PANEL POPULATION

        JPanel panelPopulation = new JPanel();
        panelPopulation.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPopulation.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Populáció", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.add(panelPopulation);
        panelPopulation.setLayout(new MigLayout("", "[200px,grow]", "[21px][grow][21px]"));

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

        spinnerPopulationCount = new JSpinner();
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

        spinnerPopulationSize = new JSpinner();
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

        JCheckBox chckbxPopulationVisibility = new JCheckBox("", Config.DEF_SHOW_ALL);
        chckbxPopulationVisibility.setHorizontalAlignment(SwingConstants.RIGHT);
        chckbxPopulationVisibility.addItemListener(e -> Bridge.sceneSide.showAll(e.getStateChange() == ItemEvent.SELECTED));
        panelPopulationVisibility.add(chckbxPopulationVisibility);

        // PANEL ENTITY

        JPanel panelEntity = new JPanel();
        panelEntity.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Egyed", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.add(panelEntity);
        panelEntity.setLayout(new MigLayout("", "[200px]", "[26px][26px][20px]"));

        // entity length

        JPanel panelEntityLength = new JPanel();
        panelEntity.add(panelEntityLength, "cell 0 0,alignx center,aligny center");
        panelEntityLength.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblEntityLength = new JLabel("Hosszúság:");
        lblEntityLength.setHorizontalAlignment(SwingConstants.LEFT);
        panelEntityLength.add(lblEntityLength);

        sliderEntityLength = new JSlider();
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

        sliderEntityThickness = new JSlider();
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
        spinnerEntityThreshold.setModel(new SpinnerNumberModel(Config.DEF_ENTITY_THRESHOLD, Config.MIN_ENTITY_THRESHOLD, Config.MAX_ENTITY_THRESHOLD, 0.1d));
        spinnerEntityThreshold.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            Bridge.sceneSide.setThreshold((double) spinner.getValue());
        });
        panelEntityThreshold.add(spinnerEntityThreshold);

        // PANEL IMAGE

        JPanel panelImage = new JPanel();
        panelImage.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(panelImage);
        panelImage.setLayout(new MigLayout("", "[200px,grow]", "[200px,grow]"));

        lblImage = new JLabel("Kép kiválasztása...");
        lblImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblImage.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 4, 3, true));
        lblImage.setPreferredSize(new Dimension(200, 200));
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        ImageChooser imageChooser = new ImageChooser(lblImage);
        PanelControl panelControl = this;
        lblImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imageChooser.choose(panelControl::updateImage);
            }
        });
        panelImage.add(lblImage, "cell 0 0,grow");

        JPanel panelBottom = new JPanel();
        this.add(panelBottom);
        panelBottom.setLayout(new BorderLayout(0, 0));

        // PERMISSIONS
        updatePermissions();
    }

    public void updateImage(final BufferedImage image) {
        int newImageWidth;
        int newImageHeight;

        BufferedImage originalImage = copyImage(image);
        if (originalImage.getWidth() > originalImage.getHeight()) {
            newImageWidth = lblImage.getHeight();
            newImageHeight = (int) (newImageWidth / (float) originalImage.getWidth() * originalImage.getHeight());
        } else {
            newImageHeight = lblImage.getHeight();
            newImageWidth = (int) (newImageHeight / (float) originalImage.getHeight() * originalImage.getWidth());
        }

        Image scaledImage = originalImage.getScaledInstance(newImageWidth, newImageHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        lblImage.setIcon(imageIcon);
        lblImage.setText("");

        imageLoaded = true;
        updatePermissions();

        Bridge.sceneSide.setImage(image);
    }

    private void updatePermissions() {
        btnPlay.setEnabled(imageLoaded);
        btnReset.setEnabled(!reseted);

        spinnerPopulationCount.setEnabled(reseted);
        spinnerPopulationSize.setEnabled(reseted);
        sliderEntityLength.setEnabled(reseted);
        sliderEntityThickness.setEnabled(reseted);
    }

    private static BufferedImage copyImage(final BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
