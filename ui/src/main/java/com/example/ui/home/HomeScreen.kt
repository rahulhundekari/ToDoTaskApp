package com.example.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.domain.model.Task
import com.example.ui.R
import com.example.ui.common.TopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: TaskState,
    snackBarHostState: SnackbarHostState,
    searchText: String,
    isSearching: Boolean,
    onSearchTextChange: (String) -> Unit,
    taskEvent: Flow<TaskEvent>,
    callLoadData: () -> Unit,
    todoTasks: List<Task>,
    addToDoTask: () -> Unit,
    onResult: Boolean?
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        callLoadData()

        onResult?.let { result ->

            if (!result) {
                snackBarHostState.showSnackbar(context.getString(R.string.failed_to_add_todo))
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopBar(stringResource(id = R.string.app_name))
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    addToDoTask()
                }) {
                Icon(
                    painterResource(R.drawable.ic_add),
                    stringResource(R.string.add_task)
                )
            }
        }
    ) { innerPadding ->

        if (state.isEmptyTodoTasks) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable {
                        openBottomSheet = true
                    }
                ) {
                    Image(
                        painterResource(R.drawable.ic_add_task),
                        stringResource(id = R.string.add_task),
                        modifier = Modifier,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        stringResource(R.string.press_the_button_to_add_a_todo_item),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = searchText,
                    onValueChange = onSearchTextChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = stringResource(R.string.search)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isSearching) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(todoTasks) { todoTask ->
                            ToDoItem(toDoTask = todoTask)
                        }
                    }
                }
            }


        }
    }


}


@Composable
fun ToDoItem(toDoTask: Task) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 6.dp),
            elevation = CardDefaults.elevatedCardElevation(5.dp),
        ) {
            Text(
                text = toDoTask.title,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}