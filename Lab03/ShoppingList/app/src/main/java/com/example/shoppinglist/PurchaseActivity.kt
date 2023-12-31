package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.ui.theme.ShoppingListTheme

// Per caricare dei valori demo
const val LOAD_DEMO_DATA = true

class MainActivity : ComponentActivity() {

    private val vm by viewModels<PurchaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se true carica 3 oggetti demo
        if(LOAD_DEMO_DATA){
            vm.addItem(PurchasableItem("Pane", ItemCategory.FORNO, true))
            vm.addItem(PurchasableItem("Focaccia", ItemCategory.FORNO, false))
            vm.addItem(PurchasableItem("Pesche", ItemCategory.ORTOFRUTTA, false))
            vm.addItem(PurchasableItem("Dentifricio", ItemCategory.CURA_PERSONA, false))
            vm.addItem(PurchasableItem("Filo interdentale", ItemCategory.CURA_PERSONA, false))
            vm.addItem(PurchasableItem("Collutorio", ItemCategory.CURA_PERSONA, false))
            vm.addItem(PurchasableItem("Sale lavastoviglie", ItemCategory.CURA_CASA, true))
            vm.addItem(PurchasableItem("Sapone per piatti", ItemCategory.CURA_CASA, false))
            vm.addItem(PurchasableItem("Detersivo vestiti", ItemCategory.CURA_CASA, false))
            vm.addItem(PurchasableItem("Bastoncini", ItemCategory.SURGELATI, false))
            vm.addItem(PurchasableItem("Pizza", ItemCategory.SURGELATI, false))
            vm.addItem(PurchasableItem("Gelato", ItemCategory.SURGELATI, false))
        }

        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme

                Navigation()
            }
        }
    }

    @Composable
    fun Navigation(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "ShoppingScreen"){
            composable("ComposingScreen") {
                ComposingScreen(navController = navController, vm)
            }
            composable("ShoppingScreen") {
                ShoppingScreen(navController = navController, vm)
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        Navigation(vm)
    }
}

 */