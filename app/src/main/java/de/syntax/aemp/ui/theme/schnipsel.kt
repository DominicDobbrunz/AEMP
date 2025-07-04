package de.syntax.aemp.ui.theme

/* TODO für den DentalScreen
// Dann in der LazyColumn nur passende Geräte anzeigen:
val filteredDevices = if (selectedCategory == "Alle Geräte") {
    devices
} else {
    devices.filter { it.device.category == selectedCategory } // abhängig von deinem Datenmodell
}

TODO für den DentalDeviceList
// Geräte filtern
        val filteredDevices = if (searchText.isNotBlank()) {
            devices.filter {
                it.device.deviceName?.contains(searchText, ignoreCase = true) == true ||
                it.device.applicant?.contains(searchText, ignoreCase = true) == true
            }
        } else {
            devices
        }

        // Fehlermeldung
        if (error != null) {
            Text(error!!, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        // Geräte-Liste
        LazyColumn {
            items(filteredDevices) { deviceUi ->
                val isFavorite = favoritesViewModel.isFavorite(deviceUi.device)

                DeviceCard(
                    device = deviceUi.device,
                    isFavorite = isFavorite,
                    onFavClick = {
                        favoritesViewModel.toggleFavorite(DeviceUi(deviceUi.device, isFavorite))
                    }
                ) {
                    navController.navigate("detail/${deviceUi.device.kNumber}")
                }
            }

            item {
                // Lazy loading bei Scroll-Ende
                LaunchedEffect(devices.size) {
                    viewModel.loadDevices()
                }
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}

val devices by viewModel.devices.collectAsState()

val filteredDevices = if (searchText.isNotBlank()) {
    devices.filter {
        it.device.deviceName?.contains(searchText, ignoreCase = true) == true ||
        it.device.applicant?.contains(searchText, ignoreCase = true) == true
    }
} else {
    devices
}

TODO falls es noch gebraucht wird.
@Composable
fun SettingsScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel()
) {
    val user = Firebase.auth.currentUser
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    // Bild hochladen Launcher
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val uid = user?.uid ?: return@let
            StorageRepository.uploadProfileImage(uid, it) { url ->
                if (url != null) {
                    Firebase.firestore.collection("users").document(uid)
                        .update("profileImageUrl", url)
                } else {
                    Toast.makeText(context, "Fehler beim Hochladen", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Profil laden
    LaunchedEffect(true) {
        FirebaseRepository.getUserProfile {
            profile = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Header mit Profilbild und Menü
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Einstellungen",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = profile?.profileImageUrl ?: user?.photoUrl,
                    contentDescription = "Profilbild",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .clickable { launcher.launch("image/*") }
                )

                Spacer(Modifier.width(8.dp))

                // Dropdown-Menü
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Mehr", tint = Color.White)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profil bearbeiten") },
                            onClick = {
                                expanded = false
                                navController.navigate("editProfile")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Profilbild hinzufügen") },
                            onClick = {
                                expanded = false
                                launcher.launch("image/*")
                            }
                        )
                    }
                }
            }
        }

        // Profilinformationen
        profile?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Info von der Person und Praxis Anschrift", fontWeight = FontWeight.Medium, color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    Text("Name: ${it.firstName} ${it.lastName}", color = Color.White)
                    Text("Praxis: ${it.praxisName}", color = Color.White)
                    Text("Adresse: ${it.street}, ${it.postalCode} ${it.city}", color = Color.White)
                    Text("E-Mail: ${it.email}", color = Color.White)
                }
            }
        }

        // Einstellungen
        val settingsItems = listOf(
            "Allgemeine Einstellung" to {},
            "Account" to {},
            "Benachrichtigungen" to {},
            "Information" to {},
            "Support" to {},
            "Datenschutz" to {}
        )

        settingsItems.forEach { (label, onClick) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Spacer(Modifier.weight(1f))

        // E-Mail Verifikation
        if (user != null && !user.isEmailVerified) {
            Text("E-Mail nicht verifiziert!", color = Color.Red)
            Button(
                onClick = {
                    user.sendEmailVerification()
                    Toast.makeText(context, "Verifizierungslink gesendet", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("E-Mail verifizieren")
            }
        }

        // Logout Button
        Button(
            onClick = {
                Firebase.auth.signOut()
                navController.navigate("login") {
                    popUpTo("settings") { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}
 */