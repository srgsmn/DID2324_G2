package it.polito.did.g2.shopdrop

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import it.polito.did.g2.shopdrop.navigation.Nav
import it.polito.did.g2.shopdrop.ui.theme.ShopDropTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var loginSP: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MAoCr", "## STARTS HERE ##")
        super.onCreate(savedInstanceState)

        //viewModel.debugInit()

        Log.i("MAoCr", "\tGetting shared refs at 'shopdrop.login_info'")
        loginSP = getSharedPreferences("shopdrop.login_info", Context.MODE_PRIVATE)


        installSplashScreen()
            .apply{//Prima di setContent per avviare splashscreen

            this.setKeepOnScreenCondition{
                viewModel.isLoading.value
            }

            viewModel.setLoginSP(loginSP)
            viewModel.loadCredentials()
        }

        setContent {
            ShopDropTheme {
                Nav(viewModel, this@MainActivity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //viewModel.debugSetDefault()
    }
}

/*
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(viewModel: MainViewModel){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "CustomerHome"
        /*
        if(viewModel.loadUserInfo()!=null){
            "CSTHomeScreen"
        }else{
            "Login"
        }
        */
    ){
        composable("Login") {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable("CustomerHome") {
            CSTHomeScreen(navController = navController, viewModel = viewModel)
        }
        composable("CarrierHome") {
            CarrierHomeScreen(navController = navController)
        }
        composable("ClientCart"){
            CartScreen(navController = navController, viewModel = viewModel)
        }
        composable("CartSummary"){
            CartSummary(navController = navController, viewModel = viewModel)
        }

        composable("OrderConfirmed"){
            OrderConfirmed(navController = navController)
        }

        composable("ClientProfile"){
            CSTProfileScreen(navController = navController, viewModel)
        }
        composable(
            "CategorySection/{sectionName}",
            arguments = listOf(
                navArgument(name = "sectionName"){
                    type = NavType.StringType
                }
            )
            ){backstackEntry ->
            CategorySection(navController = navController, viewModel = viewModel, sectionName = enumValueOf<StoreItemCategory>(backstackEntry.arguments?.getString("sectionName")?:""))
        }
        composable("COrderDetailScreen"){
            COrderDetailScreen(navController = navController, vm = viewModel)
        }
        composable("COrderListScreen"){
            COrderListScreen(navController = navController, viewModel)
        }
        composable("CameraScreen"){
            CameraScreen(navController = navController, viewModel)
        }

        composable("UnlockScreen"){
            UnlockScreen(navController = navController)
        }

        composable("OpeningScreens"){
            OpeningScreens(navController, viewModel)
        }
    }
}
 */