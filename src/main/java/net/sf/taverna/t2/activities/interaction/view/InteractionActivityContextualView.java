package net.sf.taverna.t2.activities.interaction.view;

import java.awt.Frame;

import javax.swing.Action;

import net.sf.taverna.t2.activities.interaction.InteractionActivity;
import net.sf.taverna.t2.activities.interaction.InteractionActivityConfigurationBean;
import net.sf.taverna.t2.activities.interaction.InteractionActivityType;
import net.sf.taverna.t2.activities.interaction.actions.InteractionActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.actions.activity.HTMLBasedActivityContextualView;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import net.sf.taverna.t2.workflowmodel.processor.activity.config.ActivityInputPortDefinitionBean;
import net.sf.taverna.t2.workflowmodel.processor.activity.config.ActivityOutputPortDefinitionBean;


@SuppressWarnings("serial")
public class InteractionActivityContextualView extends HTMLBasedActivityContextualView<InteractionActivityConfigurationBean> {
	public InteractionActivityContextualView(Activity<?> activity) {
        super(activity);
        init();
}

private void init() {
}

@Override
protected String getRawTableRowsHtml() {
    	InteractionActivityConfigurationBean configBean = getConfigBean();
        String html = "<tr><td colspan=\"2\" align=\"center\"><strong>";
        html += "Interaction defined by ";
        if (configBean.getInteractionActivityType().equals(InteractionActivityType.VelocityTemplate)) {
        	html += "Velocity template : ";
        } else {
        	html += "HTML page : ";
        }
        html += configBean.getPresentationOrigin() + "</strong></td></tr>";
        html = html
        + "<tr><th>Input Port Name</th>" 
                +       "<th>Depth</th>" 
        +"</tr>";
		for (ActivityInputPortDefinitionBean bean : configBean
                        .getInputPortDefinitions()) {
                html = html + "<tr><td>" + bean.getName() + "</td><td>"
                                + bean.getDepth() + "</td></tr>";
        }
        html = html
                        + "<tr><th>Output Port Name</th>" 
                                +       "<th>Depth</th>" 
                        +"</tr>";
        for (ActivityOutputPortDefinitionBean bean : configBean
                        .getOutputPortDefinitions()) {
                html = html + "<tr><td>" + bean.getName() + "</td><td>"
                                + bean.getDepth() + "</td>" 
//                                              + "<td>" + bean.getGranularDepth()
//                              + "</td>"
                                + "</tr>";
        }
        return html;
}

@Override
public String getViewTitle() {
        return "Interaction service";
}

@Override
public Action getConfigureAction(Frame owner) {
		InteractionActivity interactionActivity = (InteractionActivity) getActivity();
		if (interactionActivity.getConfiguration().getInteractionActivityType()
				.equals(InteractionActivityType.LocallyPresentedHtml)) {
			return new InteractionActivityConfigurationAction(
					interactionActivity, owner);
		}
		return null;
}

@Override
public int getPreferredPosition() {
        return 100;
}

}
