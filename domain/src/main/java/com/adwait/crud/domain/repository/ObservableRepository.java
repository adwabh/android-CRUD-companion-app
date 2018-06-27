package com.adwait.crud.domain.repository;


import java.util.Map;

import io.reactivex.Observable;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface ObservableRepository<T> {

    Observable<Boolean> insert(T model, Map<String, String> params) throws Exception;

    Observable<Boolean> update(T model, Map<String, String> params) throws Exception;

    Observable<T> get(Object id, Map<String, String> params) throws Exception;

    Observable<Boolean> delete(T model, Map<String, String> params) throws Exception;
}
