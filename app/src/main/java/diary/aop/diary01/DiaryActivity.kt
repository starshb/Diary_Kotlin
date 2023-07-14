package diary.aop.diary01

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import fastcampus.aop.part2.chapter03.R

class DiaryActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val diaryEditText: EditText by lazy {
        findViewById<EditText>(R.id.diaryEditText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        initDetailEditText()
    }

    private fun initDetailEditText() {
        val detail = getSharedPreferences("diary", Context.MODE_PRIVATE).getString("detail", "")
        diaryEditText.setText(detail)

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit(true) {
                putString("detail", diaryEditText.text.toString())
            }
        }

        diaryEditText.addTextChangedListener {
            Log.d("DiaryActivity", "text Changed :: $it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}