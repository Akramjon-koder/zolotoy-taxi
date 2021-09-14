package com.iteach.taxi.Socket
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONException
import org.json.JSONObject
import uz.algorithmgateway.project.ScoketViewModel
class SocketListener(activity: FragmentActivity) : WebSocketListener() {
    var activity: FragmentActivity = activity
    var  viewModel: ScoketViewModel =ViewModelProvider(activity).get(ScoketViewModel::class.java)
    private val mainThread = Handler(Looper.getMainLooper())
    override fun onOpen(webSocket: WebSocket, response: Response) {
        activity.runOnUiThread(Runnable {
           // Toast.makeText(activity, "Ulangan", Toast.LENGTH_LONG).show()
        }) }
    override fun onMessage(webSocket: WebSocket, text: String) {
        activity.runOnUiThread(Runnable {
            mainThread.post {
                ViewModelProvider(activity)[SocketErrorVM::class.java].setError(true)
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("message", text)
                    jsonObject.put("byServer", true)
                    var jsonObject = JSONObject(text.toString())
                    viewModel.setJsonArray(jsonObject)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }) }
    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }
    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
       // webSocket.close(1000,null)
        Log.e("close",code.toString()+" ->"+reason)
    }
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.e("onClosed",code.toString()+" ->"+reason)
    }
    override fun onFailure(webSocket: WebSocket, t:Throwable, @Nullable response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.e("Failure",t.toString()+"->")
        ViewModelProvider(activity)[SocketErrorVM::class.java].setError(false)
    }
}
class SocketErrorVM:ViewModel(){
    private  var _errorLiveData:MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData:LiveData<Boolean> get() = _errorLiveData
    fun setError(boolean:Boolean)
    {
        _errorLiveData.postValue(boolean)
    }

}