package com.sysco.planetdetails.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sysco.planetdetails.R
import com.sysco.planetdetails.presentation.components.PlanetDetailsContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailsScreen(
    onNavigateBack: () -> Unit,
    viewModel: PlanetDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val notAvailableString = stringResource(id = R.string.planet_details_na)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.planet?.name.orEmpty(),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {

                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (state.error != null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = state.error!!.asString(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 16.sp
                            )
                        )
                    }
                } else {
                    PlanetDetailsContent(
                        modifier = Modifier.fillMaxSize(),
                        gravity = stringResource(
                            id = R.string.planet_details_gravity_label,
                            state.planet?.gravity ?: notAvailableString
                        ),
                        orbitalPeriod = stringResource(
                            id = R.string.planet_details_orbital_period_label,
                            state.planet?.orbitalPeriod ?: notAvailableString
                        )
                    )
                }
            }
        }
    )

}