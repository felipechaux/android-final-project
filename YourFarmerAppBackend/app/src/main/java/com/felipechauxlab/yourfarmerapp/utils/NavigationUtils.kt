package com.felipechauxlab.yourfarmerapp.utils

import androidx.navigation.NavController

object NavigationUtils {
    fun closeDialog(navController: NavController, dialogId: Int) {
        if (navController.currentDestination == navController.graph.findNode(dialogId)) {
            navController.navigateUp()
        }
    }
}