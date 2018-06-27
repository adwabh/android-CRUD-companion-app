package com.adwait.crud.domain.repository;


import java.util.Map;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface Repository<T> {

    boolean insert(T model, Map<String, String> params) throws Exception;

    boolean update(T model, Map<String, String> params) throws Exception;

    T get(Object id, Map<String, String> params) throws Exception;

    boolean delete(T model, Map<String, String> params) throws Exception;
}
