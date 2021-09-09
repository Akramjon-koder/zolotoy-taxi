package com.iteach.taxi.fragment.addcab.ui
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.esotericsoftware.minlog.Log
import com.iteach.taxi.R
import com.iteach.taxi.Utils.Constants
import com.iteach.taxi.Utils.getFileName
import com.iteach.taxi.databinding.FragmentAddCabBinding
import com.iteach.taxi.fragment.addcab.base.CabListVM
import com.iteach.taxi.fragment.addcab.base.CarTypeModel
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
class AddCabFragment : Fragment() {
    private var _binding: FragmentAddCabBinding? = null
    val binding get() = _binding!!
    lateinit var viewmodel: CabListVM
    lateinit var list: ArrayList<CarTypeModel>
    private var selectedImage1: Uri? = null
    var carid: Int = 0
    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER1 = 1
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddCabBinding.inflate(inflater, container, false)
        list = ArrayList()
        binding.SpinkitView.visibility =View.VISIBLE
        initVM()
        loadCarType()
        setImage()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            confirmButton()
        }
        return binding.root
    }
    private fun setImage() {
        binding.cabImage.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER1)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun confirmButton() {
        binding.confirmButton.setOnClickListener {
            getCarId()
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            if (selectedImage1 != null) {
                val parcelFileDescriptor1 =
                    requireActivity().contentResolver.openAssetFileDescriptor(selectedImage1!!, "r", null) ?: return@setOnClickListener
                val inputStream1 = FileInputStream(parcelFileDescriptor1.fileDescriptor)
                // Your image file

                var file1 = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(selectedImage1!!))
                val outPutStream1 = FileOutputStream(file1)
                inputStream1.copyTo(outPutStream1)
                Log.error("imafe",file1.name.toString())
                Log.error("imafe",selectedImage1.toString())
                builder.addFormDataPart("image", file1.name, file1.asRequestBody(MultipartBody.FORM))
            }
            builder.addFormDataPart("model_id", carid.toString())
            Log.error("color",binding.cabColor.text.toString())
            builder.addFormDataPart("color", binding.cabColor.text.toString())
            builder.addFormDataPart("position", binding.cabPosition.text.toString())
            builder.addFormDataPart("number", binding.cabNumber.text.toString())
            builder.addFormDataPart("description", binding.cabDesc.text.toString())
            var request = builder.build()
            viewmodel.addCab(Constants.token, request)
            viewmodel.addCabLiveData.observe(viewLifecycleOwner, {
                findNavController().popBackStack()
                Log.error("yuklandi", it.toString())
            })
        }
    }
    private fun loadCarType() {
        var items: ArrayList<String> = ArrayList()
        viewmodel.getCarTypeModel(Constants.token)
        viewmodel.carModelLiveData.observe(viewLifecycleOwner, { list ->
            this.list.clear()
            this.list.addAll(list)
            for (i in list.indices) {
                items.add(list[i].name.toString())
            }
            val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, items)
            binding.autoCompleteCabType.setAdapter(adapter)
        })
    }


    private fun initVM() {
        viewmodel = ViewModelProvider(requireActivity()).get(CabListVM::class.java)
    }
    private fun getCarId() {
        var cartype = binding.autoCompleteCabType.text
        for (i in list.indices) {
            if (cartype.toString().equals(list[i].name.toString())) {
                carid = list[i].id?.toInt()!!
                break
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_PICKER1 -> {
                    selectedImage1 = data?.data
                    binding.cabImage.setImageURI(selectedImage1)
                }
            }
        }
    }

}