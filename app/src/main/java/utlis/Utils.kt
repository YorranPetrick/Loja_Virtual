package utlis

import android.content.Context
import android.widget.EditText
import com.yorran.lojavirtual.R

class Utils {
    companion object {
        //Função para validação
        fun validation(validate: String, context: Context, editText: EditText): Boolean {
            return if (validate.isNullOrEmpty()) {
                editText.hint = "Campo Obrigatório"
                editText.setHintTextColor(context.resources.getColor(R.color.vermelho))
                false

            }else{
                true
            }
        }
    }
}
