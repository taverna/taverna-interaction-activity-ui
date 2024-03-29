/*******************************************************************************
 * Copyright (C) 2007 The University of Manchester
 *
 *  Modifications to the initial code base are copyright of their
 *  respective authors, or their employers as appropriate.
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 ******************************************************************************/
package net.sf.taverna.t2.activities.interaction.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import net.sf.taverna.t2.workflowmodel.processor.activity.config.ActivityInputPortDefinitionBean;

@SuppressWarnings("serial")
public class InteractionInputViewer extends JPanel {

	private final ActivityInputPortDefinitionBean bean;

	private JTextField nameField;

	private JSpinner depthSpinner;

	private JCheckBox publishField;

	private boolean editable;

	/**
	 * Calls {@link #initView()} to set the look and feel and sets the
	 * components to be editable or not
	 * 
	 * @param bean
	 *            the {@link ActivityInputPortDefinitionBean} which represents
	 *            the view
	 * @param editable
	 *            whether the components should be enable for editing or not
	 */
	public InteractionInputViewer(final ActivityInputPortDefinitionBean bean,
			final boolean editable) {
		this.bean = bean;
		this.editable = editable;
		this.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		this.initView();
		this.publishField.setSelected(!bean.getTranslatedElementType().equals(
				String.class));
		this.setEditMode();
	}

	/**
	 * Uses {@link GridBagLayout} to layout the overall component. Adds the
	 * individual editable elements to the view to allow parts of the
	 * {@link ActivityInputPortDefinitionBean} to be changeD
	 */
	private void initView() {
		this.setLayout(new GridBagLayout());
		final GridBagConstraints outerConstraint = new GridBagConstraints();
		outerConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
		outerConstraint.gridx = 0;
		outerConstraint.gridy = 0;
		outerConstraint.weighty = 0;
		outerConstraint.weightx = 0.1;
		outerConstraint.fill = GridBagConstraints.BOTH;

		this.nameField = new JTextField(this.bean.getName());
		this.add(this.nameField, outerConstraint);

		this.publishField = new JCheckBox();
		outerConstraint.gridx = 1;
		this.add(this.publishField, outerConstraint);

		outerConstraint.gridx = 2;
		final SpinnerNumberModel model = new SpinnerNumberModel(new Integer(
				this.bean.getDepth()), new Integer(0), new Integer(100),
				new Integer(1));
		this.depthSpinner = new JSpinner(model);
		this.depthSpinner.setEnabled(false);
		this.depthSpinner
				.setToolTipText("A depth of 0 means a simple value, like a string. Depth 1 is a list of simple values, while depth 2 is a list of a list of simple values");
		this.depthSpinner.setValue(this.bean.getDepth());

		this.add(this.depthSpinner, outerConstraint);

	}

	/**
	 * Get the component which allows the
	 * {@link ActivityInputPortDefinitionBean} name to be edited
	 * 
	 * @return
	 */
	public JTextField getNameField() {
		return this.nameField;
	}

	/**
	 * Change the depth of the {@link ActivityInputPortDefinitionBean}
	 * 
	 * @return
	 */
	public JSpinner getDepthSpinner() {
		return this.depthSpinner;
	}

	/**
	 * Get the actual {@link ActivityInputPortDefinitionBean} which is
	 * represented by this view
	 * 
	 * @return
	 */
	public ActivityInputPortDefinitionBean getBean() {
		return this.bean;
	}

	/**
	 * Can the components on this view be edited?
	 * 
	 * @return
	 */
	public boolean isEditable() {
		return this.editable;
	}

	/**
	 * Set all the components to be editable or not
	 * 
	 * @param editable
	 */
	public void setEditable(final boolean editable) {
		this.editable = editable;
		this.setEditMode();
	}

	/**
	 * Sets the {@link #nameField}, {@link #literalSelector} and
	 * {@link #depthSpinner} to allow editing
	 */
	public void setEditMode() {
		this.nameField.setEditable(this.editable);
		this.publishField.setEnabled(this.editable);
		this.depthSpinner.setEnabled(this.editable);

	}

	/**
	 * @return the publishField
	 */
	public JCheckBox getPublishField() {
		return this.publishField;
	}

}
