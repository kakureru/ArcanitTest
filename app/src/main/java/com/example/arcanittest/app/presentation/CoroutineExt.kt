package com.example.arcanittest.app.presentation

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

fun LifecycleOwner.collectFlowSafely(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collect: suspend () -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            collect()
        }
    }
}

inline fun <R> runCatchingNonCancellation(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Log.e("EXCEPTION", "Exception", e)
        Result.failure(e)
    }
}