package com.example.secrediary_03

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    // Mark -Propeties/////////////////////////////////////////
    private val numberPicker0: NumberPicker by lazy {
        findViewById<NumberPicker?>(R.id.numberPicker0_Np).apply {
            minValue = 0
            maxValue = 9
        }
    }
    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker?>(R.id.numberPicker1_Np).apply {
            minValue = 0
            maxValue = 9
        }
    }
    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker?>(R.id.numberPicker2_Np).apply {
            minValue = 0
            maxValue = 9
        }
    }
    private val openBtn: AppCompatButton by lazy {
        findViewById(R.id.openBtn)
    }
    private val changePwBtn: AppCompatButton by lazy {
        findViewById(R.id.changePwBtn)
    }

    private var changePwMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker0
        numberPicker1
        numberPicker2

        initOpenBtn()
        initChangePwBtn()
    }


    // Mark -Fuctions/////////////////////////////////////////
    private fun initOpenBtn() {
        openBtn.setOnClickListener {
            if (changePwMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pwPreference = getSharedPreferences("password", MODE_PRIVATE)
            val pwFromUser = "${numberPicker0.value}${numberPicker1.value}${numberPicker2.value}"

            if (pwPreference.getString("password", "000").equals(pwFromUser)) {
                // pw Succeed!
                Log.d(
                    "MainActivity",
                    "PW Succeed! , password: ${pwPreference.getString("password", "000")}"
                )
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                showErrorDialog("실패!!", "비밀번호가 잘못되었습니다.")
            }
        }
    }

    private fun showErrorDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            // Event: Ramda Function
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }

    private fun initChangePwBtn() {
        changePwBtn.setOnClickListener {
            val pwPreference = getSharedPreferences("password", MODE_PRIVATE)
            val pwFromUser = "${numberPicker0.value}${numberPicker1.value}${numberPicker2.value}"
            if (changePwMode) {
                // change Done!
                //                pwPreference.edit {
                //                    val pwFromUser = "${numberPicker0.value}${numberPicker1.value}${numberPicker2.value}"
                //                    putString("password", pwFromUser)
                //
                //                    commit()
                //                }
                pwPreference.edit(true) {
                    putString("password", pwFromUser)
                }
                changePwMode = false
                changePwBtn.setBackgroundColor(Color.BLACK)
                Log.d("MainActivity", "ChangeDone!!! new PW : ${pwFromUser}")

            } else {
                // changePwMode 로 활성화
                // 비밀번호가 맞으면 모드 변경.
                if (pwPreference.getString("password", "000").equals(pwFromUser)) {
                    Log.d(
                        "MainActivity",
                        "PW Succeed! , current password: ${
                            pwPreference.getString(
                                "password",
                                "000"
                            )
                        }"
                    )
                    changePwMode = true
                    Toast.makeText(this, "변경할 패스워드 입력", Toast.LENGTH_SHORT).show()
                    changePwBtn.setBackgroundColor(Color.RED)
                    // 비밀번호가 맞지 않으면 모드 변경 실패
                } else {
                    showErrorDialog("실패!!", "비밀번호가 잘못되었습니다.")
                }
            }
        }
    }

}
