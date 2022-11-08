@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nef.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent

fun <T> LiveData<T>.observeForTesting(
    testScope: TestScope,
    pause: Boolean = false,
    block: (LiveData<T>) -> Unit
) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        if (!pause) {
            testScope.runCurrent()
        }
        block(this)
    } finally {
        removeObserver(observer)
    }
}