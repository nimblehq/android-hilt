package com.example.android.hilt.ui.logs.usecase

import com.example.android.hilt.datasource.logs.LoggerDataSource
import com.example.android.hilt.datasource.model.Log
import com.example.android.hilt.di.qualifier.InMemoryLogger
import javax.inject.Inject

class GetInMemLogsUseCase @Inject constructor(
    @InMemoryLogger val loggerDataSource: LoggerDataSource
) {
    fun execute(callback: (List<Log>) -> Unit) {
        // Rotate the screen, you will see loggerDataSource will be persisted because they are scoped to
        // ActivityRetainedScoped,
        // Also note: if you go back and forth, the same DataSource is still survived, because the VM is
        // still tied to the Activity's Scope.
        android.util.Log.d("UseCaseHilt", "DataSource: $loggerDataSource")
        return loggerDataSource.getAllLogs(callback)
    }
}
