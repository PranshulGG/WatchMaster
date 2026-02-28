package com.pranshulgg.watchmaster.core.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.core.ui.theme.Radius

fun MaterialListShape(isOnly: Boolean, isFirst: Boolean, isLast: Boolean): Shape {

    val shape = when {
        isOnly -> RoundedCornerShape(Radius.Large)
        isFirst -> RoundedCornerShape(
            topStart = Radius.Large,
            topEnd = Radius.Large,
            bottomStart = Radius.ExtraSmall,
            bottomEnd = Radius.ExtraSmall
        )

        isLast -> RoundedCornerShape(
            topStart = Radius.ExtraSmall,
            topEnd = Radius.ExtraSmall,
            bottomStart = Radius.Large,
            bottomEnd = Radius.Large
        )

        else -> RoundedCornerShape(Radius.ExtraSmall)
    }

    return shape

}
