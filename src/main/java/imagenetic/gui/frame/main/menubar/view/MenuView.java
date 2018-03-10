package imagenetic.gui.frame.main.menubar.view;

import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;

@Component
public class MenuView extends JMenu {

    @Wire
    public MenuView(final MenuItemZoomReset menuItemZoomReset, final MenuItemFrontView menuItemFrontView, final MenuItemSideView menuItemSideView) {
        super("Nézet");
        this.add(menuItemZoomReset);
        this.add(menuItemFrontView);
        this.add(menuItemSideView);
    }
}
