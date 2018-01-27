package imagenetic.scene.asset.ui;

import imagenetic.scene.asset.fps.FpsAsset;
import imagenetic.scene.asset.fps.FpsAssetArgument;
import piengine.gui.asset.ButtonAsset;
import piengine.gui.asset.ButtonAssetArgument;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import puppeteer.annotation.premade.Wire;

public class UiAsset extends GuiAsset<UiAssetArgument> {

    private ButtonAsset buttonAsset;
    private FpsAsset fpsAsset;

    private final AssetManager assetManager;

    @Wire
    public UiAsset(final AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void initialize() {
        buttonAsset = assetManager.supply(ButtonAsset.class, this, new ButtonAssetArgument(
                "buttonDefault", "buttonHover", "buttonPress",
                arguments.viewport, "Please press me!", () -> System.out.println("Button clicked!")));
        fpsAsset = assetManager.supply(FpsAsset.class, this, new FpsAssetArgument());
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return fpsAsset.getAssetContext();
    }
}
