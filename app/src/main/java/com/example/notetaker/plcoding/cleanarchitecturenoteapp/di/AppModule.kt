package com.example.notetaker.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.AddNoteUseCase
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.DeleteNoteUseCase
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.GetNoteUseCase
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.GetNotesUseCase
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,NoteDatabase::class.java,NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db:NoteDatabase):NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun providesNotesUseCase(repository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUeCase(repository)
        )
    }
}
