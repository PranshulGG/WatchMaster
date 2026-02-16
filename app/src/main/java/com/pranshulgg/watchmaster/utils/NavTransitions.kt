package com.pranshulgg.watchmaster.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MotionScheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
object NavTransitions {

    private const val FADE_IN = 350
    private const val FADE_OUT = 200

    fun enter(motionScheme: MotionScheme): EnterTransition =
        slideInHorizontally(
            animationSpec = motionScheme.defaultEffectsSpec(),
            initialOffsetX = { it }
        ) + fadeIn(tween(FADE_IN))

    fun exit(motionScheme: MotionScheme): ExitTransition =
        slideOutHorizontally(
            animationSpec = motionScheme.defaultEffectsSpec(),
            targetOffsetX = { -it }
        ) + fadeOut(tween(FADE_OUT))


    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    fun popEnter(motionScheme: MotionScheme): EnterTransition =
        slideInHorizontally(
            animationSpec = motionScheme.defaultEffectsSpec(),
            initialOffsetX = { -it }
        ) + fadeIn(tween(FADE_IN))

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    fun popExit(motionScheme: MotionScheme): ExitTransition =
        slideOutHorizontally(
            animationSpec = motionScheme.defaultEffectsSpec(),
            targetOffsetX = { it }
        ) + fadeOut(tween(FADE_OUT))
}