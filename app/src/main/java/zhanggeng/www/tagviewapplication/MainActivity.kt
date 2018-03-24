package zhanggeng.www.tagviewapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * tagview test
 *
 * @author zg
 * @date 2018/03/21
 */

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelect1.setOnClickListener { tvSelect1.setSelected(true) }

    }
}
