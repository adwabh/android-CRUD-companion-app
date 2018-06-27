package com.adwait.crud.data.repository.datasource.base;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by adwait on 1/6/17.
 */

public interface ObservableDatastore<T> {

    public static final String UNSUPPORTED_MESSAGE = "This operation is currently not supported";

    Observable<Boolean> insert(T model, Map<String, String> params) throws Exception;

    Observable<Boolean> update(T model, Map<String, String> params) throws Exception;

    Observable<T> get(Object id, Map<String, String> params) throws Exception;

    Observable<Boolean> delete(T model, Map<String, String> params) throws Exception;

    public static class Validator{

        public static boolean isValid(String input){
            return  (input!= null &&
                    input != null &&
                    input.length() > 0 &&
                    input.equalsIgnoreCase("0"));
        }
    }
}
