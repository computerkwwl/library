/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.web.auth.preauth;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.lang.Option;

/**
 * Source of the username supplied with pre-authenticated authentication
 * request. The username can be supplied in the request: in cookie, request
 * header, request parameter or as ServletRequest.getRemoteUser().
 */
public interface UsernameSource {
  /**
   * Obtain username supplied in the request.
   * 
   * @param request with username
   * @return username
   */
  Option<String> obtainUsername(HttpServletRequest request);
}
