package com.nablet.mytasks.domain.util

import com.nablet.mytasks.domain.model.GenericMessageInfo

sealed class DataState<T>
data class Loading(val loading: Boolean) : DataState<Nothing>()
data class Update<T>(val data: T) : DataState<T>()
data class Error(val message: GenericMessageInfo.Builder) : DataState<Nothing>()
