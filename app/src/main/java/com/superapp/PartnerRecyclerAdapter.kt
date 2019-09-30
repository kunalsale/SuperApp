package com.superapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by hp on 29-09-2019.
 */
class PartnerRecyclerAdapter(val partners: ArrayList<PartnerModel> ): RecyclerView.Adapter<PartnerRecyclerAdapter.PartnerViewHolder>() {

    lateinit var clickListener: PartnerRowClickListener

    fun setRowClickListner(clickListener: PartnerRowClickListener){
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): PartnerViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_partners_row_layout, viewGroup, false)
        return PartnerViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PartnerViewHolder, p1: Int) {
        val model = partners[viewHolder.adapterPosition]
        viewHolder.apply {
            this.imgPoster.setImageDrawable(model.partnerPoster)
            this.tvPartner.text = model.partnerName
        }
        viewHolder.imgInfo.setOnClickListener {
            clickListener.onInfoClicked(model.websiteUrl)
        }
        viewHolder.itemView.setOnClickListener {
            clickListener.onRowClicked(model.appPackageName)
        }
    }

    override fun getItemCount(): Int = partners.size

    class PartnerViewHolder(view: View): RecyclerView.ViewHolder(view){
        var imgPoster: ImageView
        var tvPartner: TextView
        var imgInfo: ImageView
        init {
            imgPoster = view.findViewById(R.id.imgPartner)
            tvPartner = view.findViewById(R.id.tvPartner)
            imgInfo = view.findViewById(R.id.imgInfo)
        }
    }
}