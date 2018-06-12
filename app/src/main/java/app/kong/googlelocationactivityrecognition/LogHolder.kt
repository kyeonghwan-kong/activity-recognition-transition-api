package app.kong.googlelocationactivityrecognition

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_log.view.*

class LogHolder : RecyclerView.ViewHolder {
    var mLog : TextView

    constructor(itemView : View) : super(itemView){
        mLog = itemView.log
    }
}