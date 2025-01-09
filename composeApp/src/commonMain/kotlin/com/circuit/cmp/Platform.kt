package com.circuit.cmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform