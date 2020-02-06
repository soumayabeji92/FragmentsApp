package com.example.task.ui
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import android.R
import android.content.Context
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_storage.*


/**
 * A simple [Fragment] subclass.
 */
class StorageFragment : Fragment() {

    val PREFS_FILENAME = "com.info.prefs"
    val TAG_INPUT = "UserData"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        inflater.inflate(com.example.task.R.layout.fragment_storage, container, false)

        val prefences = activity!!.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val view: View = inflater!!.inflate(com.example.task.R.layout.fragment_storage, container, false)

        val usertext = view.findViewById<View>(com.example.task.R.id.usertext) as TextView
        val edtxt = view.findViewById<View>(com.example.task.R.id.edtxt) as EditText
        val btnsavelocal = view.findViewById<View>(com.example.task.R.id.btnsavelocal) as Button
        btnsavelocal.setOnClickListener {

            //save text
            val inputstring: String = edtxt.text.toString()
            //check if the EditText have values or not
            if(inputstring.trim().length>0) {
                usertext.text=inputstring
                val editor = prefences.edit()
                editor.putString(TAG_INPUT,edtxt.text.toString())
                editor.apply()
                editor.commit()

            }else{Toast.makeText(activity,"Must Enter a text",Toast.LENGTH_SHORT).show()  }

        }

        //display user text
        val sharedinputValue = prefences.getString(TAG_INPUT,"user_input")

        if(sharedinputValue.isNullOrEmpty()){

            usertext.text="no data"

        }else{

            usertext.text=sharedinputValue
        }

        return view

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.example.task.R.menu.search_menu, menu)
        true
    }


}
