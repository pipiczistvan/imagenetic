package imagenetic.scene.asset.ui;

import imagenetic.scene.asset.button.ButtonAsset;
import imagenetic.scene.asset.button.ButtonAssetArgument;
import imagenetic.scene.asset.fps.FpsAsset;
import imagenetic.scene.asset.fps.FpsAssetArgument;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import puppeteer.annotation.premade.Wire;

public class UiAsset extends GuiAsset<UiAssetArgument> {

    private ButtonAsset buttonAsset;
    private FpsAsset fpsAsset;

    @Wire
    public UiAsset(final AssetManager assetManager) {
        super(assetManager);
    }

    @Override
    public void initialize() {
        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault",
                "buttonHover",
                "buttonPress",
                arguments.viewport,
                "Please press me!",
                () -> System.out.println("Button clicked!")));
        buttonAsset.setPosition(0.7f, 0, 0);

        fpsAsset = createAsset(FpsAsset.class, new FpsAssetArgument());
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadAssets(buttonAsset)
                .loadAssets(fpsAsset)
                .build();
    }
}
