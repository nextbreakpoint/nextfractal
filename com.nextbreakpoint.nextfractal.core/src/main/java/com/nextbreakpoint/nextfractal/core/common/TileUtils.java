package com.nextbreakpoint.nextfractal.core.common;

import com.nextbreakpoint.nextfractal.core.render.RendererPoint;
import com.nextbreakpoint.nextfractal.core.render.RendererSize;
import com.nextbreakpoint.nextfractal.core.render.RendererTile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TileUtils {
    public static RendererTile createRendererTile(int width, int height, int rows, int columns, int row, int column) {
        int tileWidth = Math.round((float) width / (float) columns);
        int tileHeight = Math.round((float) height / (float) rows);
        RendererSize imageSize = new RendererSize(width, height);
        RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
        RendererSize tileBorder = new RendererSize(0, 0);
        RendererPoint tileOffset = new RendererPoint(Math.round(((float) column * (float) width) / (float) columns), Math.round(((float) row * (float) height) / (float) rows));
        return new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
    }

    public static RendererTile createRendererTile(int width, int height) {
        RendererSize imageSize = new RendererSize(width, height);
        RendererSize tileSize = new RendererSize(width, height);
        RendererSize tileBorder = new RendererSize(0, 0);
        RendererPoint tileOffset = new RendererPoint(0, 0);
        return new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
    }

    public static RendererTile createRendererTile(int size) {
        return createRendererTile(size, size);
    }

    public static RendererTile createRendererTile(double width) {
        return createRendererTile(computeSize(width, 0.05));
    }

    private static int computeSize(double width, double percentage) {
        return (int) Math.rint(width * percentage);
    }
}
