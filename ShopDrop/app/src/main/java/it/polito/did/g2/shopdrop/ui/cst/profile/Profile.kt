package it.polito.did.g2.shopdrop.ui.cst.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.polito.did.g2.shopdrop.MainViewModel
import it.polito.did.g2.shopdrop.R
import it.polito.did.g2.shopdrop.data.TabScreen
import it.polito.did.g2.shopdrop.navigation.Screens
import it.polito.did.g2.shopdrop.ui.common.ProfileItemList
import it.polito.did.g2.shopdrop.ui.cst.common.BottomBar
import it.polito.did.g2.shopdrop.ui.cst.common.TopBar
import it.polito.did.g2.shopdrop.ui.cst.common.WIPMessage
import it.polito.did.g2.shopdrop.ui.cst.common.performDevMsg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CSTProfileScreen(navController: NavController, viewModel: MainViewModel){
    val currentTab = TabScreen.PROFILE

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // SNACKBAR
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        topBar = { TopBar(null, stringResource(id = R.string.title_profile), scrollBehavior) },
        bottomBar = { BottomBar(currentTab, navController, viewModel) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { WIPMessage(snackbarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 36.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.crr_profile_pic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Column{
                            Text("${viewModel.currUser.value?.name} (${viewModel.currUser.value?.uid})")   //TODO mettere credenziali vere
                            Text("${viewModel.currUser.value?.email}")
                            Text("(+39) 333 1234567")
                        }
                        TextButton(onClick = { performDevMsg(scope, snackbarHostState, context) }) {
                            Text("Modifica")
                        }
                    }
                }

                ProfileItemList(label = stringResource(R.string.title_my_orders).capitalize(), onClick = { navController.navigate(Screens.CstOrderHistory.route) }, viewModel.hasPendings())
                ProfileItemList(label = stringResource(R.string.title_address).capitalize(), onClick = {performDevMsg(scope, snackbarHostState, context)})
                ProfileItemList(label = stringResource(R.string.title_payment_methods).capitalize(), onClick = {performDevMsg(scope, snackbarHostState, context)})
                ProfileItemList(label = stringResource(R.string.title_help).capitalize(), onClick = {performDevMsg(scope, snackbarHostState, context)})
                ProfileItemList(label = stringResource(R.string.title_support).capitalize(), onClick = { performDevMsg(scope, snackbarHostState, context) })

                Spacer(Modifier.height(32.dp))

                Text("v. ${viewModel.bldNum}", fontSize = 10.sp, color = Color.Gray)

                Spacer(Modifier.height(32.dp))

                TextButton(
                    onClick = {
                        viewModel.logout()
                        navController.navigate(Screens.Login.route)
                    }
                ) {
                    Text(stringResource(R.string.btn_log_out).capitalize())
                }
            }
        }
    }
}

