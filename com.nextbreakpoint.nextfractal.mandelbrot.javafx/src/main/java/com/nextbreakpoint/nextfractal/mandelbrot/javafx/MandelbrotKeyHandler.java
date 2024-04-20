package com.nextbreakpoint.nextfractal.mandelbrot.javafx;

import com.nextbreakpoint.nextfractal.core.javafx.KeyHandler;
import com.nextbreakpoint.nextfractal.core.javafx.MetadataDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.RenderingContext;
import com.nextbreakpoint.nextfractal.mandelbrot.module.MandelbrotMetadata;
import javafx.scene.input.KeyEvent;

public class MandelbrotKeyHandler implements KeyHandler {
    private final RenderingContext renderingContext;
    private final MetadataDelegate delegate;

    public MandelbrotKeyHandler(RenderingContext renderingContext, MetadataDelegate delegate) {
        this.renderingContext = renderingContext;
        this.delegate = delegate;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case DIGIT1: {
                renderingContext.setZoomSpeed(1.005);
                break;
            }
            case DIGIT2: {
                renderingContext.setZoomSpeed(1.01);
                break;
            }
            case DIGIT3: {
                renderingContext.setZoomSpeed(1.025);
                break;
            }
            case DIGIT4: {
                renderingContext.setZoomSpeed(1.05);
                break;
            }
            case DIGIT5: {
                renderingContext.setZoomSpeed(1.10);
                break;
            }
            case T: {
                final MandelbrotMetadata metadata = (MandelbrotMetadata) delegate.getMetadata();
                final MandelbrotMetadata newMetadata = metadata.toBuilder().withOptions(metadata.getOptions()
                        .toBuilder().withShowTraps(!metadata.getOptions().isShowTraps()).build()).build();
                delegate.onMetadataChanged(newMetadata, false, true);
                break;
            }
            case O: {
                final MandelbrotMetadata metadata = (MandelbrotMetadata) delegate.getMetadata();
                final MandelbrotMetadata newMetadata = metadata.toBuilder().withOptions(metadata.getOptions()
                        .toBuilder().withShowOrbit(!metadata.getOptions().isShowOrbit()).build()).build();
                delegate.onMetadataChanged(newMetadata, false, true);
                break;
            }
            case P: {
                final MandelbrotMetadata metadata = (MandelbrotMetadata) delegate.getMetadata();
                final MandelbrotMetadata newMetadata = metadata.toBuilder().withOptions(metadata.getOptions()
                        .toBuilder().withShowPreview(!metadata.getOptions().isShowPreview() && !metadata.isJulia()).build()).build();
                delegate.onMetadataChanged(newMetadata, false, true);
                break;
            }
        }
    }
}
