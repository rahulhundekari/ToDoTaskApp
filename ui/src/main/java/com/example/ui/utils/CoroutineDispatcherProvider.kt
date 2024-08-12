package com.example.ui.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface CoroutineDispatcherProvider {
    val ioDispatcher: CoroutineDispatcher
    val defaultDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
}

internal class DefaultCoroutineDispatcherProvider @Inject constructor() : CoroutineDispatcherProvider {
    override val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO
    override val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default
    override val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

}