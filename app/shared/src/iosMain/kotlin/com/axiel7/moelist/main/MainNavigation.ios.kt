package com.axiel7.moelist.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.navigation3.scene.Scene
import androidx.navigationevent.NavigationEvent.Companion.EDGE_LEFT
import androidx.navigationevent.NavigationEvent.SwipeEdge

actual fun <T : Any> defaultPredictivePopTransitionSpec():
        AnimatedContentTransitionScope<Scene<T>>.(@SwipeEdge Int) -> ContentTransform = { edge ->
    val towards = if (edge == EDGE_LEFT) {
        AnimatedContentTransitionScope.SlideDirection.Right
    } else {
        AnimatedContentTransitionScope.SlideDirection.Left
    }
    ContentTransform(
        slideIntoContainer(
            towards = towards,
            initialOffset = { it / 4 },
            animationSpec = tween(),
        ),
        slideOutOfContainer(
            towards = towards,
            animationSpec = tween(),
        ),
    )
}