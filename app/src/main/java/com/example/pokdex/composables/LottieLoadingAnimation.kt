package com.example.pokdex.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.example.pokdex.R

@Composable
fun LottieLoadingAnimation(modifier: Modifier = Modifier)
{

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
        val progress by animateLottieCompositionAsState(composition, speed = 1.55f, iterations = LottieConstants.IterateForever)
        LottieAnimation(modifier = modifier,
            composition = composition,
            progress = progress,
        )

}