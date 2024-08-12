package com.example.ui.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : CoroutineDispatcherProvider {

    private val testDispatcher = UnconfinedTestDispatcher()

    override val ioDispatcher: CoroutineDispatcher
        get() = testDispatcher
    override val defaultDispatcher: CoroutineDispatcher
        get() = testDispatcher
    override val mainDispatcher: CoroutineDispatcher
        get() = testDispatcher

}