package com.project.notetaker.feature_note.domain.util

sealed class OrderType{
    object Ascending:OrderType()
    object Descending:OrderType()
}