package com.example.arcanittest.app.presentation.adapter

interface DelegateItem {
    fun content(): Any
    fun id(): Long
    fun compareToOther(other: DelegateItem): Boolean
}