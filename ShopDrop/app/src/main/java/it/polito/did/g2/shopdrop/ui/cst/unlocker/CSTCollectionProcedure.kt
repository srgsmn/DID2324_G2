package it.polito.did.g2.shopdrop.ui.cst.unlocker

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.polito.did.g2.shopdrop.MainViewModel
import it.polito.did.g2.shopdrop.navigation.Screens

enum class TimerPhase {
    Phase1,
    Phase2,
    Phase3,
    PhaseOver
}

const val ANIM_TIMING = 500

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CSTCollectionProcedure(navController: NavController, viewModel: MainViewModel, orderID: String){

    val timerMax = 3*60000L
    var timerState by remember { mutableStateOf(TimerPhase.Phase1) }
    var progress by remember { mutableStateOf(1f) }

    //val timerValue = viewModel.timerValue.observeAsState()
    val timerValue = viewModel.timerValue.collectAsState()

    val is1Open = viewModel.is1Open.collectAsState()
    val is2Open = viewModel.is2Open.collectAsState()


    LaunchedEffect(timerValue) {
        if(viewModel.isTimeout.value==false){
            when(timerValue.value){
                in timerMax*2/3..timerMax -> {
                    timerState = TimerPhase.Phase1
                }
                in timerMax/3..timerMax*2/3 -> {
                    timerState = TimerPhase.Phase2
                }
                in 0..timerMax/3 -> {
                    timerState = TimerPhase.Phase3
                }
                !in 0..timerMax -> {
                    timerState = TimerPhase.PhaseOver
                }
            }
        }else{
            timerState = TimerPhase.PhaseOver
        }
    }


    fun formatTime(milliseconds: Long?): String {
        return if(milliseconds!=null){
            val totalSeconds = milliseconds / 1000
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            String.format("%02d:%02d", minutes, seconds)
        }else{
            "ERR"
        }
    }

    viewModel.createTimer(timerMax)

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        if(is1Open.value == false && is2Open.value == false){
            Log.i("#####", "Sportello chiuso, deve cambiare")
            viewModel.cstHasCollected(orderID)
            viewModel.cancelTimer()
            navController.navigate(Screens.CstCollectionDoneScreen.route)
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if(viewModel.isTimeout.value==false){
                when(timerValue.value){
                    in timerMax*2/3..timerMax -> {
                        Text(text = "PHASE 1")
                    }
                    in timerMax/3..timerMax*2/3 -> {
                        Text(text = "PHASE 2")
                    }
                    !in timerMax/3..timerMax -> {
                        Text(text = "PHASE 3")
                    }
                }
            }else{
                Text("TIME OUT")
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ){
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = formatTime(timerValue.value),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(Modifier.padding(horizontal = 32.dp, vertical = 32.dp)){
                    LinearProgressIndicator(
                        progress = {
                            if(timerValue.value!=null){
                                (1f* timerValue.value!!)/timerMax
                            }else{
                                1f
                            }

                        },
                        color =
                            if(viewModel.isTimeout.value==false){
                                when(timerValue.value){
                                    in timerMax*2/3..timerMax -> {
                                        Color.Green
                                    }
                                    in timerMax/3..timerMax*2/3 -> {
                                        Color.Yellow
                                    }
                                    !in timerMax/3..timerMax -> {
                                        Color.Red
                                    }
                                    else -> Color.Transparent
                                }
                            }else{
                                Color.Transparent
                            },
                        trackColor = Color.LightGray,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }

            }
        }
    }
}