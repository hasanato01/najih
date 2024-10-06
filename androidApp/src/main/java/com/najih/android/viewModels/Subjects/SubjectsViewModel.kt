package com.najih.android.viewModels.Subjects

import GetSubjectsResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.najih.android.api.subjects.getRecordedSubjects
import com.najih.android.api.subjects.getStreamsSubjects
import com.najih.android.dataClasses.StreamsSubjects
import io.ktor.client.HttpClient

class SubjectsViewModel(private val httpClient: HttpClient) : ViewModel() {
    private val _recordedSubjects = mutableStateOf<List<GetSubjectsResponse>>(emptyList())
    val recordedSubjects: State<List<GetSubjectsResponse>> = _recordedSubjects

    private val _streamsSubjects = mutableStateOf<List<StreamsSubjects>>(emptyList())
    val streamsSubjects: State<List<StreamsSubjects>> = _streamsSubjects

    private val _groupByRecordedClass = mutableStateOf<Map<Int, List<GetSubjectsResponse>>>(emptyMap())
    val groupByRecordedClass: State<Map<Int, List<GetSubjectsResponse>>> = _groupByRecordedClass

    private val _groupByStreamsClass = mutableStateOf<Map<Int, List<StreamsSubjects>>>(emptyMap())
    val groupByStreamsClass: State<Map<Int, List<StreamsSubjects>>> = _groupByStreamsClass

    private val _level = mutableStateOf("Unknown Level")
    val level: State<String> = _level

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun fetchSubjects(type: String, endPoint: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (endPoint) {
                    "r_subjects" -> {
                        _recordedSubjects.value = getRecordedSubjects(httpClient, type, endPoint)
                        _groupByRecordedClass.value = _recordedSubjects.value.groupBy { it.classNumber }
                        _level.value = _recordedSubjects.value.firstOrNull()?.level?.en ?: "Unknown Level"
                    }
                    "t_subjects" -> {
                        _streamsSubjects.value = getStreamsSubjects(httpClient, type, endPoint)
                        _groupByStreamsClass.value = _streamsSubjects.value.groupBy { it.classNumber }
                        _level.value = _streamsSubjects.value.firstOrNull()?.level?.en ?: "Unknown Level"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching subjects"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
