package com.superapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PartnerRowClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = PartnerRecyclerAdapter(getPartnerList())
        adapter.setRowClickListner(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.lastPathSegment?.also { partnerName ->
                when(partnerName){
                    "taj" -> openApp("com.tajhotels.reservation")
                    "tanishq" -> openApp("com.titan.GHS")
                    "hotelinfo" -> openWebsite("https://www.tajhotels.com/")
                }
            }
        }
    }


    fun getPartnerList(): ArrayList<PartnerModel>{
        val partners = ArrayList<PartnerModel>()
        partners.add(PartnerModel("Taj Hotels",getDrawable(R.drawable.taj),"com.tajhotels.reservation","https://www.tajhotels.com/"))
        partners.add(PartnerModel("Tata Sky",getDrawable(R.drawable.tata_sky),"com.ryzmedia.tatasky","https://www.tatasky.com/wps/portal/"))
        partners.add(PartnerModel("Tanishq",getDrawable(R.drawable.tanishq),"com.titan.GHS","https://www.tanishq.co.in/"))
        partners.add(PartnerModel("Croma",getDrawable(R.drawable.croma),"croma.cns.radiant_tech.croma_new_store","https://www.croma.com/"))
        return partners
    }

    override fun onRowClicked(packageName: String) {
        openApp(packageName)
    }

    override fun onInfoClicked(websiteUrl: String) {
        openWebsite(websiteUrl)
    }

    fun openApp(packageName: String) {
        var launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent == null) {
            // Bring user to the market or let them choose an app?
            launchIntent = Intent(Intent.ACTION_VIEW)
            launchIntent.setData(Uri.parse("market://details?id=" + packageName))
        }
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(launchIntent)
    }

    fun openWebsite(websiteUrl: String){
        val webIntent: Intent = Uri.parse(websiteUrl).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }
}