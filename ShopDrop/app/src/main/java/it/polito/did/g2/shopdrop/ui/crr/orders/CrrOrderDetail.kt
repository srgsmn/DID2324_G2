package it.polito.did.g2.shopdrop.ui.crr.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.polito.did.g2.shopdrop.MainViewModel
import it.polito.did.g2.shopdrop.R
import it.polito.did.g2.shopdrop.data.OrderStateName
import it.polito.did.g2.shopdrop.navigation.Screens
import it.polito.did.g2.shopdrop.ui.theme.secondaryLight

@Composable
fun CRROrderDetail(navController: NavController, viewModel: MainViewModel, id : String?){

    val order = viewModel.ordersList.value?.find { it.id == id }

    val lastState : OrderStateName? = order?.stateList?.map { it.state }?.sortedBy { it.ordinal }?.last()


    Scaffold(
        floatingActionButton = {
            when (lastState) {
                OrderStateName.RECEIVED -> {
                    CrrScanButton(stringResource(R.string.btn_take_charge).capitalize()){
                        navController.navigate(Screens.CrrCollectionCamera.route+"/$id")
                    }
                }
                OrderStateName.CARRIED -> {
                    CrrScanButton(stringResource(R.string.btn_deposit_order).capitalize()){
                        navController.navigate(Screens.CrrBeforeDeposit.route+"/$id")
                    }
                }
                else -> {}
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    IconButton(onClick = { navController.navigate(Screens.CrrHomeScreen.route) }) {
                        Image(
                            painter = painterResource(id = R.drawable.btn_back),
                            contentDescription = "back"
                        )
                    }
                }
                if(id!=null){
                    Text(stringResource(id = R.string.order).capitalize())
                    Text("#${order?.id}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    val totalItems = order?.items?.size?:0
                    Text("$totalItems ${stringResource(if(totalItems==1) R.string.txt_item else R.string.txt_items)}")

                    Row(
                        modifier = Modifier.padding(vertical = 16.dp)
                    ){
                        Icon(Icons.Outlined.CheckCircle, null, Modifier.padding(horizontal = 8.dp), tint = secondaryLight)

                        Text(
                            text = when(lastState){
                                OrderStateName.RECEIVED -> {
                                    stringResource(id = R.string.chip_to_collect).capitalize()
                                }
                                OrderStateName.CARRIED -> {
                                    stringResource(id = R.string.chip_to_deposit).capitalize()
                                }
                                else -> {
                                    "ERR"
                                }
                            },
                            color = secondaryLight,
                            fontWeight = FontWeight.Bold
                            )
                    }

                    Spacer(Modifier.height(32.dp))

                    Column(Modifier.padding(16.dp)) {
                        Text(text = stringResource(id = R.string.title_store_address).capitalize(), style = MaterialTheme.typography.titleSmall)
                        Spacer(Modifier.height(8.dp))
                        Card(
                            Modifier
                                .fillMaxWidth()
                        ){

                            Column(Modifier.padding(8.dp)){
                                Text(
                                    "Carrefour Express",
                                    fontWeight = FontWeight.SemiBold
                                )

                                Row() {
                                    Text(
                                        "C.so G. Ferraris 24, 10121, TORINO",
                                        maxLines = 1,
                                        softWrap = true
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(64.dp))

                        Text(text = stringResource(id = R.string.title_locker_address).capitalize(), style = MaterialTheme.typography.titleSmall)
                        Spacer(Modifier.height(8.dp))
                        Card(
                            Modifier
                                .fillMaxWidth()
                        ){

                            Column(Modifier.padding(8.dp)){
                                Text(
                                    viewModel.lockersList.value?.find { it.id == order?.lockerID }?.name
                                        ?: "[LOCKER NAME]",
                                    fontWeight = FontWeight.SemiBold
                                )

                                Row() {
                                    Text(
                                        viewModel.lockersList.value?.find { it.id == order?.lockerID }?.address
                                            ?: "[LOCKER ADDRESS]",
                                        maxLines = 1,
                                        softWrap = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CrrScanButton(text: String, onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ){
        Text(text)
    }
}