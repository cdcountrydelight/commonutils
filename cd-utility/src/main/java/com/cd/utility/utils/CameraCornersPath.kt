package com.cd.utility.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.layout

object CameraCornersPath {

    /**
    * draw only corners of a rectangle in android canvas
    * */
     fun createCornersPath(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        cornerRadius: Float,
        cornerLength: Float
    ): Path {
        val path = Path()

        // top left
        path.moveTo(left, (top + cornerRadius))
        path.arcTo(
            Rect(left = left, top = top, right = left + cornerRadius, bottom = top + cornerRadius),
            180f,
            90f,
            true
        )

        path.moveTo(left + (cornerRadius / 2f), top)
        path.lineTo(left + (cornerRadius / 2f) + cornerLength, top)

        path.moveTo(left, top + (cornerRadius / 2f))
        path.lineTo(left, top + (cornerRadius / 2f) + cornerLength)

        // top right
        path.moveTo(right - cornerRadius, top)
        path.arcTo(
            Rect(left = right - cornerRadius, top = top, right = right, bottom = top + cornerRadius),
            270f,
            90f,
            true
        )

        path.moveTo(right - (cornerRadius / 2f), top)
        path.lineTo(right - (cornerRadius / 2f) - cornerLength, top)

        path.moveTo(right, top + (cornerRadius / 2f))
        path.lineTo(right, top + (cornerRadius / 2f) + cornerLength)

        // bottom left
        path.moveTo(left, bottom - cornerRadius)
        path.arcTo(
            Rect(left = left, top = bottom - cornerRadius, right = left+cornerRadius, bottom = bottom),
            90f,
            90f,
            true
        )

        path.moveTo(left + (cornerRadius / 2f), bottom)
        path.lineTo(left + (cornerRadius / 2f) + cornerLength, bottom)

        path.moveTo(left, bottom - (cornerRadius / 2f))
        path.lineTo(left, bottom - (cornerRadius / 2f) - cornerLength)

        // bottom right
        path.moveTo(left, bottom - cornerRadius)
        path.arcTo(
            Rect(left = right - cornerRadius, top = bottom - cornerRadius, right = right, bottom = bottom),
            0f,
            90f,
            true
        )

        path.moveTo(right - (cornerRadius / 2f), bottom)
        path.lineTo(right - (cornerRadius / 2f) - cornerLength, bottom)

        path.moveTo(right, bottom - (cornerRadius / 2f))
        path.lineTo(right, bottom - (cornerRadius / 2f) - cornerLength)

        return path
    }


    fun Modifier.layout90Rotated() =
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            layout(placeable.height, placeable.width) {
                placeable.place(-(placeable.width - placeable.height)/2, (placeable.width - placeable.height) / 2)
            }
        }

}
