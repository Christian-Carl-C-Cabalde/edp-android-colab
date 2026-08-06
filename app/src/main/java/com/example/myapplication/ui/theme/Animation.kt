package com.example.myapplication.ui.theme

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

val slideInFromBottom: EnterTransition = slideInVertically(
    initialOffsetY = { it },
    animationSpec = tween(durationMillis = 500)
) + fadeIn(animationSpec = tween(durationMillis = 500))

val slideOutToBottom: ExitTransition = slideOutVertically(
    targetOffsetY = { it },
    animationSpec = tween(durationMillis = 500)
) + fadeOut(animationSpec = tween(durationMillis = 500))

val slideInToTop: EnterTransition = slideInVertically(
    initialOffsetY = { -it },
    animationSpec = tween(durationMillis = 500)
) + fadeIn(animationSpec = tween(durationMillis = 500))

val slideOutToTop: ExitTransition = slideOutVertically(
    targetOffsetY = { -it },
    animationSpec = tween(durationMillis = 500)
) + fadeOut(animationSpec = tween(durationMillis = 500))
