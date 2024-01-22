package it.polito.did.g2.shopdrop.ui.home

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.polito.did.g2.shopdrop.MainViewModel
import it.polito.did.g2.shopdrop.data.StoreItem
import it.polito.did.g2.shopdrop.data.StoreItemCategory
import it.polito.did.g2.shopdrop.data.TabScreen
import it.polito.did.g2.shopdrop.ui.common.BottomBar
import it.polito.did.g2.shopdrop.ui.common.StoreItemCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySection(navController: NavController, viewModel: MainViewModel, sectionName: StoreItemCategory?){
    var currentTab = TabScreen.HOME

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    //Bottom sheet
    val bottomSheetState = rememberModalBottomSheetState()
    val bottomSheetScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val itList = viewModel.storeItems.value?.filter{storeItem -> storeItem.category==sectionName }
    var targetItem by remember{ mutableStateOf<StoreItem?>(null) }

    Scaffold(
        topBar = { SectionTopBar(navController, sectionName, scrollBehavior) },
        bottomBar = { BottomBar(currentTab, navController, viewModel) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        //floatingActionButton = { AddButton(onClick = {/*TODO*/}) },
        //floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            //Text(text = "TEST")

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 8.dp)
            ){
                items(
                    count = itList!!.size,
                    span = { GridItemSpan(1) }
                ) {
                    StoreItemCard(itList[it]) { item ->
                        targetItem = item
                        showBottomSheet = true
                    }
                }
            }
        }
    }

    if(showBottomSheet){
        Log.d("MODAL", "Dovrebbe aprirsi qui")
        ConfirmSheet(targetItem, viewModel, {showBottomSheet=false}, bottomSheetState, bottomSheetScope)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionTopBar(
    navController: NavController,
    sectionName: StoreItemCategory?,
    scrollBehavior: TopAppBarScrollBehavior
){

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = if(sectionName!=null) stringResource(sectionName.getStringRef()).capitalize() else "<ERRORE>",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}