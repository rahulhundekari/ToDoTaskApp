package com.example.ui.utils

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineDispatcherProvider @Inject constructor() {
    val ioDispatcher = Dispatchers.IO
    val defaultDispatcher = Dispatchers.Default
}