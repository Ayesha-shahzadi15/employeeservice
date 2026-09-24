package com.app.employeeservice.ui.screens.profile

import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Work

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.AsyncImage


private val APPGreen = Color(0xFF075B32)
private val LightGreen = Color(0xFFF1F8F4)


@Composable
fun ProfileScreen(
    onBackClick: () -> Unit
) {

    var isEditing by remember {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf("Ayesha Shahzadi")
    }

    var employeeId by remember {
        mutableStateOf("APP-1023")
    }

    var designation by remember {
        mutableStateOf("Software Engineer")
    }

    var department by remember {
        mutableStateOf("Information Technology")
    }

    var email by remember {
        mutableStateOf("employee@app.com")
    }

    var phone by remember {
        mutableStateOf("+92 XXX XXXXXXX")
    }

    var joiningDate by remember {
        mutableStateOf("01 January 2024")
    }

    var status by remember {
        mutableStateOf("Active")
    }

    var profileImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                profileImageUri = uri
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF9))
    ) {

        // TOP BAR
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = APPGreen
                    )
                }

                Text(
                    text = "My Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = if (isEditing) "Cancel" else "Edit",
                    color = APPGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            isEditing = !isEditing
                        }
                        .padding(12.dp)
                )
            }
        }

        // CONTENT
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 18.dp,
                bottom = 30.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // PROFILE HEADER
            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = APPGreen
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ProfileImage(
                            imageUri = profileImageUri,
                            isEditing = isEditing,
                            onImageClick = {
                                imagePickerLauncher.launch("image/*")
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = name,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = designation,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.16f)
                        ) {

                            Text(
                                text = "Employee ID: $employeeId",
                                color = Color.White,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 6.dp
                                )
                            )
                        }
                    }
                }
            }

            // PERSONAL INFORMATION
            item {

                ProfileSection(
                    title = "Personal Information"
                ) {

                    EditableProfileField(
                        icon = Icons.Default.Person,
                        label = "Full Name",
                        value = name,
                        isEditing = isEditing,
                        onValueChange = {
                            name = it
                        }
                    )

                    EditableProfileField(
                        icon = Icons.Default.Person,
                        label = "Employee ID",
                        value = employeeId,
                        isEditing = isEditing,
                        onValueChange = {
                            employeeId = it
                        }
                    )

                    EditableProfileField(
                        icon = Icons.Default.Work,
                        label = "Designation",
                        value = designation,
                        isEditing = isEditing,
                        onValueChange = {
                            designation = it
                        }
                    )

                    EditableProfileField(
                        icon = Icons.Default.Work,
                        label = "Department",
                        value = department,
                        isEditing = isEditing,
                        onValueChange = {
                            department = it
                        }
                    )
                }
            }

            // CONTACT INFORMATION
            item {

                ProfileSection(
                    title = "Contact Information"
                ) {

                    EditableProfileField(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = email,
                        isEditing = isEditing,
                        onValueChange = {
                            email = it
                        }
                    )

                    EditableProfileField(
                        icon = Icons.Default.Phone,
                        label = "Phone",
                        value = phone,
                        isEditing = isEditing,
                        onValueChange = {
                            phone = it
                        }
                    )
                }
            }

            // EMPLOYMENT INFORMATION
            item {

                ProfileSection(
                    title = "Employment Information"
                ) {

                    EditableProfileField(
                        icon = Icons.Default.CalendarMonth,
                        label = "Joining Date",
                        value = joiningDate,
                        isEditing = isEditing,
                        onValueChange = {
                            joiningDate = it
                        }
                    )

                    EditableProfileField(
                        icon = Icons.Default.Work,
                        label = "Employment Status",
                        value = status,
                        isEditing = isEditing,
                        onValueChange = {
                            status = it
                        }
                    )
                }
            }

            // SAVE BUTTON
            if (isEditing) {

                item {

                    Button(
                        onClick = {
                            isEditing = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = APPGreen
                        )
                    ) {

                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save"
                        )

                        Spacer(
                            modifier = Modifier.size(8.dp)
                        )

                        Text(
                            text = "Save Changes",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


// =========================================================
// PROFILE IMAGE
// =========================================================

@Composable
private fun ProfileImage(
    imageUri: Uri?,
    isEditing: Boolean,
    onImageClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(105.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable(
                enabled = isEditing,
                onClick = onImageClick
            ),
        contentAlignment = Alignment.Center
    ) {

        if (imageUri != null) {

            AsyncImage(
                model = imageUri,
                contentDescription = "Profile Photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

        } else {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Photo",
                tint = APPGreen,
                modifier = Modifier.size(60.dp)
            )
        }

        if (isEditing) {

            Surface(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.BottomEnd),
                shape = CircleShape,
                color = APPGreen
            ) {

                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Change Photo",
                    tint = Color.White,
                    modifier = Modifier.padding(9.dp)
                )
            }
        }
    }
}


// =========================================================
// PROFILE SECTION
// =========================================================

@Composable
private fun ProfileSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                color = APPGreen,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            content()
        }
    }
}


// =========================================================
// EDITABLE FIELD
// =========================================================

@Composable
private fun EditableProfileField(
    icon: ImageVector,
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit
) {

    if (isEditing) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            label = {
                Text(label)
            },
            leadingIcon = {

                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = APPGreen
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )

    } else {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(10.dp),
                color = LightGreen
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = APPGreen,
                    modifier = Modifier.padding(9.dp)
                )
            }

            Spacer(
                modifier = Modifier.size(12.dp)
            )

            Column {

                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}