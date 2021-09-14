package com.iteach.taxi.Utils.SharedPref
import io.paperdb.Paper
object ActiveCabSaveId {
    private const val  pref_name ="qfqqeqqwqljwg"
    fun saveId(id:Int){
        Paper.book().write(pref_name,id) }
     fun getId():Int{
     return Paper.book().read(pref_name,0)
     }
}