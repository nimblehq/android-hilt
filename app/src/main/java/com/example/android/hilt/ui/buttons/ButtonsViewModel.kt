package com.example.android.hilt.ui.buttons

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.android.hilt.ui.buttons.usecase.AddLogUseCase
import com.example.android.hilt.ui.buttons.usecase.AddLogUseCase.Input
import com.example.android.hilt.ui.buttons.usecase.RemoveLogUseCase
import com.example.android.hilt.ui.buttons.usecase.RemoveLogUseCase.TYPE

class ButtonsViewModel @ViewModelInject constructor(
    private val addLogUseCase: AddLogUseCase,
    private val removeLogUseCase: RemoveLogUseCase
) : ViewModel() {

    fun addInMemLog(log: String) = addLogUseCase.execute(Input.InMemLog(log))

    fun addDbLog(log: String) = addLogUseCase.execute(Input.DbLog(log))

    fun removeInMemberLogs() = removeLogUseCase.execute(TYPE.IN_MEM)

    fun removeDbLogs() = removeLogUseCase.execute(TYPE.DATABASE)
}
