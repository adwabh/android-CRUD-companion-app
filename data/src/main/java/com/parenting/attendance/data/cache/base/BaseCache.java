/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.parenting.attendance.data.cache.base;

/**
 * An interface representing a user Cache.
 */
public interface BaseCache {
//
//  T get(final int Id);
//
//  /**
//   * Puts and element into the cache.
//   *
//   * @param userEntity Element to insert in the cache.
//   */
//  void put(T userEntity);

    /**
     * Checks if an element (User) exists in the cache.
     *
     * @param Id The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(final long Id) throws Exception;

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();

    /**
     * Set in millis, the last time the cache was accessed.
     */
    void setLastCacheUpdateTimeMillis();

    /**
     * Get in millis, the last time the cache was accessed.
     */
    long getLastCacheUpdateTimeMillis();

}
