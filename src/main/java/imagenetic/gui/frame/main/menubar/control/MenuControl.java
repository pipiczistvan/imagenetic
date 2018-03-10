package imagenetic.gui.frame.main.menubar.control;

import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;

@Component
public class MenuControl extends JMenu {

    @Wire
    public MenuControl(final MenuItemPlay menuItemPlay, final MenuItemSlower menuItemSlower, final MenuItemFaster menuItemFaster,
                       final MenuItemStop menuItemStop, final MenuItemSync menuItemSync) {
        super("Vezérlés");
        this.add(menuItemPlay);
        this.add(menuItemSlower);
        this.add(menuItemFaster);
        this.add(menuItemStop);
        this.add(menuItemSync);
    }
}
