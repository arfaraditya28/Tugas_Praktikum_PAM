package com.example.myprofileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.ProfileUiState
import com.example.myprofileapp.ui.components.ProfileTextField

/**
 * Layar form edit nama & bio.
 * State hoisting: draft disimpan di sini (remember),
 * [ProfileTextField] murni stateless (value + onValueChange).
 */
@Composable
fun EditProfileScreen(
    uiState: ProfileUiState,
    onSave: (name: String, bio: String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nameDraft by remember(uiState.name) { mutableStateOf(uiState.name) }
    var bioDraft by remember(uiState.bio) { mutableStateOf(uiState.bio) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        ProfileTextField(
            value = nameDraft,
            onValueChange = { nameDraft = it },
            label = "Nama",
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        ProfileTextField(
            value = bioDraft,
            onValueChange = { bioDraft = it },
            label = "Bio",
            minLines = 3,
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSave(nameDraft, bioDraft) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(8.dp))
        // FilledTonalButton agar tetap jelas di dark mode
        // (OutlinedButton terlihat samar di dark mode).
        FilledTonalButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
