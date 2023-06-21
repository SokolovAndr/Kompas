package com.example.kompas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    var manager:SensorManager? = null //переменная для регистрации слушателя сенсора
    var current_degree:Int = 0 //для сохранения градусов

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager  //для инициализации менеджера, который управляет сенсорами

    }

    override fun onResume() { //функция из жизненного цикла активити для регистрации нашего слуашетеля SensorEventListener
        super.onResume()
        manager?.registerListener(this, manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() { //функция для приостановки hрегистрации слушателя, чтобы напрмиер в режиме звонка не тратился заряд
        super.onPause()
        manager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {  //первый реализованный метод интрфейса SensorEventListener
        //когда точность измерения меняется запускается эта функция. Например если точность маленькая можешь сделать чтоб данные не обновлялись или сообщить об этом пользователю
    }

    override fun onSensorChanged(p0: SensorEvent?) { //второй реализованный метод интрфейса SensorEventListener
        val degree:Int = p0?.values?.get(0)?.toInt()!!

        var tvDegree = findViewById<TextView>(R.id.tvDegree)  //решение проблемы
        tvDegree.text = degree.toString()

        val rotationAnim = RotateAnimation(current_degree.toFloat(),(-degree.toFloat()),Animation.RELATIVE_TO_SELF,
            0.5f,Animation.RELATIVE_TO_SELF, 0.5f)  //для вращения

        rotationAnim.duration = 210 //210 милисекунд
        rotationAnim.fillAfter = true
        current_degree = -degree

        var imDinamic = findViewById<ImageView>(R.id.imDinamic)
        imDinamic.startAnimation(rotationAnim)
    }
}