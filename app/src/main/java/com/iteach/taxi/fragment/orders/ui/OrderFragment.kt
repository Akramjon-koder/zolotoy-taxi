package com.iteach.taxi.fragment.orders.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.esotericsoftware.minlog.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import com.iteach.taxi.R
import com.iteach.taxi.Utils.Constants
import com.iteach.taxi.Utils.SharedPref.ActiveCabSaveId
import com.iteach.taxi.databinding.FragmentOrderBinding
import com.iteach.taxi.fragment.FragmentChangeListener
import com.iteach.taxi.fragment.mapfragment.ui.MapFragment
import com.iteach.taxi.fragment.orders.base.OrderModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.json.JSONException
import uz.algorithmgateway.project.ScoketViewModel
import uz.algorithmgateway.project.SocketListener
class OrderFragment : Fragment(), AdapterRecyclerOrder.OnItemClickListner{
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: AdapterRecyclerOrder
    private var webSocket: WebSocket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        instantiateWebSocket()
        sendData()
        binding.apply {
            recyclerOrders.setHasFixedSize(true)
            recyclerOrders.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = AdapterRecyclerOrder(arrayListOf(), this@OrderFragment)
            recyclerOrders.adapter = adapter
        }
        startrecyclerview()
        return binding.root
    }
    private fun startrecyclerview() {
        var list = arrayListOf<OrderModel>()
        var viewmodel = ViewModelProvider(requireActivity()).get(ScoketViewModel::class.java)
        viewmodel.jsonarray.observe(viewLifecycleOwner, {
            Log.error("salom",it.toString())
            list.clear()
            try {
               if (it.getJSONObject("data")!=null){
               /*     if (it.getJSONObject("message").equals("Zakazlar o'zgarishi!")){

                         }*/
                    var dataobject = it.getJSONObject("data")
                    Log.error("datalar",dataobject.toString())
                    var orderList = dataobject.getJSONArray("orders")
                    for (i in 0 until orderList.length()) {
                        var jsonObject = orderList.getJSONObject(i)
                        var id = jsonObject.getString("id")
                        Log.error("userid", id.toString())
                        list.add( OrderModel(id,jsonObject.getString("client_phone"),jsonObject.getString("address_name"), jsonObject.getString("address_name") ) )
                    }
                    adapter.setData(list)
                }
            }catch (e: Exception) {
                Log.error("salom", e.toString())
            }
        })
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
    private fun CabOrderRequest(order_id:Int){
        val body =JsonObject()
        body.addProperty("command","take_order")
        body.addProperty("token",Constants.token)
        body.addProperty("order_id",order_id)
        body.addProperty("car_id",ActiveCabSaveId.getId())
        webSocket!!.send(body.toString())
        Thread.sleep(1000)
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
        val alerDialog = MaterialAlertDialogBuilder(requireContext())
        alerDialog.setTitle(order.userName + "\n" + order.where + "\n" + order.wherefrom)
        alerDialog.setPositiveButton("ha") { dialog, which ->
            //replaceFragment(MapFragment())
            CabOrderRequest(order.id.toInt())

        //
        }
        alerDialog.setNegativeButton("yo'q") { dialod, which ->

        }
        alerDialog.show()
    }


}