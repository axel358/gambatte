package jp.axel.gambatte.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.axel.gambatte.R

class KanaItemAdapter(
    private val context: Context,
    private val layoutId: Int,
    private val kanas: List<String>,
    private val listener: KanaClickListener
) :
    RecyclerView.Adapter<KanaItemAdapter.ViewHolder>() {

    interface KanaClickListener {
        fun onKanaClicked(kana: String, position: Int, view: View)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nameTv: TextView
        private var pronunciationTv: TextView

        init {
            nameTv = itemView.findViewById(R.id.item_name_tv)
            pronunciationTv = itemView.findViewById(R.id.item_pronunciation_tv)
        }

        fun bind(kana: String, position: Int, listener: KanaClickListener) {
            val data = kana.split("_")
            nameTv.text = data[0]
            if (data.size>1)
            {
                pronunciationTv.visibility = View.VISIBLE
                pronunciationTv.text = data[1]
            }
            itemView.setOnClickListener { view -> listener.onKanaClicked(kana, position, view) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayoutView = LayoutInflater.from(context).inflate(layoutId, parent, false)
        return ViewHolder(itemLayoutView)
    }

    override fun getItemCount() = kanas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kana = kanas[position]
        holder.bind(kana, position, listener)
    }
}