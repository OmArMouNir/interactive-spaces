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

package interactivespaces.workbench.activity.project.type;

import interactivespaces.workbench.activity.project.type.android.AndroidActivityProjectType;
import interactivespaces.workbench.activity.project.type.java.JavaActivityProjectType;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * A simple implemention of a {@link ActivityProjectTypeRegistry}.
 *
 * @author Keith M. Hughes
 */
public class SimpleActivityProjectTypeRegistery implements ActivityProjectTypeRegistry {

	/**
	 * The mapping of names to project types.
	 */
	Map<String, ActivityProjectType> nameToTypes = Maps.newHashMap();
	
	public SimpleActivityProjectTypeRegistery() {
		registerActivityProjectType(new JavaActivityProjectType());
		registerActivityProjectType(new AndroidActivityProjectType());
	}

	@Override
	public ActivityProjectType getActivityProjectType(String name) {
		return nameToTypes.get(name);
	}

	@Override
	public void registerActivityProjectType(ActivityProjectType type) {
		nameToTypes.put(type.getName(), type);
	}
}
