package com.example.arcanittest.app.presentation.screens.search.model

interface DelegateItem {
    fun content(): Any
    fun id(): Long
    fun compareToOther(other: DelegateItem): Boolean
}