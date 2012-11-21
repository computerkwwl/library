/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.beangle.commons.entity.pojo.EnabledEntity;
import org.beangle.commons.entity.pojo.LongIdTimeEntity;
import org.beangle.commons.entity.pojo.TemporalActiveEntity;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface User extends LongIdTimeEntity, TemporalActiveEntity, EnabledEntity, Principal {

  // 新建用户的缺省密码
  static final String DEFAULT_PASSWORD = "1";

  /**
   * 名称
   * 
   * @return user's name
   */
  String getName();

  /**
   * 用户真实姓名
   */
  String getFullname();

  /**
   * 用户密码(不限制是明码还是密文)
   */
  String getPassword();

  /**
   * 用户邮件
   */
  String getMail();

  /**
   * 对应角色成员
   */
  Set<Member> getMembers();

  /**
   * 对应角色
   * 
   * @return 按照角色代码排序的role列表
   */
  List<Role> getRoles();

  /**
   * 创建者
   */
  User getCreator();

  /**
   * 是否启用
   */
  boolean isEnabled();

  /**
   * 备注
   */
  String getRemark();

  /**
   * 账户是否过期
   */
  boolean isAccountExpired();

  /**
   * 是否密码过期
   */
  boolean isPasswordExpired();

}
