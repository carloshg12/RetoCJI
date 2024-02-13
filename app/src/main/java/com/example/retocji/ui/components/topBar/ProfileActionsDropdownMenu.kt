package com.example.retocji.ui.components.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retocji.ui.viewmodels.UserViewModel

@Composable
fun ProfileActionsDropdownMenu(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    onShowDialogChange: (Boolean) -> Unit,
    navController: NavController,
    userViewModel: UserViewModel
) {

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onShowDialogChange(true) }
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("   Citas    ")
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = "Citas"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                navController.navigate("LogIn")
                userViewModel.exit()
            }
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("   Salir     ")
            Icon(
                imageVector = Icons.Outlined.ExitToApp,
                contentDescription = "Salir"
            )
        }
    }
}