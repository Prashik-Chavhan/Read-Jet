package com.pc.readjet.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pc.readjet.presentation.detailScreen.DetailScreen
import com.pc.readjet.presentation.main.NavigationItem
import com.pc.readjet.presentation.home_screen.HomeScreen
import com.pc.readjet.presentation.main.MainViewModel
import com.pc.readjet.presentation.search_screen.SearchScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val viewModel: MainViewModel = hiltViewModel()
    val argKey = "web_url"

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {

            HomeScreen(
                state = viewModel.categoryNews,
                onEvent = viewModel::onEvent,
                onNewsCardClicked = {url ->
                    navController.navigate("detailScreen?$argKey=$url")
                },
                modifier = modifier
                    .fillMaxSize()
            )
        }
        composable("search") {
            SearchScreen(
                state = viewModel.searchNews,
                onEvent = viewModel::onEvent,
                onNewsCardClicked = { url ->
                    navController.navigate("detailScreen?$argKey=$url")
                },
                modifier = modifier.fillMaxSize()
            )
        }
        composable("detailScreen?$argKey={$argKey}",
            arguments = listOf(navArgument(name = argKey) {
                type = NavType.StringType
            })
        ) {backStackEntry ->
            DetailScreen(
                url = backStackEntry.arguments?.getString(argKey),
            )
        }
    }
}

val bottomNavigationItems = listOf(
    NavigationItem(route = "home", label = "Home", icon = Icons.Filled.Home),
    NavigationItem(route = "search", label = "Search", icon = Icons.Filled.Search)
)