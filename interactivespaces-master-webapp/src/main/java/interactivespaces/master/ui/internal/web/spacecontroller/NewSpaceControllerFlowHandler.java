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

package interactivespaces.master.ui.internal.web.spacecontroller;

import interactivespaces.master.server.services.ControllerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

/**
 * Spring Webflow handler for new controllers.
 *
 * @author Keith M. Hughes
 */
public class NewSpaceControllerFlowHandler extends AbstractFlowHandler {

    private static final String DEFAULT_URL = "/spacecontroller/all.html";
    
    private ControllerRepository controllerRepository;

	@Override
    public String handleExecutionOutcome(FlowExecutionOutcome outcome, HttpServletRequest request,
            HttpServletResponse response) {
		System.out.println(outcome.getOutput().get("flowscope.controller"));
        return DEFAULT_URL;
    }

    @Override
    public String handleException(FlowException e, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof NoSuchFlowExecutionException) {
            return DEFAULT_URL;
        } else {
            throw e;
        }
    }

	/**
	 * @return the controllerRepository
	 */
	public ControllerRepository getControllerRepository() {
		return controllerRepository;
	}

	/**
	 * @param controllerRepository the controllerRepository to set
	 */
	public void setControllerRepository(ControllerRepository controllerRepository) {
		this.controllerRepository = controllerRepository;
	}

}
