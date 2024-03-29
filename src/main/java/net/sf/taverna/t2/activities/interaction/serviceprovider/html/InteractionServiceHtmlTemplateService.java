/**
 *
 */
package net.sf.taverna.t2.activities.interaction.serviceprovider.html;

import java.net.URI;

import javax.swing.Icon;

import net.sf.taverna.t2.activities.interaction.InteractionActivity;
import net.sf.taverna.t2.activities.interaction.InteractionActivityConfigurationBean;
import net.sf.taverna.t2.activities.interaction.InteractionActivityType;
import net.sf.taverna.t2.activities.interaction.serviceprovider.InteractionServiceIcon;
import net.sf.taverna.t2.servicedescriptions.AbstractTemplateService;
import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

/**
 * @author alanrw
 * 
 */
public class InteractionServiceHtmlTemplateService extends
		AbstractTemplateService<InteractionActivityConfigurationBean> {

	private static final URI providerId = URI
			.create("http://taverna.sf.net/2012/service-provider/interaction");

	@Override
	public Class<? extends Activity<InteractionActivityConfigurationBean>> getActivityClass() {
		return InteractionActivity.class;
	}

	@Override
	public InteractionActivityConfigurationBean getActivityConfiguration() {
		final InteractionActivityConfigurationBean configBean = new InteractionActivityConfigurationBean();
		configBean
				.setInteractionActivityType(InteractionActivityType.LocallyPresentedHtml);
		return configBean;
	}

	@Override
	public Icon getIcon() {
		return InteractionServiceIcon.getIcon();
	}

	@Override
	public String getId() {
		return providerId.toString();
	}

	@Override
	public String getName() {
		return ("Interaction");
	}

	@SuppressWarnings({ "rawtypes" })
	public static ServiceDescription getServiceDescription() {
		final InteractionServiceHtmlTemplateService bts = new InteractionServiceHtmlTemplateService();
		return bts.templateService;
	}
	
	@Override
	public String getDescription() {
		return "A service for browser-based interaction with a workflow run";	
	}

}
