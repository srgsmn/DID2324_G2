package it.polito.did.s306067.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.polito.did.s306067.lab04.ui.theme.Lab04Theme

val IS_DEBUG = false

class MainActivity : ComponentActivity() {

    val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //vm.startConnection()

        setContent {
            Lab04Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContenitoreDatiTest(vm)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenitoreDatiTest(vm : MainViewModel){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val test by vm.testText.observeAsState()
    val nickname by vm.nickname.observeAsState()
    val email by vm.email.observeAsState()
    val open by vm.open.observeAsState()

    if(IS_DEBUG){
        DebugScreen(vm)
    }else{
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            floatingActionButton = { AddButton(onClick = {/*TODO*/}) },
            floatingActionButtonPosition = FabPosition.End
        ) {
                paddingValues ->

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(){
                    Row(){
                        Text("Welcome back,\n$nickname!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            lineHeight = 44.sp
                        )
                    }
                    Row(modifier = Modifier.padding(vertical = 8.dp)){
                        Text("${vm.getUID()} – $email",
                            fontSize = 12.sp,
                            color = Color(127, 127, 127)
                        )
                    }
                    Row(modifier = Modifier.padding(vertical = 8.dp)){
                        CurrentShipments()
                    }

                    Row(){
                        CurrentShipments()
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Open value is $open")

                        Button(
                            onClick={vm.changeState()},
                            enabled = open!=null
                        ){
                            Text("Change to ${!open!!}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddButton(onClick : () -> Unit){
    FloatingActionButton(
        onClick = { onClick() },
        shape = CircleShape,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Icon(Icons.Filled.Add, "Add new shipment")
    }
}

@Composable
fun DebugScreen(vm : MainViewModel){
    val test by vm.testText.observeAsState()
    val nick by vm.nickname.observeAsState()
    val email = vm.email.value

    Column(){
        Text(
            text= "UID: ",
            fontWeight = FontWeight.Bold
        )
        Text(vm.getUID())
        Text(
            text= "Test: ",
            fontWeight = FontWeight.Bold
        )
        Text(test?:"null")
        //Text(vm.testText.value?:"null")
        Text(
            text= "User DB ref: ",
            fontWeight = FontWeight.Bold
        )
        //Text(vm.users.toString())
        Text(
            text= "Nickname: ",
            fontWeight = FontWeight.Bold
        )
        //Text(vm.nickname.value?:"null")
        Text(nick?:"null")
        Text(
            text= "Email: ",
            fontWeight = FontWeight.Bold
        )
        Text(email?:"null")
    }
}

@Composable
fun CurrentShipments(){
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded }
    ){
        Column(){
            Text(
                text = "$expanded",
                modifier = Modifier.padding(8.dp)
            )
            if (expanded) {
                Text(
                    text = "Content Sample for Display on Expansion of Card",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}