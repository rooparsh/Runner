package com.darklabs.runner.ui

import androidx.lifecycle.ViewModel
import com.darklabs.data.local.dao.LocationDao
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 05/02/22
 */

@HiltViewModel
class MainViewModel @Inject constructor(locationDao: LocationDao) : ViewModel() {

    val locationStateFlow: Flow<LatLng> =
        locationDao.getLocation().filterNotNull().map { LatLng(it.latitude, it.longitude) }
}