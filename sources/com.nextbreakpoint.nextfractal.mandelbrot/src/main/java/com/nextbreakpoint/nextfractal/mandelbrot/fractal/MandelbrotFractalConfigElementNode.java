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
package com.nextbreakpoint.nextfractal.mandelbrot.fractal;

import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.util.ConfigElementListNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElementNodeValue;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotFractalConfigElementNode extends AbstractConfigElementNode<MandelbrotFractalConfigElement> {
	public static final String NODE_ID = MandelbrotFractalConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.MandelbrotFractalElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.MandelbrotFractalElement");
	private final MandelbrotFractalConfigElement fractalElement;

	/**
	 * Constructs a new fractal node.
	 * 
	 * @param fractalElement the fractalElement.
	 */
	public MandelbrotFractalConfigElementNode(final MandelbrotFractalConfigElement fractalElement) {
		super(MandelbrotFractalConfigElementNode.NODE_ID);
		setNodeClass(MandelbrotFractalConfigElementNode.NODE_CLASS);
		setNodeLabel(MandelbrotFractalConfigElementNode.NODE_LABEL);
		if (fractalElement == null) {
			throw new IllegalArgumentException("fractalElement is null");
		}
		this.fractalElement = fractalElement;
		createChildNodes(fractalElement);
	}

	private void createChildNodes(final MandelbrotFractalConfigElement fractalElement) {
		createRenderingFormulaNode(fractalElement);
		createTransformingFormulaNode(fractalElement);
		createIncolouringFormulaNodes(fractalElement);
		createOutcolouringFormulaNodes(fractalElement);
		createProcessingFormulaNode(fractalElement);
		createOrbitTrapNode(fractalElement);
	}

	private void createRenderingFormulaNode(final MandelbrotFractalConfigElement fractalElement) {
		final RenderingFormulaConfigElement renderingFormulaElement = fractalElement.getRenderingFormulaConfigElement();
		final RenderingFormulaElementNode renderingFormulaNode = new RenderingFormulaElementNode(renderingFormulaElement);
		appendChildNode(renderingFormulaNode);
	}

	private void createTransformingFormulaNode(final MandelbrotFractalConfigElement fractalElement) {
		final TransformingFormulaConfigElement transformingFormulaElement = fractalElement.getTransformingFormulaConfigElement();
		final TransformingFormulaElementNode renderingFormulaNode = new TransformingFormulaElementNode(transformingFormulaElement);
		appendChildNode(renderingFormulaNode);
	}

	private void createProcessingFormulaNode(final MandelbrotFractalConfigElement fractalElement) {
		final ProcessingFormulaConfigElement processingFormulaElement = fractalElement.getProcessingFormulaConfigElement();
		final ProcessingFormulaElementNode renderingFormulaNode = new ProcessingFormulaElementNode(processingFormulaElement);
		appendChildNode(renderingFormulaNode);
	}

	private void createOrbitTrapNode(final MandelbrotFractalConfigElement fractalElement) {
		final OrbitTrapConfigElement orbitTrapElement = fractalElement.getOrbitTrapConfigElement();
		final OrbitTrapElementNode renderingFormulaNode = new OrbitTrapElementNode(orbitTrapElement);
		appendChildNode(renderingFormulaNode);
	}

	private void createIncolouringFormulaNodes(final MandelbrotFractalConfigElement fractalElement) {
		appendChildNode(new IncolouringFormulaListNode(fractalElement));
	}

	private void createOutcolouringFormulaNodes(final MandelbrotFractalConfigElement fractalElement) {
		appendChildNode(new OutcolouringFormulaListNode(fractalElement));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public MandelbrotFractalConfigElement getConfigElement() {
		return fractalElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new DefaultNodeEditor<MandelbrotFractalConfigElement>(this, MandelbrotFractalConfigElementNodeValue.class);
	}

	private class IncolouringFormulaListNode extends AbstractConfigElementListNode<IncolouringFormulaConfigElement> {
		private final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.IncolouringFormulaElementList");
		public static final String NODE_CLASS = "node.class.IncolouringFormulaElementList";

		/**
		 * @param fractalElement
		 */
		public IncolouringFormulaListNode(final MandelbrotFractalConfigElement fractalElement) {
			super(MandelbrotFractalConfigElementNode.this.getNodeId() + ".incolouringFormulas", fractalElement.getIncolouringFormulaListElement());
			setNodeLabel(NODE_LABEL);
			setNodeClass(IncolouringFormulaListNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<IncolouringFormulaConfigElement> createChildNode(final IncolouringFormulaConfigElement value) {
			return new IncolouringFormulaConfigElementNode(value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return IncolouringFormulaConfigElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<IncolouringFormulaConfigElement> createNodeValue(final Object value) {
			// return new IncolouringFormulaConfigElementNodeValue((IncolouringFormulaConfigElement) value != null ? ((IncolouringFormulaConfigElement) value).clone() : null);
			return new IncolouringFormulaConfigElementNodeValue((IncolouringFormulaConfigElement) value);
		}

		private class IncolouringFormulaConfigElementNodeValue extends ConfigElementListNodeValue<IncolouringFormulaConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public IncolouringFormulaConfigElementNodeValue(final IncolouringFormulaConfigElement value) {
				super(value);
			}
		}
	}

	private class OutcolouringFormulaListNode extends AbstractConfigElementListNode<OutcolouringFormulaConfigElement> {
		private final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.OutcolouringFormulaElementList");
		public static final String NODE_CLASS = "node.class.OutcolouringFormulaElementList";

		/**
		 * @param fractalElement
		 */
		public OutcolouringFormulaListNode(final MandelbrotFractalConfigElement fractalElement) {
			super(MandelbrotFractalConfigElementNode.this.getNodeId() + ".outcolouringFormulas", fractalElement.getOutcolouringFormulaListElement());
			setNodeLabel(NODE_LABEL);
			setNodeClass(OutcolouringFormulaListNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<OutcolouringFormulaConfigElement> createChildNode(final OutcolouringFormulaConfigElement value) {
			return new OutcolouringFormulaConfigElementNode(value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return OutcolouringFormulaConfigElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<OutcolouringFormulaConfigElement> createNodeValue(final Object value) {
			// return new OutcolouringFormulaConfigElementNodeValue((OutcolouringFormulaConfigElement) value != null ? ((OutcolouringFormulaConfigElement) value).clone() : null);
			return new OutcolouringFormulaConfigElementNodeValue((OutcolouringFormulaConfigElement) value);
		}

		private class OutcolouringFormulaConfigElementNodeValue extends ConfigElementListNodeValue<OutcolouringFormulaConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public OutcolouringFormulaConfigElementNodeValue(final OutcolouringFormulaConfigElement value) {
				super(value);
			}
		}
	}

	private class RenderingFormulaElementNode extends RenderingFormulaConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public RenderingFormulaElementNode(final RenderingFormulaConfigElement formulaElement) {
			super(formulaElement);
			listener = new ConfigListener();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#dispose()
		 */
		@Override
		public void dispose() {
			if (fractalElement.getRenderingFormulaSingleElement() != null) {
				fractalElement.getRenderingFormulaSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new RenderingFormulaConfigElementNodeValue(getConfigElement()));
			fractalElement.getRenderingFormulaSingleElement().addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			fractalElement.getRenderingFormulaSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new RenderingFormulaNodeEditor(this);
		}

		protected class RenderingFormulaNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public RenderingFormulaNodeEditor(final NodeObject node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				fractalElement.getRenderingFormulaSingleElement().removeChangeListener(listener);
				fractalElement.setRenderingFormulaConfigElement(((RenderingFormulaConfigElementNodeValue) value).getValue());
				fractalElement.getRenderingFormulaSingleElement().addChangeListener(listener);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
			 */
			@Override
			protected NodeObject createChildNode(final NodeValue<?> value) {
				return null;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#getNodeValueType()
			 */
			@Override
			public Class<?> getNodeValueType() {
				return RenderingFormulaConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new RenderingFormulaConfigElementNodeValue((RenderingFormulaConfigElement) value != null ? ((RenderingFormulaConfigElement) value).clone() : null);
				return new RenderingFormulaConfigElementNodeValue((RenderingFormulaConfigElement) value);
			}
		}

		protected class ConfigListener implements ElementChangeListener {
			@Override
			public void valueChanged(final ElementChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new RenderingFormulaConfigElementNodeValue((RenderingFormulaConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((RenderingFormulaConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((RenderingFormulaConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

	private class TransformingFormulaElementNode extends TransformingFormulaConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public TransformingFormulaElementNode(final TransformingFormulaConfigElement formulaElement) {
			super(formulaElement);
			listener = new ConfigListener();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#dispose()
		 */
		@Override
		public void dispose() {
			if (fractalElement.getTransformingFormulaSingleElement() != null) {
				fractalElement.getTransformingFormulaSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new TransformingFormulaConfigElementNodeValue(getConfigElement()));
			fractalElement.getTransformingFormulaSingleElement().addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			fractalElement.getTransformingFormulaSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new TransformingFormulaNodeEditor(this);
		}

		protected class TransformingFormulaNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public TransformingFormulaNodeEditor(final NodeObject node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				fractalElement.getTransformingFormulaSingleElement().removeChangeListener(listener);
				fractalElement.setTransformingFormulaConfigElement(((TransformingFormulaConfigElementNodeValue) value).getValue());
				fractalElement.getTransformingFormulaSingleElement().addChangeListener(listener);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
			 */
			@Override
			protected NodeObject createChildNode(final NodeValue<?> value) {
				return null;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#getNodeValueType()
			 */
			@Override
			public Class<?> getNodeValueType() {
				return TransformingFormulaConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new TransformingFormulaConfigElementNodeValue((TransformingFormulaConfigElement) value != null ? ((TransformingFormulaConfigElement) value).clone() : null);
				return new TransformingFormulaConfigElementNodeValue((TransformingFormulaConfigElement) value);
			}
		}

		protected class ConfigListener implements ElementChangeListener {
			@Override
			public void valueChanged(final ElementChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new TransformingFormulaConfigElementNodeValue((TransformingFormulaConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((TransformingFormulaConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((TransformingFormulaConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

	private class ProcessingFormulaElementNode extends ProcessingFormulaConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public ProcessingFormulaElementNode(final ProcessingFormulaConfigElement formulaElement) {
			super(formulaElement);
			listener = new ConfigListener();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#dispose()
		 */
		@Override
		public void dispose() {
			if (fractalElement.getProcessingFormulaSingleElement() != null) {
				fractalElement.getProcessingFormulaSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new ProcessingFormulaConfigElementNodeValue(getConfigElement()));
			fractalElement.getProcessingFormulaSingleElement().addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			fractalElement.getProcessingFormulaSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new ProcessingFormulaNodeEditor(this);
		}

		protected class ProcessingFormulaNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public ProcessingFormulaNodeEditor(final NodeObject node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				fractalElement.getProcessingFormulaSingleElement().removeChangeListener(listener);
				fractalElement.setProcessingFormulaConfigElement(((ProcessingFormulaConfigElementNodeValue) value).getValue());
				fractalElement.getProcessingFormulaSingleElement().addChangeListener(listener);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
			 */
			@Override
			protected NodeObject createChildNode(final NodeValue<?> value) {
				return null;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#getNodeValueType()
			 */
			@Override
			public Class<?> getNodeValueType() {
				return ProcessingFormulaConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new ProcessingFormulaConfigElementNodeValue((ProcessingFormulaConfigElement) value != null ? ((ProcessingFormulaConfigElement) value).clone() : null);
				return new ProcessingFormulaConfigElementNodeValue((ProcessingFormulaConfigElement) value);
			}
		}

		protected class ConfigListener implements ElementChangeListener {
			@Override
			public void valueChanged(final ElementChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new ProcessingFormulaConfigElementNodeValue((ProcessingFormulaConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((ProcessingFormulaConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((ProcessingFormulaConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

	private class OrbitTrapElementNode extends OrbitTrapConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public OrbitTrapElementNode(final OrbitTrapConfigElement orbitTrapElement) {
			super(orbitTrapElement);
			listener = new ConfigListener();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#dispose()
		 */
		@Override
		public void dispose() {
			if (fractalElement.getOrbitTrapSingleElement() != null) {
				fractalElement.getOrbitTrapSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new OrbitTrapConfigElementNodeValue(getConfigElement()));
			fractalElement.getOrbitTrapSingleElement().addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			fractalElement.getOrbitTrapSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new OrbitTrapNodeEditor(this);
		}

		protected class OrbitTrapNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public OrbitTrapNodeEditor(final NodeObject node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				fractalElement.getOrbitTrapSingleElement().removeChangeListener(listener);
				fractalElement.setOrbitTrapConfigElement(((OrbitTrapConfigElementNodeValue) value).getValue());
				fractalElement.getOrbitTrapSingleElement().addChangeListener(listener);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
			 */
			@Override
			protected NodeObject createChildNode(final NodeValue<?> value) {
				return null;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#getNodeValueType()
			 */
			@Override
			public Class<?> getNodeValueType() {
				return OrbitTrapConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new OrbitTrapConfigElementNodeValue((OrbitTrapConfigElement) value != null ? ((OrbitTrapConfigElement) value).clone() : null);
				return new OrbitTrapConfigElementNodeValue((OrbitTrapConfigElement) value);
			}
		}

		protected class ConfigListener implements ElementChangeListener {
			@Override
			public void valueChanged(final ElementChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new OrbitTrapConfigElementNodeValue((OrbitTrapConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((OrbitTrapConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((OrbitTrapConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}
}
