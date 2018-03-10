package imagenetic.gui.frame.main.menubar;

import imagenetic.gui.frame.main.menubar.control.MenuControl;
import imagenetic.gui.frame.main.menubar.file.MenuFile;
import imagenetic.gui.frame.main.menubar.view.MenuView;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;

@Component
public class MainMenuBar extends JMenuBar {

    @Wire
    public MainMenuBar(final MenuFile menuFile, final MenuControl menuControl, final MenuView menuView) {
        this.add(menuFile);
        this.add(menuControl);
        this.add(menuView);
    }
}
