package com.example.android.hilt.ui.buttons.usecase

import com.example.android.hilt.datasource.logs.LoggerDataSource
import com.example.android.hilt.di.qualifier.DatabaseLogger
import com.example.android.hilt.di.qualifier.InMemoryLogger
import javax.inject.Inject

class RemoveLogUseCase @Inject constructor(
    @InMemoryLogger val inMemoryLogger: LoggerDataSource,
    @DatabaseLogger val databaseLogger: LoggerDataSource
) {

    enum class TYPE {
        IN_MEM,
        DATABASE
    }

    fun execute(logType: TYPE) {
        when (logType) {
            TYPE.IN_MEM -> inMemoryLogger.removeLogs()
            TYPE.DATABASE -> databaseLogger.removeLogs()
        }
    }
}
