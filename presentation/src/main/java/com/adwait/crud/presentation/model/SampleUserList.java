package com.adwait.crud.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adwait on 21/03/18.
 */

public class SampleUserList implements Parcelable,Serializable{
    private String message;
    private String responseCode;
    private SampleUserListData data;

    public SampleUserList() {
    }

    protected SampleUserList(Parcel in) {
        message = in.readString();
        responseCode = in.readString();
        data = in.readParcelable(SampleUserListData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(responseCode);
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<SampleUserList> CREATOR = new Parcelable.Creator<SampleUserList>() {
        @Override
        public SampleUserList createFromParcel(Parcel in) {
            return new SampleUserList(in);
        }

        @Override
        public SampleUserList[] newArray(int size) {
            return new SampleUserList[size];
        }
    };


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

    public static class SampleUserListData implements Parcelable,Serializable {
        private ArrayList<SampleUser> user_list;

        public SampleUserListData() {
        }

        protected SampleUserListData(Parcel in) {
            user_list = in.createTypedArrayList(SampleUser.CREATOR);
        }

        public static final Creator<SampleUserListData> CREATOR = new Creator<SampleUserListData>() {
            @Override
            public SampleUserListData createFromParcel(Parcel in) {
                return new SampleUserListData(in);
            }

            @Override
            public SampleUserListData[] newArray(int size) {
                return new SampleUserListData[size];
            }
        };

        public ArrayList<SampleUser> getUser_list() {
            return user_list;
        }

        public void setUser_list(ArrayList<SampleUser> user_list) {
            this.user_list = user_list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(user_list);
        }
    }

    public static class SampleUser implements Parcelable,Serializable{
        private String email;
        private String username;

        public SampleUser() {
        }

        protected SampleUser(Parcel in) {
            email = in.readString();
            username = in.readString();
        }

        public static final Creator<SampleUser> CREATOR = new Creator<SampleUser>() {
            @Override
            public SampleUser createFromParcel(Parcel in) {
                return new SampleUser(in);
            }

            @Override
            public SampleUser[] newArray(int size) {
                return new SampleUser[size];
            }
        };

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
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(email);
            parcel.writeString(username);
        }
    }
}
