package com.project.notetaker.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.notetaker.ui.theme.Green
import com.project.notetaker.ui.theme.LightGreen
import com.project.notetaker.ui.theme.RedOrange
import com.project.notetaker.ui.theme.Blue
import com.project.notetaker.ui.theme.Violet
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
        val noteColors = listOf(RedOrange, LightGreen, Violet, Green, Blue)
    }
}

class InvalidNoteException(message:String):Exception(message)
