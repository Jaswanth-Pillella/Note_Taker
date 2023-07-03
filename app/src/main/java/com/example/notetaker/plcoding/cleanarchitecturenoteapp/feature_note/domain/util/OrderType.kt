package com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.util

sealed class OrderType{
    object Ascending:OrderType()
    object Descending:OrderType()
}