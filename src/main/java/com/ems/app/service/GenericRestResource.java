package com.ems.app.service;

import static com.ems.app.util.Constants.ENDPOINT;
import static com.ems.app.util.Constants.ID;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import com.ems.app.dao.ResourceDAO;
import com.ems.app.manager.api.ResourceManager;
import com.ems.app.object.BaseResource;

@Path("/{endpoint}")
public class GenericRestResource {
	private final ServiceLocator serviceLocator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
	Class resourceDAO = null;
	
	@GET
	@Produces({APPLICATION_JSON})
	@SuppressWarnings("unchecked")
	public Response findResources(
			@PathParam(ENDPOINT) String endpoint
			) throws Exception {

		ResourceManager<?> resourceManager = provide(endpoint, ResourceManager.class);
		
		List<Object> response = (List<Object>) resourceManager.search(resourceDAO);
		GenericEntity<List<Object>> entity = 
				 new GenericEntity<List<Object>>(response) {};
		return Response.ok(entity).build();

	}
	
	@Path("/{id}")
	@GET
	@Produces({APPLICATION_JSON})
	@SuppressWarnings("unchecked")
	public Response findResourceById(
			@PathParam(ENDPOINT) String endpoint,
			@PathParam(ID) String id
			) throws Exception {

		ResourceManager<?> resourceManager = provide(endpoint, ResourceManager.class);
		
		Object response = (Object) resourceManager.get(resourceDAO, id);
		GenericEntity<Object> entity = 
				 new GenericEntity<Object>(response) {};
		return Response.ok(entity).build();

	}

	private <T extends ResourceManager<R>, R extends BaseResource> T  provide(String endpoint,
			Class<ResourceManager> resourceManagerClass) throws Exception {
		String managerClass = null;
		String manageImplClass = null;
		String daoClass = null;
		// TODO Need to change the logic of getting manger class based on endpoint
		if(endpoint.equals("users")){
			managerClass = "com.ems.app.manager.api.UserManager";
			manageImplClass = "com.ems.app.manager.impl.UserManagerImpl";
			daoClass = "com.ems.app.dao.UserDAO";
		} else {
			throw new Exception("Invalid Endpoint");
		}
		
		Class<T> resourceMgr = null;
		Class<T> resourceMgrImpl = null;
		
		try {
			resourceMgr = (Class<T>) Class.forName(managerClass).asSubclass(resourceManagerClass);
			resourceMgrImpl = (Class<T>) Class.forName(manageImplClass).asSubclass(resourceManagerClass);
			resourceDAO = Class.forName(daoClass).asSubclass(ResourceDAO.class);
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
        config.bind(BuilderHelper.link(resourceMgrImpl).to(resourceMgr).build());
        config.commit();
        
        //TODO use ServiceLocatorUtilities.createAndPopulateServiceLocator() instead
		return locator.getService(resourceMgr);
	}
	

}
