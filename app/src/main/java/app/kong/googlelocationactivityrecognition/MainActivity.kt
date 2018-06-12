package app.kong.googlelocationactivityrecognition

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    val TRANSITIONS_RECEIVER_ACTION = BuildConfig.APPLICATION_ID + "TRANSITIONS_RECEIVER_ACTION"
    private var mLogAdapter : LogAdapter? = null
    private var mPendingIntent : PendingIntent? = null
    private var mTransitionsReceiver: TransitionsReceiver? = null

    inner class TransitionsReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (!TextUtils.equals(TRANSITIONS_RECEIVER_ACTION, intent!!.getAction())) {
                mLogAdapter!!.addData(LogData("Received an unsupported action in TransitionsReceiver: action=" + intent!!.getAction()))
                mLogAdapter!!.notifyDataSetChanged()
                return
            }
            if (ActivityTransitionResult.hasResult(intent)) {
                val result = ActivityTransitionResult.extractResult(intent)
                result?.let {
                    for (event in it.transitionEvents) {
                        val activity = toActivityString(event.activityType)
                        val transitionType = toTransitionType(event.transitionType)
                        var str = "Transition: " + activity + " (" + transitionType + ")" + "   " + SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
                        mLogAdapter!!.addData(LogData(str))
                        mLogAdapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent(TRANSITIONS_RECEIVER_ACTION)
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        mTransitionsReceiver = TransitionsReceiver()
        registerReceiver(mTransitionsReceiver, IntentFilter(TRANSITIONS_RECEIVER_ACTION))

        var recyclerView = findViewById(R.id.log_list) as RecyclerView
        mLogAdapter = LogAdapter(this, LogData("Loading..."))
        recyclerView.adapter = mLogAdapter
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        mLogAdapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        setupActivityTransitions()
    }

    override fun onStop() {
        if (mTransitionsReceiver != null) {
            unregisterReceiver(mTransitionsReceiver)
            mTransitionsReceiver = null
        }
        super.onStop()
    }

    fun setupActivityTransitions(){
        var transitions : MutableList<ActivityTransition> = mutableListOf<ActivityTransition>()
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.IN_VEHICLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.IN_VEHICLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.ON_BICYCLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.ON_BICYCLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.ON_FOOT)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.ON_FOOT)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.RUNNING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build())
        transitions.add(
                ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.RUNNING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build())

        val request = ActivityTransitionRequest(transitions)
        val task = ActivityRecognition.getClient(this).requestActivityTransitionUpdates(request, mPendingIntent)
        task.addOnSuccessListener {
            // Handle success
            mLogAdapter!!.addData(LogData("서비스를 시작합니다."))
            mLogAdapter!!.notifyDataSetChanged()
        }
        task.addOnFailureListener {
            // Handle error
            mLogAdapter!!.addData(LogData("서비스가 실패했습니다."))
            mLogAdapter!!.notifyDataSetChanged()
        }
    }

    private fun toActivityString(activity: Int): String {
        when (activity) {
            DetectedActivity.STILL -> return "STILL"
            DetectedActivity.WALKING -> return "WALKING"
            DetectedActivity.ON_BICYCLE -> return "ON_BICYCLE"
            DetectedActivity.IN_VEHICLE -> return "IN_VEHICLE"
            DetectedActivity.RUNNING -> return "RUNNING"
            DetectedActivity.ON_FOOT -> return "ON_FOOT"
            DetectedActivity.TILTING -> return "TILTING"
            else -> return "UNKNOWN"
        }
    }

    private fun toTransitionType(transitionType: Int): String {
        when (transitionType) {
            ActivityTransition.ACTIVITY_TRANSITION_ENTER -> return "ENTER"
            ActivityTransition.ACTIVITY_TRANSITION_EXIT -> return "EXIT"
            else -> return "UNKNOWN"
        }
    }
}
