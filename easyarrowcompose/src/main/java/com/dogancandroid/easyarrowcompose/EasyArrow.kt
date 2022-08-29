package com.dogancandroid.easyarrowcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * @author dogancankilic
 * Created on 26.08.2022
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EasyArrow(
    lazyListState: LazyListState,
    arrowImage: Painter,
    backgroundColor: Color
) {
    var durationMillis by remember { mutableStateOf(1200) }
    var delayMillis by remember { mutableStateOf(400) }
    val offsetY = remember { mutableStateOf(0f) }
    val repeat = remember { mutableStateOf(2) }
    val firstVisibility = remember { mutableStateOf(false) }
    firstVisibility.value = lazyListState.isScrollingUp()
    val triggerOffset = remember {
        mutableStateOf(false)
    }

    val rotationAngle by animateFloatAsState(
        targetValue = if (!firstVisibility.value) 180F else 0F,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis,
            easing = FastOutSlowInEasing,
        ),

        finishedListener = {
            offsetY.value = 15f
            triggerOffset.value = true

        }

    )

    val offsetAnimation: Dp by animateDpAsState(
        targetValue = offsetY.value.dp,
        animationSpec = tween(
            500,
            100,
            easing = FastOutSlowInEasing
        ), finishedListener = {
            if (!firstVisibility.value) {
                if (triggerOffset.value)
                    when (repeat.value) {
                        2 -> {
                            repeat.value -= 1
                            offsetY.value = 0f
                        }
                        1 -> {
                            repeat.value -= 1
                            offsetY.value = 15f
                        }
                        0 -> {
                            repeat.value -= 1
                            offsetY.value = 0f
                        }
                    }
            }


        }
    )

    AnimatedVisibility(
        visible = !firstVisibility.value,
        enter = scaleIn(
            animationSpec = tween(
                durationMillis = 500
            )
        ),
        exit = scaleOut(),
    ) {
        if (firstVisibility.value) {
            durationMillis = 0
            delayMillis = 0
            offsetY.value = 0f
            repeat.value = 2
            triggerOffset.value = false
        } else {
            durationMillis = 1200
            delayMillis = 400
        }
        val coroutineScope = rememberCoroutineScope()

        FloatingActionButton(onClick = {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(0)
            }

        }, backgroundColor = backgroundColor) {
            Image(
                painter = arrowImage,
                contentDescription = "",
                modifier = Modifier
                    .rotate(rotationAngle)
                    .offset(y = offsetAnimation)
            )

        }

    }

}