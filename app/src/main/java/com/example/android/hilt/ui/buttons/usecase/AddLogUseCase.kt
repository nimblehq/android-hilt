package com.example.android.hilt.ui.buttons.usecase

import com.example.android.hilt.datasource.logs.LoggerDataSource
import com.example.android.hilt.di.qualifier.DatabaseLogger
import com.example.android.hilt.di.qualifier.InMemoryLogger
import javax.inject.Inject

class AddLogUseCase @Inject constructor(
    @InMemoryLogger val inMemoryLogger: LoggerDataSource,
    @DatabaseLogger val databaseLogger: LoggerDataSource
) {
    sealed class Input(
        open val log: String
    ) {
        data class InMemLog(
            override val log: String
        ): Input(log)

        data class DbLog(
            override val log: String
        ): Input(log)
    }

    fun execute(input: Input) {
        when (input) {
            is Input.InMemLog -> inMemoryLogger.addLog(input.log)
            is Input.DbLog -> databaseLogger.addLog(input.log)
        }
    }
}
