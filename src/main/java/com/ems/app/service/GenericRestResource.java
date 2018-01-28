package com.ems.app.service;

import static com.ems.app.util.Constants.ENDPOINT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import com.ems.app.manager.api.ResourceManager;
import com.ems.app.manager.api.UserManager;
import com.ems.app.manager.impl.UserManagerImpl;
import com.ems.app.object.BaseResource;

@Path("/{endpoint}")
public class GenericRestResource {
	private final ServiceLocator serviceLocator = ServiceLocatorUtilities.createAndPopulateServiceLocator();

	@GET
	@Produces({APPLICATION_JSON})
	@SuppressWarnings("unchecked")
	public List<? extends BaseResource> findResources(
			@PathParam(ENDPOINT) String endpoint
			) throws Exception {

		ResourceManager<?> resourceManager = provide(endpoint, ResourceManager.class);
		
		List<? extends BaseResource> returnVal = resourceManager.search();
		return returnVal;

	}

	private <T extends ResourceManager<R>, R extends BaseResource> T  provide(String endpoint,
			Class<ResourceManager> resourceManagerClass) {
		String managerClass = null;
		// TODO Need to change the logic of getting manger class based on endpoint
		if(endpoint.equals("users")){
			managerClass = "com.ems.app.manager.api.UserManager";
		}
		
		Class<T> resourceMgr = null;
		
		try {
			resourceMgr = (Class<T>) Class.forName(managerClass).asSubclass(resourceManagerClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Obtain a ServiceLocator object
        ServiceLocatorFactory factory = ServiceLocatorFactory.getInstance();
        ServiceLocator locator = factory.create("default");
        
        // Obtain the DynamicConfiguration object for binding a service to a contract.
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        
        // binding a service to a contract.
        config.bind(BuilderHelper.link(UserManagerImpl.class).to(UserManager.class).build());
        config.commit();
        
        //TODO user ServiceLocatorUtilities.createAndPopulateServiceLocator() instead
		return locator.getService(resourceMgr);
	}
	

}
