/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lightadmin.core.web.security;

import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This implementation of {@code RequestCache} stores request in HttpSession as
 * LigtAdmin-specific attribute.
 */
public class LightAdminRequestCache extends HttpSessionRequestCache {

    protected final String savedRequestKey = "lightadmin:SPRING_SECURITY_SAVED_REQUEST";
    protected final PortResolver portResolver = new PortResolverImpl();

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, portResolver);
        request.getSession().setAttribute(savedRequestKey, savedRequest);
        logger.debug("DefaultSavedRequest added to Session: " + savedRequest);
    }

    @Override
    public SavedRequest getRequest(HttpServletRequest currentRequest, HttpServletResponse response) {
        HttpSession session = currentRequest.getSession(false);
        if (session != null) {
            return (DefaultSavedRequest) session.getAttribute(savedRequestKey);
        }
        return null;
    }

    @Override
    public void removeRequest(HttpServletRequest currentRequest, HttpServletResponse response) {
        HttpSession session = currentRequest.getSession(false);
        if (session != null) {
            logger.debug("Removing DefaultSavedRequest from session if present");
            session.removeAttribute(savedRequestKey);
        }
    }

}
