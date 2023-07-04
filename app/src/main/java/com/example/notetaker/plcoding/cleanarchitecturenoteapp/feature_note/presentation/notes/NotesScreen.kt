package com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NoteItem
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NotesEvent
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NotesViewModel
import com.example.notetaker.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.OrderSection
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()// scaffoldstate function is not available in material3
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

            },
               backgroundColor = MaterialTheme.colorScheme.primary
            ) {
               Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            },
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your note",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                          viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(imageVector = Icons.Default.Sort,
                        contentDescription = "Sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
               OrderSection(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(vertical = 16.dp),
                   noteOrder = state.noteOrder,
                   onOrderChange = {
                       viewModel.onEvent(NotesEvent.Order(it))
                   }
               )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.notes){note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note Deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
/*https://stackoverflow.com/questions/71363542/not-able-to-use-rememberscaffoldstate-in-android-compose-material3
Seems like it's not going to get [fixed] :((issuetracker.google.com/issues/185097403) "We decided to explicitly remove drawer in material3's scaffold. The reason being that Scaffolds are usually used on the per screen or per set of screens basis, where drawer is usually a app-wide entity. They are at different layers and should not be mixed. you can easily add a drawer by wrapping it your scaffold with NavigationDrawer component." –
fuomag9
Apr 24, 2022 at 23:25*/
