package com.example.myprofileapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myprofileapp.ui.EditProfileScreen
import com.example.myprofileapp.ui.ProfileScreen
import com.example.myprofileapp.ui.theme.ProfileTheme
import com.example.myprofileapp.viewmodel.ProfileViewModel

@Composable
@Preview
fun App(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileTheme(
        darkTheme = uiState.isDarkMode
    ) {
        // Surface mengecat seluruh layar dengan colorScheme.background,
        // jadi dark mode benar-benar gelap (tanpa ini latar jendela tetap putih).
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (uiState.isEditing) {
                EditProfileScreen(
                    uiState = uiState,
                    onSave = { name, bio -> viewModel.saveProfile(name, bio) },
                    onCancel = { viewModel.setEditing(false) }
                )
            } else {
                ProfileScreen(
                    uiState = uiState,
                    onEditClick = { viewModel.setEditing(true) },
                    onDarkModeChange = { viewModel.setDarkMode(it) }
                )
            }
        }
    }
}
