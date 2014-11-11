/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.runtime.extension.Extension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRendererFormula.ColorRendererFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRendererFormula.ColorRendererFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRenderer.PaletteRendererExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRenderer.PaletteRendererExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRenderer.PaletteRendererExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRendererFormula.PaletteRendererFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRendererFormula.PaletteRendererFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.processingFormula.ProcessingFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.processingFormula.ProcessingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.transformingFormula.TransformingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.transformingFormula.TransformingFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.transformingFormula.TransformingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotRegistry {
	private ConfigurableExtensionRegistry<RenderingFormulaExtensionRuntime<?>, RenderingFormulaExtensionConfig> renderingFormulaRegistry;
	private ConfigurableExtensionRegistry<TransformingFormulaExtensionRuntime<?>, TransformingFormulaExtensionConfig> transformingFormulaRegistry;
	private ConfigurableExtensionRegistry<IncolouringFormulaExtensionRuntime<?>, IncolouringFormulaExtensionConfig> incolouringFormulaRegistry;
	private ConfigurableExtensionRegistry<OutcolouringFormulaExtensionRuntime<?>, OutcolouringFormulaExtensionConfig> outcolouringFormulaRegistry;
	private ConfigurableExtensionRegistry<ColorRendererExtensionRuntime<?>, ColorRendererExtensionConfig> colorRendererRegistry;
	private ExtensionRegistry<ColorRendererFormulaExtensionRuntime> colorRendererFormulaRegistry;
	private ConfigurableExtensionRegistry<PaletteRendererExtensionRuntime<?>, PaletteRendererExtensionConfig> paletteRendererRegistry;
	private ExtensionRegistry<PaletteRendererFormulaExtensionRuntime> paletteRendererFormulaRegistry;
	private ConfigurableExtensionRegistry<OrbitTrapExtensionRuntime<?>, OrbitTrapExtensionConfig> orbitTrapRegistry;
	private ExtensionRegistry<ProcessingFormulaExtensionRuntime> processingFormulaRegistry;

	private static class RegistryHolder {
		private static final MandelbrotRegistry instance = new MandelbrotRegistry();
	}

	private MandelbrotRegistry() {
		setRenderingFormulaRegistry(new RenderingFormulaExtensionRegistry());
		setTransformingFormulaRegistry(new TransformingFormulaExtensionRegistry());
		setIncolouringFormulaRegistry(new IncolouringFormulaExtensionRegistry());
		setOutcolouringFormulaRegistry(new OutcolouringFormulaExtensionRegistry());
		setPaletteRendererRegistry(new PaletteRendererExtensionRegistry());
		setPaletteRendererFormulaRegistry(new PaletteRendererFormulaExtensionRegistry());
		setColorRendererRegistry(new ColorRendererExtensionRegistry());
		setColorRendererFormulaRegistry(new ColorRendererFormulaExtensionRegistry());
		setOrbitTrapRegistry(new OrbitTrapExtensionRegistry());
		setProcessingFormulaRegistry(new ProcessingFormulaExtensionRegistry());
	}

	/**
	 * @return
	 */
	public static MandelbrotRegistry getInstance() {
		return RegistryHolder.instance;
	}

	/**
	 * Returns a rendering formula extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<RenderingFormulaExtensionRuntime<?>, RenderingFormulaExtensionConfig> getRenderingFormulaExtension(final String extensionId) throws ExtensionNotFoundException {
		return renderingFormulaRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * Returns a transforming formula extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<TransformingFormulaExtensionRuntime<?>, TransformingFormulaExtensionConfig> getTransformingFormulaExtension(final String extensionId) throws ExtensionNotFoundException {
		return transformingFormulaRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * Returns a incolouring formula extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<IncolouringFormulaExtensionRuntime<?>, IncolouringFormulaExtensionConfig> getIncolouringFormulaExtension(final String extensionId) throws ExtensionNotFoundException {
		return incolouringFormulaRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * Returns a outcolouring formula extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<OutcolouringFormulaExtensionRuntime<?>, OutcolouringFormulaExtensionConfig> getOutcolouringFormulaExtension(final String extensionId) throws ExtensionNotFoundException {
		return outcolouringFormulaRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<ColorRendererExtensionRuntime<?>, ColorRendererExtensionConfig> getColorRendererExtension(final String extensionId) throws ExtensionNotFoundException {
		return colorRendererRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ColorRendererFormulaExtensionRuntime> getColorRendererFormulaExtension(final String extensionId) throws ExtensionNotFoundException {
		return colorRendererFormulaRegistry.getExtension(extensionId);
	}

	/**
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<PaletteRendererExtensionRuntime<?>, PaletteRendererExtensionConfig> getPaletteRendererExtension(final String extensionId) throws ExtensionNotFoundException {
		return paletteRendererRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<PaletteRendererFormulaExtensionRuntime> getPaletteRendererFormulaExtension(final String extensionId) throws ExtensionNotFoundException {
		return paletteRendererFormulaRegistry.getExtension(extensionId);
	}

	/**
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<OrbitTrapExtensionRuntime<?>, OrbitTrapExtensionConfig> getOrbitTrapExtension(final String extensionId) throws ExtensionNotFoundException {
		return orbitTrapRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ProcessingFormulaExtensionRuntime> getProcessingFormulaExtension(final String extensionId) throws ExtensionNotFoundException {
		return processingFormulaRegistry.getExtension(extensionId);
	}

	private void setColorRendererFormulaRegistry(final ExtensionRegistry<ColorRendererFormulaExtensionRuntime> colorRendererFormulaRegistry) {
		this.colorRendererFormulaRegistry = colorRendererFormulaRegistry;
	}

	private void setColorRendererRegistry(final ConfigurableExtensionRegistry<ColorRendererExtensionRuntime<?>, ColorRendererExtensionConfig> colorRendererRegistry) {
		this.colorRendererRegistry = colorRendererRegistry;
	}

	private void setIncolouringFormulaRegistry(final ConfigurableExtensionRegistry<IncolouringFormulaExtensionRuntime<?>, IncolouringFormulaExtensionConfig> incolouringFormulaRegistry) {
		this.incolouringFormulaRegistry = incolouringFormulaRegistry;
	}

	private void setOutcolouringFormulaRegistry(final ConfigurableExtensionRegistry<OutcolouringFormulaExtensionRuntime<?>, OutcolouringFormulaExtensionConfig> outcolouringFormulaRegistry) {
		this.outcolouringFormulaRegistry = outcolouringFormulaRegistry;
	}

	private void setPaletteRendererFormulaRegistry(final ExtensionRegistry<PaletteRendererFormulaExtensionRuntime> paletteRendererFormulaRegistry) {
		this.paletteRendererFormulaRegistry = paletteRendererFormulaRegistry;
	}

	private void setPaletteRendererRegistry(final ConfigurableExtensionRegistry<PaletteRendererExtensionRuntime<?>, PaletteRendererExtensionConfig> paletteRendererRegistry) {
		this.paletteRendererRegistry = paletteRendererRegistry;
	}

	private void setRenderingFormulaRegistry(final ConfigurableExtensionRegistry<RenderingFormulaExtensionRuntime<?>, RenderingFormulaExtensionConfig> renderingFormulaRegistry) {
		this.renderingFormulaRegistry = renderingFormulaRegistry;
	}

	private void setTransformingFormulaRegistry(final ConfigurableExtensionRegistry<TransformingFormulaExtensionRuntime<?>, TransformingFormulaExtensionConfig> transformingFormulaRegistry) {
		this.transformingFormulaRegistry = transformingFormulaRegistry;
	}

	private void setOrbitTrapRegistry(final ConfigurableExtensionRegistry<OrbitTrapExtensionRuntime<?>, OrbitTrapExtensionConfig> orbitTrapRegistry) {
		this.orbitTrapRegistry = orbitTrapRegistry;
	}

	private void setProcessingFormulaRegistry(final ExtensionRegistry<ProcessingFormulaExtensionRuntime> processingFormulaRegistry) {
		this.processingFormulaRegistry = processingFormulaRegistry;
	}

	/**
	 * @return the colorRendererFormulaRegistry
	 */
	public ExtensionRegistry<ColorRendererFormulaExtensionRuntime> getColorRendererFormulaRegistry() {
		return colorRendererFormulaRegistry;
	}

	/**
	 * @return the colorRendererRegistry
	 */
	public ConfigurableExtensionRegistry<ColorRendererExtensionRuntime<?>, ColorRendererExtensionConfig> getColorRendererRegistry() {
		return colorRendererRegistry;
	}

	/**
	 * @return the incolouringFormulaRegistry
	 */
	public ConfigurableExtensionRegistry<IncolouringFormulaExtensionRuntime<?>, IncolouringFormulaExtensionConfig> getIncolouringFormulaRegistry() {
		return incolouringFormulaRegistry;
	}

	/**
	 * @return the outcolouringFormulaRegistry
	 */
	public ConfigurableExtensionRegistry<OutcolouringFormulaExtensionRuntime<?>, OutcolouringFormulaExtensionConfig> getOutcolouringFormulaRegistry() {
		return outcolouringFormulaRegistry;
	}

	/**
	 * @return the paletteRendererFormulaRegistry
	 */
	public ExtensionRegistry<PaletteRendererFormulaExtensionRuntime> getPaletteRendererFormulaRegistry() {
		return paletteRendererFormulaRegistry;
	}

	/**
	 * @return the paletteRendererRegistry
	 */
	public ConfigurableExtensionRegistry<PaletteRendererExtensionRuntime<?>, PaletteRendererExtensionConfig> getPaletteRendererRegistry() {
		return paletteRendererRegistry;
	}

	/**
	 * @return the renderingFormulaRegistry
	 */
	public ConfigurableExtensionRegistry<RenderingFormulaExtensionRuntime<?>, RenderingFormulaExtensionConfig> getRenderingFormulaRegistry() {
		return renderingFormulaRegistry;
	}

	/**
	 * @return the transformingFormulaRegistry
	 */
	public ConfigurableExtensionRegistry<TransformingFormulaExtensionRuntime<?>, TransformingFormulaExtensionConfig> getTransformingFormulaRegistry() {
		return transformingFormulaRegistry;
	}

	/**
	 * @return the transformingFormulaRegistry
	 */
	public ConfigurableExtensionRegistry<OrbitTrapExtensionRuntime<?>, OrbitTrapExtensionConfig> getOrbitTrapRegistry() {
		return orbitTrapRegistry;
	}

	/**
	 * @return the processingFormulaRegistry
	 */
	public ExtensionRegistry<ProcessingFormulaExtensionRuntime> getProcessingFormulaRegistry() {
		return processingFormulaRegistry;
	}
}
