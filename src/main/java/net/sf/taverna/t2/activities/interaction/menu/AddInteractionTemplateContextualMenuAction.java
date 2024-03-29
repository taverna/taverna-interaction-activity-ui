/*******************************************************************************
 * Copyright (C) 2007-2009 The University of Manchester
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
package net.sf.taverna.t2.activities.interaction.menu;

import java.awt.event.ActionEvent;
import java.net.URI;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.sf.taverna.t2.activities.interaction.InteractionActivity;
import net.sf.taverna.t2.activities.interaction.serviceprovider.html.InteractionServiceHtmlTemplateService;
import net.sf.taverna.t2.ui.menu.AbstractContextualMenuAction;
import net.sf.taverna.t2.workbench.activityicons.ActivityIconManager;
import net.sf.taverna.t2.workbench.file.FileManager;
import net.sf.taverna.t2.workbench.ui.workflowview.WorkflowView;
import net.sf.taverna.t2.workflowmodel.Dataflow;

import org.apache.log4j.Logger;

/**
 * An action to add a interaction activity + a wrapping processor to the
 * workflow.
 * 
 * @author Alex Nenadic
 * 
 */
@SuppressWarnings("serial")
public class AddInteractionTemplateContextualMenuAction extends
		AbstractContextualMenuAction {

	private static final String ADD_INTERACTION = "Interaction";

	private static final URI insertSection = URI
			.create("http://taverna.sf.net/2009/contextMenu/insert");

	@SuppressWarnings("unused")
	private static Logger logger = Logger
			.getLogger(AddInteractionTemplateContextualMenuAction.class);

	public AddInteractionTemplateContextualMenuAction() {
		super(insertSection, 350);
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled()
				&& (this.getContextualSelection().getSelection() instanceof Dataflow);
	}

	@Override
	protected Action createAction() {

		return new AddInteractionAction();
	}

	protected class AddInteractionAction extends AbstractAction {
		AddInteractionAction() {
			super(ADD_INTERACTION, ActivityIconManager.getInstance()
					.iconForActivity(new InteractionActivity()));
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			FileManager.getInstance().getCurrentDataflow();

			WorkflowView.importServiceDescription(
					InteractionServiceHtmlTemplateService
							.getServiceDescription(), false);
		}
	}

}
