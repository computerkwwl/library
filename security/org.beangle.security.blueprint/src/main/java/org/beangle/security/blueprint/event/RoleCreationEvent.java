/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.event;

import org.beangle.security.SecurityUtils;
import org.beangle.security.blueprint.Role;

/**
 * @author chaostone
 * @version $Id: RoleCreationEvent.java Jul 27, 2011 10:30:24 AM chaostone $
 */
public class RoleCreationEvent extends RoleEvent {
  private static final long serialVersionUID = -60909204679372326L;

  public RoleCreationEvent(Role role) {
    super(role);
    setSubject(SecurityUtils.getUsername() + " 创建了 " + getRole().getName());
  }
}