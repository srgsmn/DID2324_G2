package it.polito.did.g2.shopdrop.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import it.polito.did.g2.shopdrop.MainViewModel
import it.polito.did.g2.shopdrop.ui.cst.home.CSTHomeScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.cstNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
){
    navigation(
        startDestination = Screens.CstHomeScreen.route,
        route = CST_ROUTE
    ){

        composable(route = Screens.CstHomeScreen.route){
            CSTHomeScreen(navController, viewModel)
        }

        composable(route = Screens.CstCartScreen.route){
            //TODO
            Text("CST CART SCREEN")
        }

        composable(route = Screens.CstProfileScreen.route){
            //TODO
            Text("CST PROFILE SCREEN")
        }

        composable(route = Screens.CstOrderDetailScreen.route){
            //TODO
            Text("CST ORDER DETAIL SCREEN")
        }

        composable(route = Screens.CstCameraScreen.route){
            //TODO
            Text("CST CAMERA SCREEN")
        }
    }
}