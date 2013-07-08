/*
 * Copyright (C) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package interactivespaces.master.server.services.internal.jpa;

import interactivespaces.InteractiveSpacesException;
import interactivespaces.domain.basic.SpaceController;
import interactivespaces.master.server.services.ActivityRepository;
import interactivespaces.master.server.services.ControllerRepository;
import interactivespaces.master.server.services.internal.jpa.domain.JpaSpaceController;
import interactivespaces.util.uuid.UuidGenerator;

import java.util.List;
import java.util.Map;

import org.springframework.orm.jpa.JpaTemplate;

import com.google.common.collect.Maps;

/**
 * A JPA implementation of {@linkControllerRepository}.
 * 
 * @author Keith M. Hughes
 */
public class JpaControllerRepository implements ControllerRepository {

	/**
	 * The repository for activity entities.
	 */
	private ActivityRepository activityRepository;

	/**
	 * The UUID generator to use.
	 */
	private UuidGenerator uuidGenerator;

	/**
	 * The Spring JPA template.
	 */
	private JpaTemplate template;

	@Override
	public SpaceController newSpaceController() {
		SpaceController controller = new JpaSpaceController();

		controller.setUuid(uuidGenerator.newUuid());

		return controller;
	}

	@Override
	public SpaceController newSpaceController(SpaceController template) {
		return copySpaceControllerTemplate(uuidGenerator.newUuid(), template);
	}

	@Override
	public SpaceController newSpaceController(String uuid,
			SpaceController template) {
		return copySpaceControllerTemplate(uuid, template);
	}

	/**
	 * Create a new controller and fill in the controller from the template.
	 * 
	 * @param uuid
	 *            the uuid to give the controller
	 * @param template
	 *            the template with the values for the new controller
	 * 
	 * @return a new controller with the specified values
	 */
	private SpaceController copySpaceControllerTemplate(String uuid,
			SpaceController template) {
		SpaceController controller = new JpaSpaceController();
		controller.setUuid(uuid);
		controller.setName(template.getName());
		controller.setDescription(template.getDescription());
		controller.setHostId(template.getHostId());

		return controller;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpaceController> getAllSpaceControllers() {
		return template.findByNamedQuery("spaceControllerAll");
	}

	@Override
	public SpaceController getSpaceControllerById(String id) {
		return template.find(JpaSpaceController.class, id);
	}

	@Override
	public SpaceController getSpaceControllerByUuid(String uuid) {
		Map<String, String> params = Maps.newHashMap();
		params.put("uuid", uuid);
		@SuppressWarnings("unchecked")
		List<SpaceController> results = template
				.findByNamedQueryAndNamedParams("spaceControllerByUuid", params);
		if (!results.isEmpty()) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public SpaceController saveSpaceController(SpaceController controller) {
		if (controller.getId() != null) {
			return template.merge(controller);
		} else {
			template.persist(controller);

			return controller;
		}
	}

	@Override
	public void deleteSpaceController(SpaceController controller) {
		long count = activityRepository
				.getCountLiveActivitiesByController(controller);
		if (count == 0) {
			template.remove(controller);
		} else {
			throw new InteractiveSpacesException(
					String.format(
							"Cannot delete space controller %s, it is in %d live activities",
							controller.getId(), count));
		}
	}

	/**
	 * @param activityRepository
	 *            the activityRepository to set
	 */
	public void setActivityRepository(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	/**
	 * @param uuidGenerator
	 *            the uuidGenerator to set
	 */
	public void setUuidGenerator(UuidGenerator uuidGenerator) {
		this.uuidGenerator = uuidGenerator;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(JpaTemplate template) {
		this.template = template;
	}
}
