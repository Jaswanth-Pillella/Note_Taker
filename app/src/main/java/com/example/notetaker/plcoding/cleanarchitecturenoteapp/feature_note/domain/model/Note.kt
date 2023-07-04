package com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.ui.theme.BabyBlue
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.ui.theme.LightGreen
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.ui.theme.RedOrange
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.ui.theme.RedPink
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.ui.theme.Violet
import java.lang.Exception

@Entity
data class Note(
    val title:String,
    val content:String,
    val timeStamp:Long,
    val color:Int,
    @PrimaryKey
    val id:Int? = null
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteExecption(messsage:String):Exception(messsage)
