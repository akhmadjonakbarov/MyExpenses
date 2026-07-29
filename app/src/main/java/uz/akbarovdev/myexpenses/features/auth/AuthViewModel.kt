package uz.akbarovdev.myexpenses.features.auth

import android.content.Intent
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthUiState(
    val isLoading: Boolean = true,
    val isAuthenticated: Boolean = false,
    val userEmail: String? = null,
    val error: String? = null,
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val displayName: String = "",
    val isRegisterMode: Boolean = false,
)

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    init {
        val currentUser = auth.currentUser
        _state.value = AuthUiState(
            isAuthenticated = currentUser != null,
            isLoading = false,
            userEmail = currentUser?.email,
        )

        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _state.update {
                it.copy(
                    isAuthenticated = user != null,
                    userEmail = user?.email,
                )
            }
        }
    }

    fun onEmailChange(email: String) = _state.update { it.copy(email = email, error = null) }
    fun onPasswordChange(password: String) = _state.update { it.copy(password = password, error = null) }
    fun onConfirmPasswordChange(password: String) = _state.update { it.copy(confirmPassword = password, error = null) }
    fun onDisplayNameChange(name: String) = _state.update { it.copy(displayName = name, error = null) }
    fun toggleMode() = _state.update { it.copy(isRegisterMode = !it.isRegisterMode, error = null) }
    fun dismissError() = _state.update { it.copy(error = null) }

    fun login() {
        val s = _state.value
        if (!validate(s.email, s.password)) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                auth.signInWithEmailAndPassword(s.email, s.password).await()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Login failed") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun register() {
        val s = _state.value
        if (!validate(s.email, s.password)) return
        if (s.password != s.confirmPassword) {
            _state.update { it.copy(error = "Passwords do not match") }
            return
        }
        if (s.displayName.isBlank()) {
            _state.update { it.copy(error = "Name is required") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val result = auth.createUserWithEmailAndPassword(s.email, s.password).await()
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(s.displayName).build()
                result.user?.updateProfile(profileUpdates)?.await()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Registration failed") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun handleGoogleSignInResult(data: Intent?) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                Log.d(TAG, "Google account received, idToken=${account.idToken != null}")
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).await()
                Log.d(TAG, "Firebase signInWithCredential succeeded, user=${auth.currentUser?.email}")
            } catch (e: Exception) {
                Log.e(TAG, "Google sign-in error", e)
                _state.update { it.copy(error = e.localizedMessage ?: "Google sign-in failed") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }

    fun logout() {
        auth.signOut()
    }

    private fun validate(email: String, password: String): Boolean {
        if (email.isBlank()) { _state.update { it.copy(error = "Email is required") }; return false }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { _state.update { it.copy(error = "Invalid email") }; return false }
        if (password.length < 6) { _state.update { it.copy(error = "Password must be at least 6 characters") }; return false }
        return true
    }
}
