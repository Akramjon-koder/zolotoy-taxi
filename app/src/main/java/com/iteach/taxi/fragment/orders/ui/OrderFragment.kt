package com.iteach.taxi.fragment.orders.ui
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import com.iteach.taxi.R
import com.iteach.taxi.Utils.*
import com.iteach.taxi.Utils.SharedPref.ActiveCabSaveId
import com.iteach.taxi.databinding.FragmentOrderBinding
import com.iteach.taxi.fragment.orders.base.OrderModel
import info.texnoman.texnomart.utils.CheckInternet.ConnectivityListener
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.json.JSONException
import uz.algorithmgateway.project.ScoketViewModel
import com.iteach.taxi.Socket.SocketErrorVM
import com.iteach.taxi.Socket.SocketListener
class OrderFragment : Fragment(), AdapterRecyclerOrder.OnItemClickListner, ConnectivityListener,
    LifecycleObserver {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: AdapterRecyclerOrder
    private var webSocket: WebSocket? = null
    private var trynumber:Int =0
    companion object{
        var intentCablist =0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentCablist =0
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreated(){
        Log.i("tag","reached the State.Created")
      }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }
    override fun onDetach() {
        super.onDetach()
        lifecycle.removeObserver(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        instantiateWebSocket()
        sendData()
        CheckSocketConnect()
        binding.apply {
            recyclerOrders.setHasFixedSize(true)
            recyclerOrders.layoutManager=GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = AdapterRecyclerOrder(arrayListOf(), this@OrderFragment)
            recyclerOrders.adapter = adapter
        }
        startrecyclerview()
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
          if(intentCablist==0){
        if (ActiveCabSaveId.getId() == 0) {
            findNavController().navigate(R.id.action_orderFragment_to_cabListFragment)
        }
          }
    }
    override fun onResume() {
        super.onResume()
        setConnectivityListener(this)
        if (!requireContext().checkConnection()){
        alertDialog()
        }
    }
    private fun CheckSocketConnect() {
        ViewModelProvider(requireActivity())[SocketErrorVM::class.java].errorLiveData.observe(viewLifecycleOwner,{
            if (!it){
           Log.e("xatolikda",it.toString())
                trynumber++
                if (trynumber<5){
                instantiateWebSocket()
                sendData()
                }else{
                    alertDialog()
                    ViewModelProvider(requireActivity())[SocketErrorVM::class.java].setError(true)
                }
              //  Log.e("tryNumber",trynumber.toString())
            }else{

            }
        })
    }
    private fun startrecyclerview() {
        var list = arrayListOf<OrderModel>()
        var viewmodel = ViewModelProvider(requireActivity()).get(ScoketViewModel::class.java)
        viewmodel.jsonarray.observe(viewLifecycleOwner, {
            Log.e("salom", it.toString())
            list.clear()
            try {
                if (it.getJSONObject("data") != null) {
                    /*     if (it.getJSONObject("message").equals("Zakazlar o'zgarishi!")){

                            }*/
                    var dataobject = it.getJSONObject("data")
                    Log.e("datalar", dataobject.toString())
                    var orderList = dataobject.getJSONArray("orders")
                    for (i in 0 until orderList.length()) {
                        var jsonObject = orderList.getJSONObject(i)
                        var id = jsonObject.getString("id")
                        Log.e("userid", id.toString())
                        list.add(OrderModel(id, jsonObject.getString("client_phone"), jsonObject.getString("address_name"), jsonObject.getString("address_name")))
                    }
                    adapter.setData(list)
                }
            } catch (e: Exception) {
                Log.e("salom", e.toString())
            }
        })
    }
    fun alertDialog(){
        alert("Internetingizni tekshiring", "") {
            positiveButton("Qaytadan") {
                if (!requireContext().checkConnection()){
                    alertDialog()
                }
                else{
                    instantiateWebSocket()
                    sendData()
                }
            }
            negativeButton("Cancel"){
                // Do negative stuff here
            }
        }.show()
    }
    private fun sendData() {
        val body = JsonObject()
        body.addProperty("command", "auth")
        body.addProperty("token", Constants.token)
        try {
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        webSocket!!.send(body.toString())
    }
    private fun CabOrderRequest(order_id: Int) {
        val body = JsonObject()
        body.addProperty("command", "take_order")
        body.addProperty("token", Constants.token)
        body.addProperty("order_id", order_id)
        body.addProperty("car_id", ActiveCabSaveId.getId())
        webSocket!!.send(body.toString())

        findNavController().navigate(R.id.action_orderFragment_to_mapFragment)
    }
    private fun instantiateWebSocket() {
        val client = OkHttpClient()
        val request =
            Request.Builder().url("ws://taxi.biznesgoya.uz/ws").build()
        val socketListener = SocketListener(requireActivity())
        webSocket = client.newWebSocket(request, socketListener)
    }
    override fun orderClicked(order: OrderModel) {
        if (!requireContext().checkConnection()){
            alertDialog()
        }else{
        val alerDialog = MaterialAlertDialogBuilder(requireContext())
        alerDialog.setTitle(order.clientphone)
            alerDialog.setMessage(order.where+"borasizmi")
        alerDialog.setPositiveButton("ha") { dialog, which ->
            //replaceFragment(MapFragment())
            ViewModelProvider(requireActivity()).get(OrderIdShareVM::class.java).setOrderId(order)
            CabOrderRequest(order.id.toInt())
        }
        alerDialog.setNegativeButton("yo'q") { dialod, which -> }
        alerDialog.show()
        }
    }
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        alertDialog()
        Log.e("Connectionnetwork", isConnected.toString())
    }
}
class OrderIdShareVM:ViewModel(){
    private var _id:MutableLiveData<OrderModel> = MutableLiveData()
    val id:LiveData<OrderModel> get() =_id
    fun setOrderId(id:OrderModel){
        _id.postValue(id)
    }
}