package imagenetic.gui.frame.main.menubar;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static imagenetic.gui.common.api.ViewChangedListener.VIEW_TYPE.FRONT;
import static imagenetic.gui.common.api.ViewChangedListener.VIEW_TYPE.NONE;
import static imagenetic.gui.common.api.ViewChangedListener.VIEW_TYPE.SIDE;

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
    private final JMenuItem mntmFrontView;
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
        mntmZoomReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        mntmZoomReset.addActionListener(this::onZoomResetClick);
        mnView.add(mntmZoomReset);

        mntmFrontView = new JMenuItem("Normal nézet");
        mntmFrontView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0));
        mntmFrontView.addActionListener(this::onFrontViewClick);
        mnView.add(mntmFrontView);

        mntmSideView = new JMenuItem("Oldal nézet");
        mntmSideView.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
        mntmSideView.addActionListener(this::onSideViewClick);
        mnView.add(mntmSideView);
    }

    @Override
    public void onImageSelect(final BufferedImage image) {
        System.out.println("Image selected");
    }

    private void onZoomResetClick(ActionEvent e) {
        Bridge.LISTENER_CONTAINER.viewChangedListeners.forEach(l ->
                l.viewChanged(NONE, true));
    }

    private void onFrontViewClick(ActionEvent e) {
        Bridge.LISTENER_CONTAINER.viewChangedListeners.forEach(l ->
                l.viewChanged(FRONT, false));
    }

    private void onSideViewClick(ActionEvent e) {
        Bridge.LISTENER_CONTAINER.viewChangedListeners.forEach(l ->
                l.viewChanged(SIDE, false));
    }
}
