package imagenetic.scene.asset.ui;

import imagenetic.common.control.button.ButtonAsset;
import imagenetic.common.control.button.ButtonAssetArgument;
import imagenetic.common.control.selector.SelectorAsset;
import imagenetic.common.control.selector.SelectorAssetArgument;
import imagenetic.scene.asset.fps.FpsAsset;
import imagenetic.scene.asset.fps.FpsAssetArgument;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import puppeteer.annotation.premade.Wire;

import java.io.File;

public class UiAsset extends GuiAsset<UiAssetArgument> {

    private ButtonAsset buttonAsset;
    private SelectorAsset selectorAsset;
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
                this::onClick));
        buttonAsset.setPosition(0.7f, 0, 0);

        selectorAsset = createAsset(SelectorAsset.class, new SelectorAssetArgument(
                this,
                "buttonDefault",
                "buttonHover",
                "buttonPress",
                arguments.viewport,
                "Select file!",
                this::onSelected
        ));
        selectorAsset.setPosition(0.7f, 0.25f, 0);

        fpsAsset = createAsset(FpsAsset.class, new FpsAssetArgument());
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadAssets(buttonAsset)
                .loadAssets(selectorAsset)
                .loadAssets(fpsAsset)
                .build();
    }

    private void onClick() {
        System.out.println("Button clicked");
    }

    private void onSelected(final File[] files) {
        System.out.println("Selected files: ");
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
