package output

import DAO.UserEntity

class Consola : Iconsola {

    override fun showMessage(message:String, lineBreak:Boolean){
        if(lineBreak) println(message)
        else print(message)
    }

    override fun show(userList: List<UserEntity>?,message: String){
        if(userList != null){
            if(userList.isNotEmpty()){
                showMessage(message)
                userList.forEachIndexed { index, userEntity ->
                    showMessage("\t${index+1}. $userEntity")
                }
            }else{
                showMessage("No Users Found")
            }
        }

    }
}