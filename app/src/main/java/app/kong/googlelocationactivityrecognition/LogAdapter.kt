package app.kong.googlelocationactivityrecognition

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class LogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {


    var list : MutableList<LogData> = mutableListOf<LogData>()
    var context : Context

    constructor(context : Context, logData : LogData ){
        list.add(logData)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent!!.context)
        var contactView = inflater.inflate(R.layout.item_log, parent, false)
        return LogHolder(contactView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var log =list.get(position)
        holder as LogHolder
        holder.mLog.setText(log.text)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(logData: LogData){
        list.add(logData)
    }

}