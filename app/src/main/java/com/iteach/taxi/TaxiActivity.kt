package com.iteach.taxi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iteach.taxi.Utils.Constants
import com.iteach.taxi.Utils.IOnBackPressed
import com.iteach.taxi.Utils.SharedPref.ActiveCabSaveId
import com.iteach.taxi.Utils.SharedPref.TarifSave
import com.iteach.taxi.databinding.ActivityTaxiBinding
import com.iteach.taxi.fragment.FragmentChangeListener
import com.iteach.taxi.fragment.addcab.ui.CabListFragment
import com.iteach.taxi.fragment.mapfragment.ui.MapFragment
import com.iteach.taxi.fragment.orders.ui.OrderFragment
import com.iteach.taxi.generalVM.GetTarifVM
class TaxiActivity : AppCompatActivity() {
    var _binding: ActivityTaxiBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTaxiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //replaceFragment(OrderFragment())
        //replaceFragment(CabListFragment())
        var viewModel=ViewModelProvider(this)[GetTarifVM::class.java]
        viewModel.getTarif(Constants.token,ActiveCabSaveId.getId())
        viewModel.error.observe(this,{
            Log.e("error",it.toString())
        })
        viewModel.getTarifLiveData.observe(this,{
        TarifSave.setTarif(it)
        })
    }



}
