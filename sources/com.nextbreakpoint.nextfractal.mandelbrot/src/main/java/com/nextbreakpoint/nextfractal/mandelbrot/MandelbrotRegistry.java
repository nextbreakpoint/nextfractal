/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.extension.ColorRendererExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.extension.ColorRendererExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.extension.ColorRendererExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula.extension.ColorRendererFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula.extension.ColorRendererFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.extension.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.extension.IncolouringFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.extension.IncolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.extension.PaletteRendererExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.extension.PaletteRendererExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.extension.PaletteRendererExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.extension.PaletteRendererFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.extension.PaletteRendererFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.extension.TransformingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.extension.TransformingFormulaExtensionRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.extension.TransformingFormulaExtensionRuntime;

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
