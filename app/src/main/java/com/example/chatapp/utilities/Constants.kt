package com.example.chatapp.utilities

class Constants {
    companion object {
        var KEY_COLLECTION_USERS = "users"
        var KEY_NAME = "name"
        var KEY_EMAIL = "email"
        var KEY_PASSWORD = "password"
        var KEY_PREFERENCE_NAME = "ChatAppPreference"
        var KEY_IS_MAIN_CHAT = "isMainChat"
        var KEY_USER_ID = "userId"
        var KEY_IMAGE = "image"

        var KEY_IS_SIGNED_IN = "isSingedIn"
        var KEY_FCM_TOKEN = "fcmToken"
        var KEY_USER = "user"
        var KEY_COLLECTION_CHAT = "chat"
        var KEY_SENDER_ID = "senderId"
        var KEY_RECEIVER_ID = "receierId"
        var KEY_MESSAGE = "message"
        var KEY_TIMESTAMP = "timeStamp"
        var KEY_COLLECTION_CONVERSATION = "conversations"
        var KEY_SENDER_NAME = "senderName"
        var KEY_RECEIVER_NAME = "receiverName"
        var KEY_SENDER_IMAGE = "sanderImg"
        var KEY_RECEIVER_IMAGE = "receiverImg"
        var KEY_LAST_MESSAGE = "lastMessage"
        var KEY_AVAILABLE = "available"
        var REMOTE_MSG_AUTHORIZATION = "Authorization"
        var REMOTE_MSG_CONTENT_TYPE = "Content-Type"
        var REMOTE_MSG_DATA = "data"
        var REMOTE_MSG_REGISTRATION_IDS = "registration_ids"
        var remoteMsgHeaders: HashMap<String, String>? = null
    }

    fun getRemoteMsgHeaders(): HashMap<String, String>? {
        if (remoteMsgHeaders == null) {
            remoteMsgHeaders = java.util.HashMap()
            remoteMsgHeaders!![REMOTE_MSG_AUTHORIZATION] =
                "key=AAAAXc-oW3Q:APA91bFipeWVuaHgkfAX7KbsWC3_9etZajwD5k0ykUs7xKwULojtPDqVSK7ey7e0ZzdfZg94kPqSlluy6rqQ6lNpoe38xqqBJ64jpdzxULJjScs11_7FYKZvIRkRQaRoUqCX5FoA2UQ_"
            remoteMsgHeaders!![REMOTE_MSG_CONTENT_TYPE] =
                "application/json"
        }
        return remoteMsgHeaders
    }
}