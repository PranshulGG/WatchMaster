package com.pranshulgg.watchmaster.core.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.pranshulgg.watchmaster.core.ui.theme.Radius

@Composable
fun listItemShape(isOnly: Boolean, isFirst: Boolean, isLast: Boolean): RoundedCornerShape {

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
