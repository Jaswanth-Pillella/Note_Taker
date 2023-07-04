package com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NotesUseCases
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCase: NotesUseCases
) : ViewModel(){
    private val _state = mutableStateOf<NotesState>(NotesState())
    val state:State<NotesState> = _state

    private var recentelyDeletedNote:Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }
    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.Order->{
                if(state.value.noteOrder::class == event.noteOrder::class &&
                        state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote->{
                viewModelScope.launch {
                    noteUseCase.deleteNoteUseCase(event.note)
                    recentelyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote->{
                viewModelScope.launch {
                    noteUseCase.addNoteUseCase(recentelyDeletedNote?:return@launch)
                    recentelyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection->{
                _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCase.getNotesUseCase(noteOrder).
                onEach { notes ->
                    _state.value = state.value.copy(
                        notes = notes,
                        noteOrder = noteOrder
                    )
                }.launchIn(viewModelScope)
    }
}