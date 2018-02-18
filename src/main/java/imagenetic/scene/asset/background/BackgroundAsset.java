package imagenetic.scene.asset.background;

import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.manager.CanvasManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.postprocessing.domain.EffectType.RADIAL_GRADIENT_EFFECT;

public class BackgroundAsset extends GuiAsset<BackgroundAssetArgument> {

    private final CanvasManager canvasManager;

    private Canvas backgroundCanvas;

    @Wire
    public BackgroundAsset(final AssetManager assetManager, final CanvasManager canvasManager) {
        super(assetManager);
        this.canvasManager = canvasManager;
    }

    @Override
    public void initialize() {
        backgroundCanvas = canvasManager.supply(this, "gray", RADIAL_GRADIENT_EFFECT);
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder
                .create()
                .loadCanvases(backgroundCanvas)
                .build();
    }
}
