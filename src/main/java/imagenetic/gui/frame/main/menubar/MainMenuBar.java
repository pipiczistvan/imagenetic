package imagenetic.gui.frame.main.menubar;

import imagenetic.gui.common.ImageSelectionListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.image.BufferedImage;

@Component
public class MainMenuBar extends JMenuBar implements ImageSelectionListener {

    private final JMenu mnFile;
    private final JMenuItem mntmSelect;

    private final JMenu mnControl;
    private final JMenuItem mntmPlay;
    private final JMenuItem mntmSlower;
    private final JMenuItem mntmFaster;
    private final JMenuItem mntmStop;
    private final JMenuItem mntmSync;

    private final JMenu mnView;
    private final JMenuItem mntmZoomReset;
    private final JMenuItem mntmNormalView;
    private final JMenuItem mntmSideView;

    public MainMenuBar() {
        // FILE
        mnFile = new JMenu("Fájl");
        this.add(mnFile);

        mntmSelect = new JMenuItem("Kép kiválasztása...");
        mnFile.add(mntmSelect);

        // CONTROL
        mnControl = new JMenu("Vezérlés");
        this.add(mnControl);

        mntmPlay = new JMenuItem("Indítás");
        mnControl.add(mntmPlay);

        mntmSlower = new JMenuItem("Lassítás");
        mnControl.add(mntmSlower);

        mntmFaster = new JMenuItem("Gyorsítás");
        mnControl.add(mntmFaster);

        mntmStop = new JMenuItem("Leállítás");
        mnControl.add(mntmStop);

        mntmSync = new JMenuItem("Folyamatos szinkronizáció");
        mnControl.add(mntmSync);

        // VIEW
        mnView = new JMenu("Nézet");
        this.add(mnView);

        mntmZoomReset = new JMenuItem("Közelítés visszaállítása");
        mnView.add(mntmZoomReset);

        mntmNormalView = new JMenuItem("Normál nézet");
        mnView.add(mntmNormalView);

        mntmSideView = new JMenuItem("Oldal nézet");
        mnView.add(mntmSideView);
    }

    @Override
    public void onImageSelect(final BufferedImage image) {
        System.out.println("Image selected");
    }
}
