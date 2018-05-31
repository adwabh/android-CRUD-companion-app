package com.parenting.attendance.presentation.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by apple on 25/05/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private String isLoggedIn;
    private String isSkipedCalled;

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        /*this.isLoggedIn = EatRepeatPref.getInstance().getString(this, EatRepeatPref.PreferenceKey.isLoggedIn, "0");
        this.isSkipedCalled = EatRepeatPref.getInstance().getString(this, EatRepeatPref.PreferenceKey.isCalledSkip, "0");
        AddSkipUserRepository skipRepo = new AddSkipUserRepositoryImpl(this);
        // Add custom implementation, as needed.
        try {
            SkipUser user = skipRepo.get(Cacheble.KEY_TEMP, RetrofitRequest.addSkippedUserParameters(
                    ERApp.restaurantId,
                    Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID),
                    Constant.android,
                    token,
                    "0",
                    token));
            Intent broadcastIntent = new Intent(Constant.ACTION_FIREBASE_TOKEN_RETRIVED);
            broadcastIntent.putExtra(Constant.FIREBASE_TOKEN, token);
            sendBroadcast(broadcastIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private boolean isLogIn() {
        return isLoggedIn.equals("1");
    }

    private boolean isSkipped() {
        return isSkipedCalled.equalsIgnoreCase("1");
    }
}