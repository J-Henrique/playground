package com.jhbb.kotlinflow.combinezipmerge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class CombineZipMergeViewModel : ViewModel() {

    private val isAuthenticated = MutableStateFlow(true)
    private val user = MutableStateFlow<User?>(null)
    private val posts = MutableStateFlow(emptyList<Post>())

    private val _profileState = MutableStateFlow<ProfileState?>(null)
    val profileState = _profileState.asStateFlow()

    private val flow1 = (1..10).asFlow().onEach { delay(1000) }
    private val flow2 = (10..20).asFlow().onEach { delay(300) }
    var numberString by mutableStateOf("")
        private set

    init {
        isAuthenticated.combine(user) { isAuthenticated, user ->
            if (isAuthenticated) user else null
        }.combine(posts) { user, posts ->
            user?.let {
                _profileState.value = _profileState.value?.copy(
                    profilePicUrl = user.profilePicUrl,
                    username = user.userName,
                    description = user.description,
                    posts = posts
                )
            }
        }.launchIn(viewModelScope)

//        flow1.zip(flow2) { number1, number2 ->
//            numberString += "($number1, $number2)\n"
//        }.launchIn(viewModelScope)

        merge(flow1, flow2).onEach {
            numberString += "$it\n"
        }.launchIn(viewModelScope)
    }
}