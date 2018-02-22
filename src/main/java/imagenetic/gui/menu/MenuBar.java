package imagenetic.gui.menu;

import imagenetic.gui.common.ImageChooser;
import imagenetic.gui.panel.PanelControl;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(final PanelControl panelControl) {
        JMenu mnFile = new JMenu("Fájl");
        this.add(mnFile);

        ImageChooser imageChooser = new ImageChooser(this);

        JMenuItem mntmSelect = new JMenuItem("Kép kiválasztása...");
        mntmSelect.setMnemonic('K');
        mntmSelect.addActionListener(e -> imageChooser.choose(panelControl::updateImage));
        mnFile.add(mntmSelect);
    }

}
