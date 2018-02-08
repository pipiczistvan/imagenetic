package imagenetic.scene.asset.ui;

import imagenetic.common.control.button.ButtonAsset;
import imagenetic.common.control.icon.IconAsset;
import imagenetic.common.control.icon.IconAssetArgument;
import imagenetic.common.control.label.LabelAsset;
import imagenetic.common.control.label.LabelAssetArgument;
import imagenetic.common.control.selector.SelectorAsset;
import imagenetic.common.control.selector.SelectorAssetArgument;
import piengine.core.base.api.Resizable;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import puppeteer.annotation.premade.Wire;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UiAsset extends GuiAsset<UiAssetArgument> implements Resizable {

    private static final float MARGIN = 0.05f;

    private SelectorAsset strImage;
    private IconAsset icnStart;
    private IconAsset icnPause;
    private IconAsset icnRestart;
    private IconAsset icnReset;
    private IconAsset icnPlus;
    private IconAsset icnMinus;

    private LabelAsset lblNumberOfGenerations;
    private LabelAsset lblNumberOfPopulations;
    private LabelAsset lblBestFitness;
    private LabelAsset lblAverageFitness;

    @Wire
    public UiAsset(final AssetManager assetManager) {
        super(assetManager);
    }

    @Override
    public void initialize() {
        strImage = createAsset(SelectorAsset.class, new SelectorAssetArgument(this, arguments.viewport, "Select file!", this::onSelected));
        icnStart = createAsset(IconAsset.class, new IconAssetArgument(arguments.viewport, "start", this::onStartClick));
        icnPause = createAsset(IconAsset.class, new IconAssetArgument(arguments.viewport, "pause", this::onPauseClick));
        icnRestart = createAsset(IconAsset.class, new IconAssetArgument(arguments.viewport, "restart", this::onRestartClick));
        icnReset = createAsset(IconAsset.class, new IconAssetArgument(arguments.viewport, "reset", this::onResetClick));
        icnPlus = createAsset(IconAsset.class, new IconAssetArgument(arguments.viewport, "up", this::onPlusClick));
        icnMinus = createAsset(IconAsset.class, new IconAssetArgument(arguments.viewport, "down", this::onMinusClick));

        lblNumberOfGenerations = createAsset(LabelAsset.class, new LabelAssetArgument("Generaciok szama: ", arguments.viewport, 0.35f));
        lblNumberOfPopulations = createAsset(LabelAsset.class, new LabelAssetArgument("Populaciok szama: ", arguments.viewport, 0.3f));
        lblAverageFitness = createAsset(LabelAsset.class, new LabelAssetArgument("Atlag fitness: ", arguments.viewport, 0.3f));
        lblBestFitness = createAsset(LabelAsset.class, new LabelAssetArgument("Legjobb fitness: ", arguments.viewport, 0.3f));

        setTransformations(arguments.viewport.x, arguments.viewport.y, arguments.viewport.x, arguments.viewport.y);
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadAssets(strImage)
                .loadAssets(icnStart)
                .loadAssets(icnPause)
                .loadAssets(icnRestart)
                .loadAssets(icnReset)
                .loadAssets(icnPlus)
                .loadAssets(icnMinus)
                .loadAssets(lblNumberOfGenerations)
                .loadAssets(lblNumberOfPopulations)
                .loadAssets(lblAverageFitness)
                .loadAssets(lblBestFitness)
                .build();
    }

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        setTransformations(oldWidth, oldHeight, width, height);
    }

    public void updateLabels() {
        lblNumberOfGenerations.setText(String.format("Generaciok szama: %s", arguments.geneticAlgorithm.getNumberOfGenerations()));
        lblNumberOfPopulations.setText(String.format("Populaciok szama: %s", arguments.geneticAlgorithm.getNumberOfPopulations()));
        lblAverageFitness.setText(String.format("Atlag fitness: %1.3f", arguments.geneticAlgorithm.getAverageFitness()));
        lblBestFitness.setText(String.format("Legjobb fitness: %1.3f", arguments.geneticAlgorithm.getBestFitness()));
    }

    private void setTransformations(final int oldWidth, final int oldHeight, final int width, final int height) {
        float scaleX = (float) width / (float) oldWidth;
        float scaleY = (float) height / (float) oldHeight;

        transformLabels(scaleY, scaleX);
        transformButtons(scaleY, scaleX);
    }

    private void transformLabels(float scaleX, float scaleY) {
        lblNumberOfGenerations.scale(scaleX, scaleY, 1);
        lblNumberOfPopulations.scale(scaleX, scaleY, 1);
        lblAverageFitness.scale(scaleX, scaleY, 1);
        lblBestFitness.scale(scaleX, scaleY, 1);

        float currentPosX = lblNumberOfGenerations.getScale().x * LabelAsset.FONT_SIZE - 1 + lblNumberOfGenerations.getScale().x * MARGIN;
        float currentPosY = -lblNumberOfGenerations.getScale().y * LabelAsset.FONT_SIZE - 1 + lblNumberOfGenerations.getScale().y * MARGIN * 2;

        lblNumberOfGenerations.setPosition(currentPosX, currentPosY, 0);
        currentPosX += lblNumberOfGenerations.getScale().x * lblNumberOfGenerations.getMaxLength() * LabelAsset.FONT_SIZE + lblNumberOfGenerations.getScale().x * MARGIN;

        lblNumberOfPopulations.setPosition(currentPosX, currentPosY, 0);
        currentPosX += lblNumberOfPopulations.getScale().x * lblNumberOfPopulations.getMaxLength() * LabelAsset.FONT_SIZE + lblNumberOfPopulations.getScale().x * MARGIN;

        lblAverageFitness.setPosition(currentPosX, currentPosY, 0);
        currentPosX += lblAverageFitness.getScale().x * lblAverageFitness.getMaxLength() * LabelAsset.FONT_SIZE + lblAverageFitness.getScale().x * MARGIN;

        lblBestFitness.setPosition(currentPosX, currentPosY, 0);
        currentPosX += lblBestFitness.getScale().x * lblBestFitness.getMaxLength() * LabelAsset.FONT_SIZE + lblBestFitness.getScale().x * MARGIN;
    }

    private void transformButtons(float scaleX, float scaleY) {
        strImage.scale(scaleX, scaleY, 1);
        icnStart.scale(scaleX, scaleY, 1);
        icnPause.scale(scaleX, scaleY, 1);
        icnRestart.scale(scaleX, scaleY, 1);
        icnReset.scale(scaleX, scaleY, 1);
        icnPlus.scale(scaleX, scaleY, 1);

        icnMinus.scale(scaleX, scaleY, 1);

        float currentPosX = strImage.getScale().x * ButtonAsset.SCALE_X + strImage.getScale().x * MARGIN;
        float currentPosY = strImage.getScale().y * ButtonAsset.SCALE_Y + strImage.getScale().y * MARGIN;

        strImage.setPosition(1f - currentPosX, 1f - currentPosY, 0);
        currentPosY += (strImage.getScale().y * ButtonAsset.SCALE_Y * 2) + strImage.getScale().y * MARGIN;

        icnStart.setPosition(1f - currentPosX, 1f - currentPosY, 0);
        currentPosY += (icnStart.getScale().y * IconAsset.SCALE_Y * 2) + icnStart.getScale().y * MARGIN;

        icnPause.setPosition(1f - currentPosX, 1f - currentPosY, 0);
        currentPosY += (icnPause.getScale().y * IconAsset.SCALE_Y * 2) + icnPause.getScale().y * MARGIN;

        icnRestart.setPosition(1f - currentPosX, 1f - currentPosY, 0);
        currentPosY += (icnRestart.getScale().y * IconAsset.SCALE_Y * 2) + icnRestart.getScale().y * MARGIN;

        icnReset.setPosition(1f - currentPosX, 1f - currentPosY, 0);
        currentPosY += (icnReset.getScale().y * IconAsset.SCALE_Y * 2) + icnReset.getScale().y * MARGIN;

        icnPlus.setPosition(1f - currentPosX, 1f - currentPosY, 0);
        currentPosY += (icnPlus.getScale().y * IconAsset.SCALE_Y * 2) + icnPlus.getScale().y * MARGIN;

        icnMinus.setPosition(1f - currentPosX, 1f - currentPosY, 0);
        currentPosY += (icnMinus.getScale().y * IconAsset.SCALE_Y * 2) + icnMinus.getScale().y * MARGIN;
    }

    private void onSelected(final File[] files) {
        if (files.length > 0) {
            try {
                BufferedImage image = ImageIO.read(files[0]);
                arguments.geneticAlgorithm.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onStartClick() {
        arguments.mainScene.stickAsset.paused = false;
    }

    private void onPauseClick() {
        arguments.mainScene.stickAsset.paused = true;
    }

    private void onRestartClick() {
        arguments.mainScene.stickAsset.createLotOfSticks();
    }

    private void onResetClick() {
        arguments.mainScene.cameraAsset.resetRotation();
    }

    private void onPlusClick() {
        arguments.mainScene.stickAsset.speed += 2;
    }

    private void onMinusClick() {
        arguments.mainScene.stickAsset.speed -= 2;
    }
}
