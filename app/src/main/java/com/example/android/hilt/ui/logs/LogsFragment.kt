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

package com.example.android.hilt.ui.logs

import analytics.AnalyticsService
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.android.hilt.R
import com.example.android.hilt.datasource.logs.LoggerDataSource
import com.example.android.hilt.datasource.model.Log
import com.example.android.hilt.di.qualifier.DatabaseLogger
import com.example.android.hilt.di.qualifier.InMemoryLogger
import com.example.android.hilt.navigator.LogsScreen
import com.example.android.hilt.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_logs.*
import javax.inject.Inject

/**
 * Fragment that displays the logs from either in-memory or database source.
 */
@AndroidEntryPoint
class LogsFragment : Fragment() {

    @InMemoryLogger
    @Inject lateinit var logger: LoggerDataSource

    @DatabaseLogger
    @Inject lateinit var dbLogger: LoggerDataSource

    @Inject lateinit var dateFormatter: DateFormatter

    @Inject lateinit var analyticsService: AnalyticsService

    private val logsViewModel: LogsViewModelImpl by viewModels()

    private val args: LogsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvLogs.setHasFixedSize(true)

        logsViewModel
            .logs
            .observe(viewLifecycleOwner, Observer { logs ->
                rvLogs.adapter = LogsViewAdapter(logs, dateFormatter)
            })

        when (args.logType) {
            LogsScreen.ALL_IN_MEM_LOGS -> logsViewModel.getInMemLogs()
            LogsScreen.ALL_IN_DB_LOGS -> getLogFrom(dbLogger)
        }

        analyticsTrack()
    }

    private fun getLogFrom(logger: LoggerDataSource) {
        logger.getAllLogs { logs ->
            rvLogs.adapter =
                LogsViewAdapter(
                    logs,
                    dateFormatter
                )
        }
    }

    private fun analyticsTrack() {
        // Notice the AnalyticsServiceImpl instances, they are created/destroyed per fragment lifecycle
        analyticsService.track(this::class.simpleName.toString())
    }
}

/**
 * RecyclerView adapter for the logs list.
 */
private class LogsViewAdapter(
    private val logsDataSet: List<Log>,
    private val daterFormatter: DateFormatter
) : RecyclerView.Adapter<LogsViewAdapter.LogsViewHolder>() {

    class LogsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.text_row_item, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.textView.text = "${log.msg}\n\t${daterFormatter.formatDate(log.timestamp)}"
    }
}
