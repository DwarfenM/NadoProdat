package kz.sherua.nadoprodat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_tutorial.*
import kz.sherua.nadoprodat.adapter.TutorAdapter

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        viewPagerTut.adapter = TutorAdapter(applicationContext, viewPagerTut)
    }
}