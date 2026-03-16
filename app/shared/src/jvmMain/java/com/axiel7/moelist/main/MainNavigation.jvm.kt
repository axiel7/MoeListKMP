package com.axiel7.moelist.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.navigation3.scene.Scene
import androidx.navigationevent.NavigationEvent

actual fun <T : Any> defaultPredictivePopTransitionSpec():
        AnimatedContentTransitionScope<Scene<T>>.(@NavigationEvent.SwipeEdge Int) -> ContentTransform =
    {
        (slideInHorizontally(initialOffsetX = { -it })
                + fadeIn(animationSpec = tween())) togetherWith
                (slideOutHorizontally(targetOffsetX = { it }))
    }