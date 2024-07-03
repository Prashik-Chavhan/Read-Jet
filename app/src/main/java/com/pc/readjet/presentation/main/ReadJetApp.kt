package com.pc.readjet.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pc.readjet.presentation.AppNavigation
import com.pc.readjet.presentation.bottomNavigationItems
import com.pc.readjet.presentation.components.TopAppBar

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomBarRoutes = setOf(
    "home", "search"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadJetApp(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior)},
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                NavigationBar {
                    bottomNavigationItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(text = item.label)},
                            selected = currentRoute == item.route,
                            onClick = {
                                      if (currentRoute != item.route) {
                                          navController.navigate(item.route) {
                                              popUpTo(navController.graph.startDestinationId)
                                              launchSingleTop
                                          }
                                      }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}