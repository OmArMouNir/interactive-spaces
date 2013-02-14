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

package interactivespaces.workbench.activity.project;

import interactivespaces.domain.basic.pojo.SimpleConfigurationParameter;
import interactivespaces.workbench.activity.project.creator.ActivityProjectTemplate;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * A specification for creating a project.
 * 
 * @author Keith M. Hughes
 */
public class ActivityProjectCreationSpecification {

	/**
	 * The language for the project.
	 */
	private String language;

	/**
	 * The template for the project.
	 */
	private ActivityProjectTemplate template;

	/**
	 * The executable for the project.
	 */
	private String executable;

	/**
	 * The project itself.
	 */
	private ActivityProject project;

	/**
	 * Extra configuration parameters for the activity
	 */
	private List<SimpleConfigurationParameter> extraConfigurationParameters = Lists
			.newArrayList();

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the template
	 */
	public ActivityProjectTemplate getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(ActivityProjectTemplate template) {
		this.template = template;
	}

	/**
	 * @return the project
	 */
	public ActivityProject getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(ActivityProject project) {
		this.project = project;
	}

	/**
	 * @return the executable
	 */
	public String getExecutable() {
		return executable;
	}

	/**
	 * @param executable
	 *            the executable to set
	 */
	public void setExecutable(String executable) {
		this.executable = executable;
	}
	
	/**
	 * Get all extra configurations for the activity.
	 * 
	 * @return all extra configurations for the activity
	 */
	public List<SimpleConfigurationParameter> getExtraConfigurationParameters() {
		return extraConfigurationParameters;
	}
	
	/**
	 * Add an extra configuration parameter to the spec.
	 * 
	 * @param parameter
	 * 		the new configuration parameter to add
	 */
	public void addExtraConfigurationParameter(SimpleConfigurationParameter parameter) {
		extraConfigurationParameters.add(parameter);
	}
	
	/**
	 * Add an extra configuration parameter to the spec.
	 * 
	 * @param name
	 * 		the name of the parameter to add
	 * @param value
	 * 		the value of the parameter to add
	 */
	public void addExtraConfigurationParameter(String name, String value) {
		SimpleConfigurationParameter parameter = new SimpleConfigurationParameter();
		parameter.setName(name);
		parameter.setValue(value);
		
		extraConfigurationParameters.add(parameter);
	}
}
