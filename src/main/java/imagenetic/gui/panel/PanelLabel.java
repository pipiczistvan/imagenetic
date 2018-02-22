package imagenetic.gui.panel;

import imagenetic.common.Config;

import javax.swing.*;
import java.awt.*;

public class PanelLabel extends JPanel {

    private JLabel lblGenerationCount;
    private JLabel lblAverageFitness;
    private JLabel lblBestFitness;
    private JLabel lblSpeed;

    public PanelLabel() {
        FlowLayout flowLayout = (FlowLayout) this.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);

        this.lblGenerationCount = new JLabel("");
        this.add(lblGenerationCount);

        this.lblAverageFitness = new JLabel("");
        this.add(lblAverageFitness);

        this.lblBestFitness = new JLabel("");
        this.add(lblBestFitness);

        lblSpeed = new JLabel("");
        this.add(lblSpeed);

        updateLabels(0, 0, 0, Config.DEF_SPEED);
    }

    public void updateLabels(final int numberOfGenerations, final float averageFitness, final float bestFitness, final int generationsPerSec) {
        lblGenerationCount.setText(String.format("Generációk száma: %s", numberOfGenerations));
        lblAverageFitness.setText(String.format("Átlagos fitness: %1.3f", averageFitness));
        lblBestFitness.setText(String.format("Legjobb fitness: %1.3f", bestFitness));
        lblSpeed.setText(String.format("Sebesség: %s generáció/mp", generationsPerSec));
    }
}
