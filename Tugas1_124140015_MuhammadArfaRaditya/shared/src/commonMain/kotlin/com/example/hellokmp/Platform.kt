package com.example.hellokmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform