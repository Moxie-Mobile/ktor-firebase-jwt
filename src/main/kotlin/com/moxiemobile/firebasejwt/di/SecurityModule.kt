package com.moxiemobile.firebasejwt.di

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import io.ktor.server.application.*
import org.koin.dsl.module
import java.io.ByteArrayInputStream

fun securityModule(
    environment: ApplicationEnvironment
) = module {
    single {
        val firebaseConfig = environment.config.property("firebase.config").getString()

        val options = FirebaseOptions.builder()
            .setCredentials(
                GoogleCredentials.fromStream(
                    ByteArrayInputStream(firebaseConfig.toByteArray(Charsets.UTF_8))
                )
            )
            .build()

        FirebaseApp.initializeApp(options)
        FirebaseAuth.getInstance()
    }
}
