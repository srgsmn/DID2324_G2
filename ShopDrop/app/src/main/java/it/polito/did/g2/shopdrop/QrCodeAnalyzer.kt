package it.polito.did.g2.shopdrop

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QrCodeAnalyzer(
    private val onQrCodeScanned : (String) -> Unit
): ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )
    override fun analyze(image: ImageProxy) {
        if(image.format in supportedImageFormats){
            val bytes = image.planes.first().buffer.toByteArray()

            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )

            val binaryBmp = BinaryBitmap(HybridBinarizer(source))

            try{
                var result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE,
                                BarcodeFormat.CODE_128,
                                BarcodeFormat.CODE_93,
                                BarcodeFormat.CODE_39
                            )
                        )
                    )
                }.decode(binaryBmp)

                onQrCodeScanned(result.text)
            }catch(e: Exception){
                e.printStackTrace()
            }finally {
                image.close()
            }
        }
    }

    private fun ByteBuffer.toByteArray() : ByteArray{
        rewind()

        return ByteArray(remaining()).also{
            get(it)
        }
    }
}