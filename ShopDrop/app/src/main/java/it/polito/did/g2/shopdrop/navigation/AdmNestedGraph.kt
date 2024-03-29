package it.polito.did.g2.shopdrop.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import it.polito.did.g2.shopdrop.MainViewModel
import it.polito.did.g2.shopdrop.ui.adm.ADMCameraTest
import it.polito.did.g2.shopdrop.ui.adm.ADMHomeScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.admNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    lifecycleOwner: LifecycleOwner
){
    navigation(
        startDestination = Screens.AdmHomeScreen.route,
        route = ADM_ROUTE
    ){
        composable(route = Screens.AdmHomeScreen.route){
            ADMHomeScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screens.AdmCameraTest.route){
            ADMCameraTest(navController = navController, viewModel = viewModel)
        }

        /*
        composable(route = Screens.AdmCollectionTest.route){
            CollectionProcedure(navController, viewModel)
        }
         */
    }
}