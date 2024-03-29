package it.polito.did.g2.shopdrop.ui.cst.common

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import it.polito.did.g2.shopdrop.R
import it.polito.did.g2.shopdrop.data.StoreItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StoreItemCard(item: StoreItem, onClick: (it: StoreItem) -> Unit){
    Card(
        Modifier
            .width(165.dp)
            .padding(8.dp)
            .clickable(onClick = { onClick(item) })
    ){
        //Spacer(Modifier.height(160.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.thumbnail)
                .listener(
                    onError = { request, result ->
                        Log.e("ASYNCIMG", "Errore nel caricamento dell'immagine (${result.throwable})")
                    }
                )
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.desc_item_pic_of)+item.name,
            modifier = Modifier
                .height(165.dp)
                .width(165.dp)

                //.wrapContentSize(),
        )

        Text(
            text = item.name.capitalize(),
            //fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            maxLines = 1,

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                //.align(Alignment.CenterHorizontally)
        )
        //Spacer(Modifier.height(8.dp))
        Text(
            text = item.price.toString()+" €",
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(Modifier.height(8.dp))
    }
}