package uz.akbarovdev.myexpenses.features.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { Firebase.auth }
    single { FirebaseFirestore.getInstance() }
    viewModel { AuthViewModel(get(), androidApplication()) }
}
