package com.adwait.crud.domain.models;

import com.adwait.crud.domain.repository.Cacheble;

import java.util.ArrayList;

/**
 * Created by adwait on 21/03/18.
 */

public class SampleUserListResponse implements Cacheble {
    private String message;
    private String responseCode;
    private SampleUserListData data;

    public SampleUserListResponse() {
    }
    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public String getResponseCode() {
        return responseCode;
    }


    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public SampleUserListData getData() {
        return data;
    }

    public void setData(SampleUserListData data) {
        this.data = data;
    }

    public static class SampleUserListData{
        private ArrayList<SampleUser> user_list;

        public SampleUserListData() {
        }

        public ArrayList<SampleUser> getUser_list() {
            return user_list;
        }

        public void setUser_list(ArrayList<SampleUser> user_list) {
            this.user_list = user_list;
        }
    }

    public static class SampleUser implements Cacheble{
        private String email;
        private String username;

        public SampleUser() {
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }


        @Override
        public String key() {
            return KEY_TEMP;
        }
    }

    @Override
    public String key() {
        return KEY_TEMP;
    }
}
