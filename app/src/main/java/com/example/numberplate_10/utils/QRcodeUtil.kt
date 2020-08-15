package com.example.numberplate_10.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.CharacterSetECI
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

class QRcodeUtil {
    companion object {
        fun createQRcode(qrContent: String, width: Int, height: Int): Bitmap {
            val qrCodeOptionMap: MutableMap<EncodeHintType, Any> = mutableMapOf()
            qrCodeOptionMap.also {
                it[EncodeHintType.CHARACTER_SET] = CharacterSetECI.UTF8
                it[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
                it[EncodeHintType.MARGIN] = 1
            }

            val barcodeFormat = BarcodeFormat.QR_CODE
            val matrix = MultiFormatWriter().encode(qrContent, barcodeFormat, width, height, qrCodeOptionMap)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            for (i: Int in 0 until width) {
                for (j: Int in 0 until height) {
                    val color = matrix.get(i, j).let { if (it) Color.BLACK else Color.WHITE }
                    bitmap.setPixel(i, j, color)

                }
            }

            return bitmap
        }
    }
}