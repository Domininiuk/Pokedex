package com.example.pokdex.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pokdex.R

@Composable
fun LottieLoadingAnimation()
{

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            composition,
            progress,
        )

}