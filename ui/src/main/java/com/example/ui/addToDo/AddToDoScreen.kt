package com.example.ui.addToDo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddToDoScreen(
    state: AddToDoState,
    addToDoEvent: Flow<AddToDoEvent>,
    onEvent: (AddToDOUiEvent) -> Unit,
    onResult: (Boolean) -> Unit,
    onGoBack: () -> Unit
) {

    val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit) {

        addToDoEvent.collect { event ->
            when (event) {
                is AddToDoEvent.OnResult -> {
                    onResult(event.success)
                }
            }

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Add TODO Screen",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onGoBack()
                            }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .padding(8.dp),
                        )
                    }
                },
            )
        }
    ) { innerPadding ->


        Box(
            Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .navigationBarsPadding()
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = state.toDoText,
                    onValueChange = {
                        onEvent(AddToDOUiEvent.OnEditText(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(56.dp))

                Button(
                    onClick = {
                        localSoftwareKeyboardController?.hide()
                        onEvent(AddToDOUiEvent.OnAddToDo)
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    if (state.isLoadingAddTask) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    } else {
                        Text(stringResource(R.string.add_todo))
                    }
                }
            }
        }
    }

}