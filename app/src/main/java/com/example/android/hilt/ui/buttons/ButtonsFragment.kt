/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt.ui.buttons

import analytics.AnalyticsService
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.hilt.R
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.LogsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_buttons.*
import javax.inject.Inject

/**
 * Fragment that displays buttons whose interactions are recorded.
 */
@AndroidEntryPoint
class ButtonsFragment : Fragment() {

    @Inject lateinit var navigator: AppNavigator

    @Inject lateinit var analyticsService: AnalyticsService

    private val viewModel: ButtonsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buttons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btMemory1.setOnClickListener { viewModel.addInMemLog("Tapped on 'Button 1'") }
        btMemory2.setOnClickListener { viewModel.addInMemLog("Tapped on 'Button 2'")}
        btMemory3.setOnClickListener { viewModel.addInMemLog("Tapped on 'Button 3'") }
        btAllLogsInMem.setOnClickListener { navigator.navigateToAllLogs(LogsScreen.ALL_IN_MEM_LOGS) }
        btClearMemLogs.setOnClickListener { viewModel.removeInMemberLogs() }

        btDb1.setOnClickListener { viewModel.addDbLog("Tapped on 'Button 1'") }
        btDb2.setOnClickListener { viewModel.addDbLog("Tapped on 'Button 2'")}
        btDb3.setOnClickListener { viewModel.addDbLog("Tapped on 'Button 3'") }
        btAllLogsInDb.setOnClickListener { navigator.navigateToAllLogs(LogsScreen.ALL_IN_DB_LOGS) }
        btClearDbLogs.setOnClickListener { viewModel.removeDbLogs() }

        analyticsTrack()
    }

    private fun analyticsTrack() {
        analyticsService.track(this::class.simpleName.toString())
    }
}
