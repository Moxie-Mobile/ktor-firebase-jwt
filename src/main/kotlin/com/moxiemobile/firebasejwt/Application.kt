package com.moxiemobile.firebasejwt

import com.google.firebase.auth.FirebaseAuth
import com.moxiemobile.firebasejwt.di.securityModule
import com.moxiemobile.firebasejwt.jwt.FirebaseJWTVerifier
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

private const val AUTH_JWT = "auth-jwt"

fun Application.module() {
//    configureRouting()
    install(Koin) {
        slf4jLogger(org.koin.core.logger.Level.INFO)
        modules(securityModule(environment))
    }

    install(ContentNegotiation) {
        json()
    }

    val firebaseAuth by inject<FirebaseAuth>()
    val audience = "sample-app"

    install(Authentication) {
        jwt(AUTH_JWT) {
            verifier(FirebaseJWTVerifier(firebaseAuth, audience))
            validate { credential -> JWTPrincipal(credential.payload) }
            challenge { _, _ -> call.respond(HttpStatusCode.Unauthorized, "Invalid token") }
        }
    }

    routing {
        authenticate(AUTH_JWT) {
            get("/secured") {
                call.respondText("Hello, Secure World!")
            }
        }
        get("/") {
            call.respondText("Hello, World!")
        }
    }
}
