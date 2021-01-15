package  com.pretty.library.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Permissions {

    //Calendar (日历, 日程表)
    const val CALENDAR_READ = Manifest.permission.READ_CALENDAR
    const val CALENDAR_WRITE = Manifest.permission.WRITE_CALENDAR
    val CALENDAR = arrayOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR
    )

    //Camera(相机)
    const val CAMERA = Manifest.permission.CAMERA

    //Contacts(联系人)
    const val CONTACTS_READ = Manifest.permission.READ_CONTACTS
    const val CONTACTS_WRITE = Manifest.permission.WRITE_CONTACTS
    const val CONTACTS_GET = Manifest.permission.GET_ACCOUNTS
    val CONTACTS = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.GET_ACCOUNTS
    )

    //Location(位置)
    val LOCATION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    const val LOCATION_BACKGROUND = Manifest.permission.ACCESS_BACKGROUND_LOCATION// 后台定位

    //Microphone(麦克风)
    const val MICROPHONE = Manifest.permission.RECORD_AUDIO

    //Phone(手机)
    const val PHONE_STATE = Manifest.permission.READ_PHONE_STATE
    const val PHONE_CALL = Manifest.permission.CALL_PHONE
    const val PHONE_READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
    const val PHONE_WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG
    const val PHONE_ADD_EMAIL = Manifest.permission.ADD_VOICEMAIL //语音信箱
    const val PHONE_USE_SIP = Manifest.permission.USE_SIP //使用SIP视频服务

    //Sensors(传感器)
    const val SENSORS = Manifest.permission.BODY_SENSORS //传感器

    //SMS(短信)
    const val SMS_SEND = Manifest.permission.SEND_SMS
    const val SMS_RECEIVE = Manifest.permission.RECEIVE_SMS
    const val SMS_READ = Manifest.permission.READ_SMS
    const val SMS_RECEIVE_PUSH = Manifest.permission.RECEIVE_WAP_PUSH
    const val SMS_RECEIVE_MMS = Manifest.permission.RECEIVE_MMS //彩信

    //External Storage(SD卡)
    const val STORAGE_READ = Manifest.permission.READ_EXTERNAL_STORAGE
    const val STORAGE_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}