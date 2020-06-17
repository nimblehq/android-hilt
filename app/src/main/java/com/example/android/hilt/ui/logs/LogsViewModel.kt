package com.example.android.hilt.ui.logs

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.android.hilt.datasource.model.Log
import com.example.android.hilt.ui.logs.usecase.GetInMemLogsUseCase

class LogsViewModelImpl @ViewModelInject constructor(
    private val getInMemLogsUseCase: GetInMemLogsUseCase
) : ViewModel() {

    private val _logs = MutableLiveData<List<Log>>()

    val logs: LiveData<List<Log>>
        get() = _logs

    fun getInMemLogs() {
        // Rotate the screen, you will observe that the ViewModel & UseCase are retained the same instances.
        android.util.Log.d("ViewModelHilt", "ViewModel: $this useCase: $getInMemLogsUseCase")
        getInMemLogsUseCase.execute {
            _logs.postValue(it)
        }
    }
}
