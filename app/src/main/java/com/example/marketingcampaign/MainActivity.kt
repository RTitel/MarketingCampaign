package com.example.marketingcampaign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.marketingcampaign.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navController = this.findNavController(R.id.nav_graph)
        NavigationUI.setupActionBarWithNavController(this, navController)

        /*val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MarketingDatabase"
        ).build()*/

        //db.channelDao().insertAll()

        /*val apiInterface = ApiInterface.create().getTarget()

        apiInterface.enqueue( object : Callback<List<Target>> {
            override fun onResponse(call: Call<List<Target>>?, response: Response<List<Target>>?) {

                if(response?.body() != null)
                    Log.d("apiResponse", response.message())
            }

            override fun onFailure(call: Call<List<Target>>?, t: Throwable?) {
                Log.d("apiResponse", "Fail")
            }
        })*/

        val progressBar = ProgressBar(this)
        //setting height and width of progressBar
        progressBar.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_graph)
        return navController.navigateUp()
    }
}