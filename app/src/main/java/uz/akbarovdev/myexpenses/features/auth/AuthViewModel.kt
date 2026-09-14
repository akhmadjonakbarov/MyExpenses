package uz.akbarovdev.myexpenses.features.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uz.akbarovdev.myexpenses.features.dashboard.data.sync.CloudSyncRepository

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
    val showLogoutConfirmation: Boolean = false,
)

class AuthViewModel(
    private val cloudSyncRepository: CloudSyncRepository,
    private val applicationContext: Context,
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _state.update { it.copy(userEmail = user?.email) }
            if (user != null && !_state.value.isAuthenticated) {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }
                    runCatching { cloudSyncRepository.pushAllLocal() }
                        .onFailure { Log.e(TAG, "Cloud push failed", it) }
                    runCatching { cloudSyncRepository.pullAllRemote() }
                        .onFailure { Log.e(TAG, "Cloud pull failed", it) }
                    analytics.logEvent(
                        FirebaseAnalytics.Event.LOGIN,
                        Bundle().apply {
                            putString(
                                FirebaseAnalytics.Param.METHOD,
                                if (user.email != null) "password" else "google"
                            )
                        }
                    )
                    _state.update { it.copy(isLoading = false, isAuthenticated = true) }
                }
            } else if (user == null) {
                _state.update { it.copy(isAuthenticated = false, isLoading = false) }
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
                analytics.logEvent(
                    FirebaseAnalytics.Event.SIGN_UP,
                    Bundle().apply {
                        putString(FirebaseAnalytics.Param.METHOD, "password")
                    }
                )
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

    fun requestLogout() = _state.update { it.copy(showLogoutConfirmation = true) }

    fun dismissLogout() = _state.update { it.copy(showLogoutConfirmation = false) }

    fun confirmLogout() {
        viewModelScope.launch {
            _state.update { it.copy(showLogoutConfirmation = false) }
            runCatching { cloudSyncRepository.pushAllLocal() }
            runCatching {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                GoogleSignIn.getClient(applicationContext, gso).signOut().await()
            }
            runCatching { cloudSyncRepository.clearLocalData() }
            analytics.logEvent("logout", null)
            auth.signOut()
        }
    }

    private fun validate(email: String, password: String): Boolean {
        if (email.isBlank()) { _state.update { it.copy(error = "Email is required") }; return false }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { _state.update { it.copy(error = "Invalid email") }; return false }
        if (password.length < 6) { _state.update { it.copy(error = "Password must be at least 6 characters") }; return false }
        return true
    }
}
