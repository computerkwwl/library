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
package org.beangle.commons.cache;

import java.util.Collection;

/**
 * Cache Manager
 * 
 * @author chaostone
 * @since 3.2.0
 */
public interface CacheManager {

  /**
   * Return the cache associated with the given name.
   */
  <K, V> Cache<K, V> getCache(String name);

  /**
   * Return a collection of the caches known by this cache manager.
   */
  Collection<String> getCacheNames();

}
