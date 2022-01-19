package com.nablet.objectives

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}