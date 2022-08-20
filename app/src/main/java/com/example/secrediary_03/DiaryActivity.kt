package com.example.secrediary_03

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    // Mark -Properties/////////////////////////////////////////
    private val handler = Handler(Looper.getMainLooper()) // MainThread 에 들어이있는 Handler.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        
        // Mark -Properties/////////////////////////////////////////
        val diaryEt = findViewById<EditText>(R.id.diary_Et)
        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)
        diaryEt.setText(detailPreferences.getString("detail", ""))

        val runnable = Runnable{
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit{
                putString("detail", diaryEt.text.toString())
            }
            Log.d("DiaryActivity","Save!")
        }

        /*
        handler 는 thread 와 thread 사이 통신을 엮어주는 기능.
네트워크 통신 혹은 파일 저장 같이 오래 걸리는 작업은 UIThread 가 아닌 다른 Thread 에서 작업한다.

        val t = Thread(Runnable{
            // 네트워크 작업이 일어난 후
            Log.d("aa","aa")
            // UI에 변경 내용 업데이트. -> 불가능. (main thread(UiThread) 가 아니기 때문)
            runOnUiThread{
                // 이 안에서 UI 작업을 해야 한다.
            }
        }).start()

        handler 가 runOnUiThread 에서 사용하는 것.
        실제로는
        handler.post{

        }
         */


        diaryEt.addTextChangedListener {
            // 메인 스레드와 연결을 해주는 기능을 Handler을 이용하여 구현할 것.
            handler.removeCallbacks(runnable) // Remove any pending posts of Runnable r that are in the message queue.
            handler.postDelayed(runnable, 1000)

        }


    }

}