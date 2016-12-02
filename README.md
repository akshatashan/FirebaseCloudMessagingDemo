## FirebaseCloudMessagingDemo for notification with data and click action

### Set up android app on firebase developer console

![Create a project and add an app using the firebase developer console](https://github.com/akshatashan/FirebaseCloudMessagingDemo/blob/master/screenshots/Firebase%20console%20App%20setup.png)

- Download the google-services.json file and place it in the app folder of the Android Project

- Note down the Firebase server key from the settings page of the firebase project.This will be used in the POST request to the Firebase Api
![Firebase server key](https://github.com/akshatashan/FirebaseCloudMessagingDemo/blob/master/screenshots/Server%20key.png)


### Android setup for Firebase Cloud Messaging 
- Add dependency to module level gradle file in dependencies
```sh
compile 'com.google.firebase:firebase-messaging:9.6.1'
```
- Apply plugin at the end of the module level gradle file 
```sh
apply plugin: 'com.google.gms.google-services'
```

- Define services in AndroidManifest.xml file
    - FirebaseInstanceIdService : called for initial generation of the token on app startup. You could extend this class and override the onTokenRefresh function.The onTokenRefresh is called when the token is no longer valid.
        ```sh
        <service
            android:name="com.google.firebase.iid.FirebaseInstanceIdService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT"/>
            </intent-filter>
        </service>
        ```
        Note: You could also create a custom class that extends com.google.firebase.iid.FirebaseInstanceIdService and override the onTokenRefresh function to get the refreshed token.

    - Define the NotificationDisplayActivity to be launched on the click of the received notification
        ```
        <activity
            android:name=".NotificationDisplayActivity"
            android:label="@string/title_activity_notification_display"
            android:theme="@style/AppTheme.NoActionBar">
            <intent-filter>
                <action android:name ="leapingwolf.firebasecloudmessaging.targetAction"/>
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
        ```

        The value used for the intent filter `leapingwolf.firebasecloudmessaging.targetAction`
        is used to define the click action of the push notification request.We will get to it shortly.

    * Define a custom service in the AndroidManifest.xml file to display notification and direct to a certain activity on click of the notification.This custom service extends com.google.firebase.messaging.FirebaseMessagingService and overrides onMessageReceived function.
        ```sh
        <service
            android:name=".MyFirebaseInstanceIDService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT"/>
            </intent-filter>
        </service>
        ```
    * Call sendNotification function from onMessageReceived. This function displays the notification and specifies a pending intent for the NotificationDisplayActivity.

        ```
        public void onMessageReceived(RemoteMessage remoteMessage) {
            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getData().get("user"),remoteMessage.getNotification().getClickAction())
        }
        ```
        ```
        private void sendNotification(String messageBody,String user, String clickAction) {
            Intent intent = new Intent(clickAction);
            intent.putExtra("user", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT);
        // more code to define a notification and display it.    
        }
        ```

    * In LauncherActivity, get the Firebase token for the android device
        ```sh
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        ```
 - Run the android app and get the value of the token in the Launcher Activity.This value uniquely identifies the android device.ex: ackfjajfal124fjd

### Sending a push notification to the android app.
I use Postman to show the structure of the json and the headers in the POST request to the Firebase Api.
- Set the `Authorization` header in the request to "key=<Firebase server key>(https://github.com/akshatashan/FirebaseCloudMessagingDemo/blob/master/screenshots/Server%20key.png)"
![Header](https://github.com/akshatashan/FirebaseCloudMessagingDemo/blob/master/screenshots/PostRequestHeaders.png)

- The request body is a json with the following structure
    * registration_ids - Identifies the android device that receives the push notification.Specify the value of the token obtained in the Launcher Activity here.ex: ackfjajfal124fjd
    * notification - defines a title and message body for the push notification. It also defines the `clickAction` - the activity to be launched on clicking the notification on the android device
    * Data - custom data sent as part of the request and used by the notification activity.
![Body](https://github.com/akshatashan/FirebaseCloudMessagingDemo/blob/master/screenshots/PostRequestBodyWithResult.png)
