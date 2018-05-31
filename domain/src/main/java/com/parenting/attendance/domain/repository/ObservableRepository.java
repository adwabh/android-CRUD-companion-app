package com.parenting.attendance.domain.repository;


import java.util.Map;

import io.reactivex.Observable;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface ObservableRepository<T> {

    boolean insert(T model, Map<String, String> params) throws Exception;

    boolean update(T model, Map<String, String> params) throws Exception;

    Observable<T> get(Object id, Map<String, String> params) throws Exception;

    boolean delete(T model, Map<String, String> params) throws Exception;
}
