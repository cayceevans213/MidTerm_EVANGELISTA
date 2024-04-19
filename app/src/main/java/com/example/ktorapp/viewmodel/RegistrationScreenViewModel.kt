package com.example.ktorapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ktorapp.data.Repository.UserRepository
import com.example.ktorapp.data.offlineRepository.OfflineUserRepository
import com.example.ktorapp.data.onlineRepository.OnlineUserRepository
import com.example.ktorapp.model.UserProfile
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class RegistrationScreenViewModel(private val onlineUserRepository: OnlineUserRepository, private val offlineUserRepository: OfflineUserRepository) : ViewModel() {
    /**
     * Holds current user ui state
     */
    var userUiState by mutableStateOf(UserUiState())
        private set
    /**
     * Updates the [userUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(userDetails: UserDetails) {
        userUiState =
            UserUiState(userDetails = userDetails, isEntryValid = validateInput(userDetails))
    }
    /**
     * Inserts a [User] in the Room database
     */
    suspend fun saveUser() {
        if (validateInput()) {
            onlineUserRepository.insertUser(userUiState.userDetails.toUser())
            offlineUserRepository.insertUser(userUiState.userDetails.toUser())
        }
    }
    private fun validateInput(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            username.isNotBlank() && password.isNotBlank()
        }
    }
    suspend fun selectUser(username:String) : Flow<UserProfile?>? {
        //return usersRepository.getUserStream(username)
        var flow : Flow<UserProfile?>? = null

        //flow = usersRepository.getUserPasswordStream(userDetails.username, userDetails.password)
        try {
            flow = onlineUserRepository.getUserStream(username);offlineUserRepository.getUserStream(username)

        } catch (e: Exception){
            Timber.i("SAMPLE $e")
        }
        return flow
    }
}
