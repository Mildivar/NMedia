package ru.netology.pusher

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.io.FileInputStream


fun main() {
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
//        .setDatabaseUrl(dbUrl)
        .build()

    FirebaseApp.initializeApp(options)

//    val token = constants.token
    val message = Message.builder()
        .putData("action", "POST")
        .putData("content", """{
          "userId": 1,
          "author": "Vasiliy",
          "postId": 2,
          "postAuthor": "Netology",
          "content": "Show a conversation in a notification Apply NotificationCompat.MessagingStyle to display sequential messages between any number of people. This is ideal for messaging apps because it provides a consistent layout for each message by handling the sender name and message text separately, and each message can be multiple lines long.
To add a new message, call addMessage(), passing the message text, the time received, and the sender name. You can also pass this information as a NotificationCompat.MessagingStyle.Message object."
        }""".trimIndent())
        .setToken(token)
        .build()

    FirebaseMessaging.getInstance().send(message)
}