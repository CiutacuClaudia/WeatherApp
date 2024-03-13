package com.ciutacuclaudia.weatherapp.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.ciutacuclaudia.domain.model.result.ErrorCause
import com.ciutacuclaudia.weatherapp.BuildConfig
import com.ciutacuclaudia.weatherapp.R
import com.ciutacuclaudia.weatherapp.ui.theme.spacing
import com.ciutacuclaudia.weatherapp.utils.dateDifferenceFormat
import com.ciutacuclaudia.weatherapp.utils.dateFormat
import com.ciutacuclaudia.weatherapp.viewmodel.MainViewModel
import com.ciutacuclaudia.weatherapp.viewmodel.ScreenState
import kotlinx.coroutines.delay
import java.util.Date
import kotlin.math.abs

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is ScreenState.Success -> {
            SuccessScreen(uiState = uiState as ScreenState.Success)
        }

        is ScreenState.Error -> {
            ErrorScreen(uiState = uiState as ScreenState.Error)
        }

        ScreenState.Loading -> {
            LoadingAnimation()
        }
    }
}


@Composable
fun SuccessScreen(
    uiState: ScreenState.Success,
    viewModel: MainViewModel = hiltViewModel()
) {
    val weatherAlerts = uiState.weatherAlerts
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(weatherAlerts.size) { index ->
            val startDate = dateFormat.parse(weatherAlerts[index].startDate)
            val endDate = weatherAlerts[index].endDate?.let { dateFormat.parse(it) }
            val formattedDifference: String = if (startDate != null && endDate != null) {
                val differenceInMillis: Long = abs(endDate.time - startDate.time)
                dateDifferenceFormat.format(Date(differenceInMillis))
            } else {
                stringResource(R.string.nullString)
            }

            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.small),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = weatherAlerts[index].event,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Source: " + weatherAlerts[index].source,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "Starts at: " + weatherAlerts[index].startDate,
                        style = MaterialTheme.typography.labelSmall
                    )
                    weatherAlerts[index].endDate?.let {
                        Text(
                            text = "Ends at: $it",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Text(
                        text = "Duration : $formattedDifference",
                        style = MaterialTheme.typography.labelSmall
                    )

                    val url = BuildConfig.IMAGES_URL

                    val indexQueryParameter =
                        "index=${viewModel.randomValueForSession - index}"
                    val urlWithRandom =
                        if (url.contains("?")) "$url&$indexQueryParameter" else "$url?$indexQueryParameter"

                    ImageFromUrl(url = urlWithRandom)
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(
    uiState: ScreenState.Error,
    viewModel: MainViewModel = hiltViewModel()
) {
    val message: String = when (uiState.errorCause) {
        ErrorCause.EMPTY_LIST -> {
            stringResource(R.string.the_list_is_empty)
        }

        ErrorCause.UNKNOWN_ERROR -> {
            stringResource(R.string.oops_something_went_wrong)
        }
    }


    AlertDialog(
        title = { Text(text = stringResource(R.string.error)) },
        text = { Text(text = message) },
        onDismissRequest = { viewModel.getWeatherAlerts() },
        confirmButton = {
            TextButton(onClick = { viewModel.getWeatherAlerts() }) {
                Text(text = stringResource(R.string.ok))
            }
        }
    )
}

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    circleSize: Dp = 25.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    if (isVisible) {
        val circles = listOf(
            remember { Animatable(initialValue = 0f) },
            remember { Animatable(initialValue = 0f) },
            remember { Animatable(initialValue = 0f) }
        )

        circles.forEachIndexed { index, animatable ->
            LaunchedEffect(key1 = animatable) {
                delay(index * 100L)
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = 1200
                            0.0f at 0 using LinearOutSlowInEasing
                            1.0f at 300 using LinearOutSlowInEasing
                            0.0f at 600 using LinearOutSlowInEasing
                            0.0f at 1200 using LinearOutSlowInEasing
                        },
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        }

        val circleValues = circles.map { it.value }
        val distance = with(LocalDensity.current) { travelDistance.toPx() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray.copy(0.4f)),
            contentAlignment = Alignment.Center
        )
        {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(spaceBetween)
            ) {
                circleValues.forEach { value ->
                    Box(
                        modifier = Modifier
                            .size(circleSize)
                            .graphicsLayer {
                                translationY = -value * distance
                            }
                            .background(
                                color = circleColor,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun ImageFromUrl(modifier: Modifier = Modifier, url: String) {
    SubcomposeAsyncImage(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .shadow(10.dp),
        model = url,
        contentDescription = "",
        loading = {
            CircularProgressIndicator()
        }
    )
}