package com.example.ui.addToDo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R

@Composable
fun AddToDoScreen(
    isLoadingAddTask: Boolean,
    onAddToDo: (String) -> Unit
) {

    val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
    val todoText = remember {
        mutableStateOf("")
    }

    Box(
        Modifier
            .fillMaxWidth()
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
                value = todoText.value,
                onValueChange = { todoText.value = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(56.dp))

            Button(
                onClick = {
                    localSoftwareKeyboardController?.hide()
                    onAddToDo(todoText.value)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                if (isLoadingAddTask) {
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