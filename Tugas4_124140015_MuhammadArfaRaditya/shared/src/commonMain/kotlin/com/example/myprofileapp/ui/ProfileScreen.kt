package com.example.myprofileapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.ProfileUiState
import myprofileapp.shared.generated.resources.Res
import myprofileapp.shared.generated.resources.compose_multiplatform
import myprofileapp.shared.generated.resources.foto_profile
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileHeader(name: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(182.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.foto_profile),
                contentDescription = "Foto Profil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(170.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProfileCard(email: String, phone: String, location: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            InfoItem(
                label = "Email",
                value = email
            )
            InfoItem(
                label = "Phone",
                value = phone
            )
            InfoItem(
                label = "Location",
                value = location
            )
        }
    }
}

// ---- ProfileScreen (Reactive terhadap ProfileUiState) ----

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onEditClick: () -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Toggle Light / Dark Mode — state berasal dari ViewModel
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (uiState.isDarkMode) "Dark Mode" else "Light Mode",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = uiState.isDarkMode,
                onCheckedChange = onDarkModeChange
            )
        }

        ProfileHeader(
            name = uiState.name
        )
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = uiState.bio,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProfileCard(
            email = uiState.email,
            phone = uiState.phone,
            location = uiState.location
        )
        Spacer(modifier = Modifier.height(16.dp))
        // FilledTonalButton: kontras aman di light & dark mode
        // (OutlinedButton terlihat samar di dark mode).
        FilledTonalButton(
            onClick = onEditClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Profile")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Contact Me")
        }
    }
}
