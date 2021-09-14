package com.iteach.taxi.Utils.SharedPref

import com.iteach.taxi.model.GetTarifModel
import io.paperdb.Paper

object TarifSave {
    const val  tarif= "safjfjwkld"
    fun setTarif(model:GetTarifModel)
    {
        Paper.book().write(tarif,model)
    }
    fun getTarif():GetTarifModel{
        return Paper.book().read(tarif,GetTarifModel(0,0,0,0,0,0,0,0,0,0))
    }



}