package com.jhbb.kotlinflow.combinezipmerge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn

class CombineZipMergeViewModel: ViewModel() {

    private val isAuthenticated = MutableStateFlow(true)
    private val user = MutableStateFlow<User?>(null)
    private val posts = MutableStateFlow(emptyList<Post>())

    private val _profileState = MutableStateFlow<ProfileState?>(null)
    val profileState = _profileState.asStateFlow()

    init {
        user.combine(posts) { user, posts ->
            _profileState.value = _profileState.value?.copy(
                profilePicUrl = user?.profilePicUrl,
                username = user?.userName,
                description = user?.description,
                posts = posts
            )
        }.launchIn(viewModelScope)
    }
}