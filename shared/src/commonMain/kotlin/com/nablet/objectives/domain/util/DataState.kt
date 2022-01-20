package com.nablet.objectives.domain.util

import com.nablet.objectives.domain.model.GenericMessageInfo

sealed class DataState<T>
data class Loading(val loading: Boolean) : DataState<Nothing>()
data class Update<T>(val data: T) : DataState<T>()
data class Failure(val message: GenericMessageInfo.Builder) : DataState<Nothing>()
