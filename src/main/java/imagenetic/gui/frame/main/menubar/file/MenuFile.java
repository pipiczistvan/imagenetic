package imagenetic.gui.frame.main.menubar.file;

import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;

@Component
public class MenuFile extends JMenu {

    @Wire
    public MenuFile(final MenuItemSelect menuItemSelect) {
        super("Fájl");
        this.add(menuItemSelect);
    }
}
