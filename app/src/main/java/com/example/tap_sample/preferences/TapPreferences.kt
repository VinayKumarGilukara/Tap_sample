package com.example.tap_sample.preferences

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.preference.PreferenceManager
import com.example.tap_sample.R


class TapPreferences(private val mContext: Context) :
    OnSharedPreferenceChangeListener {
    private val prefs: SharedPreferences

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext)
        prefs.registerOnSharedPreferenceChangeListener(this)
        ArrayList<OnTapPreferencesChangedListener>()
    }

    fun saveGCMRegId(regId: String?) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(mContext.getString(R.string.prefs_gcm_id), regId)
        editor.commit()
    }



    fun saveSecretkey(key: String?) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(mContext.getString(R.string.prefs_secret_key), key)
        editor.commit()
    }

    val secretKey: String?
        get() = prefs.getString(
            mContext.getString(R.string.prefs_secret_key),
            null
        )
    var isFirstTime: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_firstime),
                true
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_firstime),
                flag
            )
            editor.commit()
        }


    fun saveStatusMessage(message: String?) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(
            mContext.getString(R.string.prefs_device_status_inserver),
            message
        )
        editor.commit()
    }


    var serverResponse: String?
        get() {
            return prefs.getString(
                mContext.getString(R.string.prefs_server_response), null
            )
        }
        set(code) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(
                mContext.getString(R.string.prefs_server_response),
                code
            )
            editor.commit()
        }


    override fun onSharedPreferenceChanged(
        sharedPreferences: SharedPreferences,
        key: String?,
    ) {
    }

    interface OnTapPreferencesChangedListener {
        fun onTapPreferenceChanged(key: String?)
    }

    var dbVersion: Int
        get() {
            return prefs
                .getInt(
                    mContext.resources.getString(
                        R.string.prefs_db_version
                    ), 1
                )
        }
        set(version) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.prefs_db_version),
                version
            )
            editor.commit()
        }
    var dBSize: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(R.string.prefs_db_size), 0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.prefs_db_size), size
            )
            editor.commit()
        }
    var zipSize: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(R.string.prefs_zip_size), 0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources
                    .getString(R.string.prefs_zip_size), size
            )
            editor.commit()
        }
    var tapDbUrl: String?
        get() {
            return prefs.getString(
                mContext.resources.getString(R.string.prefs_db_url), null
            )
        }
        set(url) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(
                mContext.resources.getString(R.string.prefs_db_url), url
            )
            editor.commit()
        }
    var lastCommunicationTime: Long
        get() {
            return prefs.getLong(
                mContext.resources.getString(
                    R.string.prefs_last_communication
                ), 0
            )
        }
        set(time) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putLong(
                mContext.resources.getString(
                    R.string.prefs_last_communication
                ), time
            )
            editor.commit()
        }

    var lastUploadedTime: Long
        get() {
            return prefs
                .getLong(
                    mContext.resources.getString(
                        R.string.prefs_last_uploaded
                    ), 0
                )
        }
        set(time) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putLong(
                mContext.resources.getString(R.string.prefs_last_uploaded),
                time
            )
            editor.commit()
        }


    var jWTToken: String?
        get() {
            return prefs.getString(mContext.getString(R.string.prefs_jwt_token), null)
        }
        set(route) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(mContext.getString(R.string.prefs_jwt_token), route)
            editor.commit()
        }
    var keyVersion: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(R.string.prefs_key_version),
                -1
            )
        }
        set(version) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.prefs_key_version),
                version
            )
            editor.commit()
        }
    var isLogin: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_firstime),
                true
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_firstime),
                flag
            )
            editor.commit()
        }
    var autoWipeTime: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(R.string.prefs_Wipe_time), 0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.prefs_Wipe_time),
                size
            )
            editor.commit()
        }
    var deviceStatusTime: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(R.string.prefs_status_time),
                0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.prefs_status_time),
                size
            )
            editor.commit()
        }

    fun incrementRedundantCount() {
        checkDateCount()
        var count: Int = redundantCount
        count++
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(
            mContext.resources.getString(R.string.pref_count_redundant),
            count
        )
        editor.commit()
    }

    var redundantCount: Int
        get() {
            //checkDateCount();
            return prefs
                .getInt(
                    mContext.resources.getString(
                        R.string.pref_count_redundant
                    ), 0
                )
        }
        set(count) {
            checkDateCount()
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.pref_count_redundant),
                count
            )
            editor.commit()
        }

    fun incrementConversionCount() {
        checkDateCount()
        var count: Int = conversionCount
        count++
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(
            mContext.resources.getString(R.string.pref_count_conversion),
            count
        )
        editor.commit()
    }

    var conversionCount: Int
        get() {
            //checkDateCount();
            return prefs
                .getInt(
                    mContext.resources.getString(
                        R.string.pref_count_conversion
                    ), 0
                )
        }
        set(count) {
            checkDateCount()
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.pref_count_conversion),
                count
            )
            editor.commit()
        }

    fun incrementGreenCount() {
        checkDateCount()
        var count: Int = greenCount
        count++
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(
            mContext.resources.getString(R.string.pref_count_green),
            count
        )
        editor.commit()
    }

    var greenCount: Int
        get() {
            //checkDateCount();
            return prefs
                .getInt(
                    mContext.resources.getString(
                        R.string.pref_count_green
                    ), 0
                )
        }
        set(count) {
            checkDateCount()
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.pref_count_green),
                count
            )
            editor.commit()
        }

    fun incrementYellowCount() {
        checkDateCount()
        var count: Int = yellowCount
        count++
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(
            mContext.resources.getString(R.string.pref_count_yellow),
            count
        )
        editor.commit()
    }

    var yellowCount: Int
        get() {
            //checkDateCount();
            return prefs.getInt(
                mContext.resources.getString(R.string.pref_count_yellow),
                0
            )
        }
        set(count) {
            checkDateCount()
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.pref_count_yellow),
                count
            )
            editor.commit()
        }

    fun incrementRedCount() {
        checkDateCount()
        var count: Int = redCount
        count++
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(
            mContext.resources
                .getString(R.string.pref_count_red), count
        )
        editor.commit()
    }

    var redCount: Int
        get() {
            //checkDateCount();
            return prefs.getInt(
                mContext.resources.getString(R.string.pref_count_red), 0
            )
        }
        set(count) {
            checkDateCount()
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources
                    .getString(R.string.pref_count_red), count
            )
            editor.commit()
        }


    private fun checkDateCount() {
        val prevDate: String? = prefs.getString(
            mContext.resources.getString(R.string.pref_count_date),
            "empty"
        )

    }

    var criminalDbVersion: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_Criminal_db_version
                ), 1
            )
        }
        set(version) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_Criminal_db_version
                ), version
            )
            editor.commit()
        }
    var criminalDBSize: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_Criminal_db_size
                ), 0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_Criminal_db_size
                ), size
            )
            editor.commit()
        }
    var criminalDBZipSize: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_Criminal_zip_size
                ), 0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_Criminal_zip_size
                ), size
            )
            editor.commit()
        }
    var criminalDbUrl: String?
        get() {
            return prefs.getString(
                mContext.resources.getString(
                    R.string.prefs_Criminal_db_url
                ), null
            )
        }
        set(url) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(
                mContext.resources.getString(
                    R.string.prefs_Criminal_db_url
                ), url
            )
            editor.commit()
        }
    var railMapVersion: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_rail_map_version
                ), 0
            )
        }
        set(version) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_rail_map_version
                ), version
            )
            editor.commit()
        }
    var dBInstalled: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(R.string.prefs_db_install),
                -1
            )
        }
        set(status) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.prefs_db_install),
                status
            )
            editor.commit()
        }
    var isDeviceAdminActive: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_device_admin),
                false
            )
        }
        set(status) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_device_admin),
                status
            )
            editor.commit()
        }
    var criminalDBInstalled: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_criminal_db_install
                ), -1
            )
        }
        set(status) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_criminal_db_install
                ), status
            )
            editor.commit()
        }

    fun clearAppdata() {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear().commit()
    }

    var autoDeleteKeyTime: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_delete_key_time
                ), 0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_delete_key_time
                ), size
            )
            editor.commit()
        }
    var preTransTime: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_upload_transcations
                ), 0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_upload_transcations
                ), size
            )
            editor.commit()
        }
    var autoLogoutTime: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(R.string.prefs_auto_logout),
                0
            )
        }
        set(size) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(R.string.prefs_auto_logout),
                size
            )
            editor.commit()
        }

    fun setAutoupdateTime(size: Int) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(
            mContext.resources.getString(R.string.prefs_auto_update),
            size
        )
        editor.commit()
    }

    val autoUpdateTime: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(R.string.prefs_auto_update),
                0
            )
        }
    var isTapDbExist: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_tap_db_exist),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_tap_db_exist),
                flag
            )
            editor.commit()
        }
    var isCriminalDbExist: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(
                    R.string.prefs_criminal_db_exist
                ), false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(
                    R.string.prefs_criminal_db_exist
                ), flag
            )
            editor.commit()
        }
    var isTapKeyExist: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_tapKey_exist),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_tapKey_exist),
                flag
            )
            editor.commit()
        }
    var isUpdateGcmSuccess: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_gcm_update),
                true
            )
        }
        set(success) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_gcm_update),
                success
            )
            editor.commit()
        }
    var lastCommend: String?
        get() {
            return prefs.getString(
                mContext.resources.getString(
                    R.string.prefs_last_commend
                ), null
            )
        }
        set(commend) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(
                mContext.resources.getString(
                    R.string.prefs_last_commend
                ), commend
            )
            editor.commit()
        }
    var serverBaseUrl: String?
        get() {
            return prefs.getString(
                mContext.resources.getString(
                    R.string.prefs_server
                ), null
            )
        }
        set(name) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(
                mContext.resources.getString(
                    R.string.prefs_server
                ), name
            )
            editor.commit()
        }
    val transactionNumber: Short
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_transcation_number
                ), 0
            ).toShort()
        }

    fun incrementTransactionCount() {
        var count: Short
        if (transactionNumber <= Short.MAX_VALUE) {
            count = transactionNumber
            count++
        } else {
            count = 0
        }
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(
            mContext.resources.getString(
                R.string.prefs_transcation_number
            ), count.toInt()
        )
        editor.commit()
    }

    fun setTransactionCount(count: Short) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(
            mContext.resources.getString(
                R.string.prefs_transcation_number
            ), count.toInt()
        )
        editor.commit()
    }

    var appVersion: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_appversion
                ), 0
            )
        }
        set(version) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_appversion
                ), version
            )
            editor.commit()
        }

    var serverSettings: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_server_settings),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_server_settings),
                flag
            )
            editor.commit()
        }
    var locationUpdate: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_status_location_update),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_status_location_update),
                flag
            )
            editor.commit()
        }
    var transactionsUpload: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_transactions_upload),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_transactions_upload),
                flag
            )
            editor.commit()
        }
    var inspectors: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_inspectors),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_inspectors),
                flag
            )
            editor.commit()
        }
    var performanceLogs: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_performance),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_performance),
                flag
            )
            editor.commit()
        }
    var netWorkLogs: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_network),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_network),
                flag
            )
            editor.commit()
        }
    var cardOperationsLogs: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(R.string.prefs_card_operations),
                false
            )
        }
        set(flag) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(R.string.prefs_card_operations),
                flag
            )
            editor.commit()
        }

    var userLoginStatus: String?
        get() {
            return prefs.getString(
                mContext.resources.getString(
                    R.string.prefs_user_status
                ), null
            )
        }
        set(staus) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(
                mContext.resources.getString(
                    R.string.prefs_user_status
                ), staus
            )
            editor.commit()
        }
    var redundantTime: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_redundant_time
                ), 0
            )
        }
        set(staus) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_redundant_time
                ), staus
            )
            editor.commit()
        }
    var conversionTime: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_conversion_time
                ), 0
            )
        }
        set(staus) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_conversion_time
                ), staus
            )
            editor.commit()
        }
    var isEnableLoggingControl: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(
                    R.string.prefs_logging_enable
                ), false
            )
        }
        set(staus) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(
                mContext.resources.getString(
                    R.string.prefs_logging_enable
                ), staus
            )
            editor.commit()
        }
    var operationMode: Int
        get() {
            return prefs.getInt(mContext.resources.getString(R.string.prefs_operation_mode), 0)
        }
        set(mode) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(mContext.resources.getString(R.string.prefs_operation_mode), mode)
            editor.commit()
        }
    var serviceLevelZone: String?
        get() {
            return prefs.getString(
                mContext.resources.getString(R.string.pref_service_level_zone),
                null
            )
        }
        /* public void setServiceLevel(int serviceLevel) {
                         SharedPreferences.Editor editor = prefs.edit();
                         editor.putInt(mContext.getResources().getString(R.string.pref_service_level), serviceLevel);
                         editor.commit();
                     }

                     public int getServiceLevel() {
                         return prefs.getInt(mContext.getResources().getString(R.string.pref_service_level),
                                 1);
                     }*/ set(serviceLevelZone) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(
                mContext.resources.getString(R.string.pref_service_level_zone),
                serviceLevelZone
            )
            editor.commit()
        }
    var schedulerCount: Int
        get() {
            return prefs.getInt(
                mContext.resources.getString(
                    R.string.prefs_scheduler_count
                ), 0
            )
        }
        set(staus) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                mContext.resources.getString(
                    R.string.prefs_scheduler_count
                ), staus
            )
            editor.commit()
        }

    fun setLocationServiceEnable(staus: Boolean) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putBoolean(
            mContext.resources.getString(
                R.string.prefs_location_service_enable
            ), staus
        )
        editor.commit()
    }

    val isEnableLocationService: Boolean
        get() {
            return prefs.getBoolean(
                mContext.resources.getString(
                    R.string.prefs_location_service_enable
                ), false
            )
        }
    var iMEINumber: String?
        get() {
            return prefs.getString(
                mContext.resources.getString(
                    R.string.prefs_imei_number
                ), null
            )
        }
        set(imeiNumber) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(
                mContext.resources.getString(
                    R.string.prefs_imei_number
                ), imeiNumber
            )
            editor.commit()
        }
    var iVKey: String?
        get() {
            return prefs.getString(mContext.resources.getString(R.string.prefs_ivKey), null)
        }
        set(ivKey) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(mContext.resources.getString(R.string.prefs_ivKey), ivKey)
            editor.commit()
        }

    fun saveEncryptSecretKey(key: String?) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(mContext.getString(R.string.prefs_encrypt_secret_key), key)
        editor.commit()
    }

    val encryptSecretKey: String?
        get() {
            return prefs.getString(
                mContext.getString(R.string.prefs_encrypt_secret_key),
                null
            )
        }
    fun getIncorrectLoginAttemptTime(): Long {
        return prefs.getLong(
            mContext.resources.getString(
                R.string.prefs_incorrect_login_time
            ), 0
        )
    }
    fun setIncorrectLoginAttemptTime(time: Long) {
        val editor = prefs.edit()
        editor.putLong(
            mContext.resources.getString(R.string.prefs_incorrect_login_time),
            time
        )
        editor.commit()
    }
    fun getLoginFailCount(): Int {
        return prefs.getInt(
            mContext.resources.getString(
                R.string.prefs_login_fail_count
            ), 0
        )
    }
    fun setLoginFailCount(count: Int) {
        val editor = prefs.edit()
        editor.putInt(
            mContext.resources.getString(R.string.prefs_login_fail_count),
            count
        )
        editor.commit()
    }

    fun getGCMRegId(): String? {
        return prefs.getString(mContext.getString(R.string.prefs_gcm_id), null)
    }
    fun getStatusMessage(): String? {
        return prefs.getString(
            mContext.getString(R.string.prefs_device_status_inserver),
            "Device is not registered with server please try again"
        )
    }
    fun setShiftId(shiftId: String?) {
        val editor = prefs.edit()
        editor.putString(
            mContext.resources.getString(
                R.string.prefs_shift
            ), shiftId
        )
        editor.commit()
    }

    fun getShiftId(): String? {
        return prefs.getString(
            mContext.resources.getString(
                R.string.prefs_shift
            ), null
        )
    }

    fun setShiftCardNumber(number: String?) {
        val editor = prefs.edit()
        editor.putString(
            mContext.resources.getString(
                R.string.prefs_shift_card
            ), number
        )
        editor.commit()
    }

    fun getShiftCardNumber(): String? {
        return prefs.getString(
            mContext.resources.getString(
                R.string.prefs_shift_card
            ), null
        )
    }
    fun setUsername(name: String?) {
        val editor = prefs.edit()
        editor.putString(mContext.getString(R.string.prefs_user_name), name)
        editor.commit()
    }

    fun getUsername(): String? {
        return prefs.getString(
            mContext.getString(R.string.prefs_user_name),
            null
        )
    }
    fun setUserFirstName(name: String?) {
        val editor = prefs.edit()
        editor.putString(
            mContext.getString(R.string.prefs_user_first_name),
            name
        )
        editor.commit()
    }

    fun getUserFirstName(): String? {
        return prefs.getString(
            mContext.getString(R.string.prefs_user_first_name), null
        )
    }
    fun setUserLastName(name: String?) {
        val editor = prefs.edit()
        editor.putString(
            mContext.getString(R.string.prefs_user_last_name),
            name
        )
        editor.commit()
    }

    fun getUserLastName(): String? {
        return prefs.getString(
            mContext.getString(R.string.prefs_user_last_name), null
        )
    }

    fun setUserGroup(name: String?) {
        val editor = prefs.edit()
        editor.putString(
            mContext.getString(R.string.prefs_user_group_name),
            name
        )
        editor.commit()
    }

    fun getUserGroup(): String? {
        return prefs.getString(
            mContext.getString(R.string.prefs_user_group_name), null
        )
    }
    fun setUserId(name: String?) {
        val editor = prefs.edit()
        editor.putString(mContext.getString(R.string.prefs_user_id), name)
        editor.commit()
    }

    fun getUserId(): String? {
        return prefs
            .getString(mContext.getString(R.string.prefs_user_id), null)
    }
    fun getCountsDate(): String? {
        return prefs.getString(
            mContext.resources.getString(R.string.pref_count_date),
            "empty"
        )
    }
    fun setOperator(operater: String?) {
        val editor = prefs.edit()
        editor.putString(
            mContext.resources.getString(
                R.string.prefs_operator
            ), operater
        )
        editor.commit()
    }

    fun getOperator(): String? {
        return prefs.getString(
            mContext.resources.getString(
                R.string.prefs_operator
            ), null
        )
    }
    fun setInspectorName(name: String?) {
        val editor = prefs.edit()
        editor.putString(
            mContext.resources.getString(
                R.string.prefs_device_inspector
            ), name
        )
        editor.commit()
    }

    fun getInspectorName(): String? {
        return prefs.getString(
            mContext.resources.getString(
                R.string.prefs_device_inspector
            ), null
        )
    }
    fun getSelectedRoute(): String? {
        return prefs.getString(mContext.getString(R.string.prefs_route), null)
    }
    fun setSelectedStop(stop: Int) {
        val editor = prefs.edit()
        editor.putInt(mContext.getString(R.string.prefs_stop), stop)
        editor.commit()
    }

    fun getSelectedStop(): Int {
        return prefs.getInt(mContext.getString(R.string.prefs_stop), -1)
    }

    fun setSelectedStopName(stopName: String?) {
        val editor = prefs.edit()
        editor.putString(mContext.getString(R.string.prefs_stop_name), stopName)
        editor.commit()
    }

    fun getSelectedStopName(): String? {
        return prefs.getString(mContext.getString(R.string.prefs_stop_name), null)
    }
    fun setSelectedRoute(route: String?) {
        val editor = prefs.edit()
        editor.putString(mContext.getString(R.string.prefs_route), route)
        editor.commit()
    }


    companion object {
        private val TAG: String = "TapPreferences"
    }
}