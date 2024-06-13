package com.example.tap_sample.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tap_sample.database.HotSheet
import com.example.tap_sample.database.Inspector
import com.example.tap_sample.database.InspectorDao
import com.example.tap_sample.database.LineRoute
import com.example.tap_sample.database.Notifications
import com.example.tap_sample.database.RoomDB
import com.example.tap_sample.database.ServiceType
import com.example.tap_sample.network.NetworkResult
import com.example.tap_sample.network.ResponseModel
import com.example.tap_sample.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor
    (private val loginRepository: LoginRepository,
     private val roomDB: RoomDB,
     application: Application): AndroidViewModel(application)
{
         suspend fun login(username: String, password: String):Inspector? {
        return loginRepository.login(username, password)
    }

      val inspector : LiveData<Inspector?>
      get() = loginRepository.inspector

    private val _response: MutableLiveData<NetworkResult<ResponseModel>> = MutableLiveData()
    val response: LiveData<NetworkResult<ResponseModel>> = _response

    fun fetchDataResponse() = viewModelScope.launch {
        loginRepository.fetchData().collect { values ->
            _response.value = values
        }
    }

    private val _progressLiveData = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progressLiveData

    fun downloadAndExtractDataFromUrl(urlString: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loginRepository.downloadAndExtractZipFile(urlString) { progress ->
                    _progressLiveData.postValue(progress)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun searchDatabase(): LiveData<List<LineRoute>> {
        return loginRepository.searchDatabase()
    }

     fun getLineRouteByDescription(description: String): LineRoute? {
        return loginRepository.getLineRouteByDescription(description)
    }

    val allTopSheets: LiveData<List<HotSheet>> =loginRepository.allTopSheets

    private val _selectedValue = MutableLiveData<String>()
    val selectedValue: LiveData<String>
        get() = _selectedValue

    fun setSelectedValue(value: String) {
        Log.d("LoginViewModel", "Setting selected value: $value")
        _selectedValue.value = value
    }

    private val _serviceTypeList = MutableLiveData<List<ServiceType>>()
    val serviceTypeList: LiveData<List<ServiceType>> get() = _serviceTypeList

    fun getServiceTypeList(selectedTransitType: Int) {
        viewModelScope.launch {
            _serviceTypeList.value = loginRepository.getServiceTypeList(selectedTransitType)
        }
    }

    val allNotifications: LiveData<List<Notifications>> = loginRepository.getAllNotifications()

    fun insertNotification(notification: Notifications) {
        viewModelScope.launch {
            loginRepository.insertNotification(notification)
        }
    }



}