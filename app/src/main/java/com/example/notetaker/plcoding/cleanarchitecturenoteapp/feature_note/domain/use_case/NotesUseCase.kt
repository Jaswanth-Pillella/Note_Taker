package com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

data class NotesUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase
)
