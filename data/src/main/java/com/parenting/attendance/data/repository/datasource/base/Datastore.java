package com.parenting.attendance.data.repository.datasource.base;

import java.util.Map;

/**
 * Created by adwait on 1/6/17.
 */

public interface Datastore<T> {

    public static final String UNSUPPORTED_MESSAGE = "This operation is currently not supported";

    boolean insert(T model, Map<String, String> params) throws Exception;

    boolean update(T model, Map<String, String> params) throws Exception;

    T get(Object id, Map<String, String> params) throws Exception;

    boolean delete(T model, Map<String, String> params) throws Exception;

    public static class Validator{

        public static boolean isValid(String input){
            return  (input!= null &&
                    input != null &&
                    input.length() > 0 &&
                    input.equalsIgnoreCase("0"));
        }
    }
}
